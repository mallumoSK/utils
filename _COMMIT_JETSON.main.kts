#!/usr/bin/env kotlin

@file:DependsOn("org.json:json:20231013")
@file:DependsOn("org.slf4j:slf4j-simple:2.0.9")

import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import org.json.JSONObject
import org.json.JSONArray

val dir = args.getOrNull(0)?:error("missing dir path")
val serviceUrl = "http://192.168.1.27:8000/generate/live"

fun executeCommand(args: List<String>): String? {
    return try {
        val process = ProcessBuilder(args)
            .directory(File(dir))
            .redirectErrorStream(true)
            .start()

        val output = process.inputStream.bufferedReader().readText()
        process.waitFor()
        output.trim()
    } catch (e: Exception) {
        null
    }
}

fun callGemmaLiveService(systemPrompt: String, userPrompt: String): String? {
    val messages = JSONArray().apply {
        put(JSONObject().apply {
            put("role", "user")
            put("content", JSONArray().apply {
                put(JSONObject().apply {
                    put("type", "text")
                    put("text", "$systemPrompt\n\n$userPrompt")
                })
            })
        })
    }

    val requestBody = JSONObject().apply {
        put("messages", messages)
        put("max_new_tokens", 2048)
    }

    val client = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .connectTimeout(Duration.ofSeconds(10))
        .build()

    val request = HttpRequest.newBuilder()
        .uri(URI.create(serviceUrl))
        .header("Content-Type", "application/json")
        .header("Accept", "text/plain")
        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
        .build()

    val fullResponse = StringBuilder()

    return try {
        val response = client.send(request, HttpResponse.BodyHandlers.ofInputStream())

        if (response.statusCode() == 200) {
            val reader = response.body().bufferedReader()
            var charCode = reader.read()
            while (charCode != -1) {
                val character = charCode.toChar()
                print(character)
                System.out.flush()
                fullResponse.append(character)
                charCode = reader.read()
            }

            fullResponse.toString().trim().let {
                val tokenToRemove = "<turn|>"
                if (it.trim().endsWith(tokenToRemove)) {
                    val backtrack = "\u001b[D".repeat(tokenToRemove.length)
                    val clear = "\u001b[K"
                    print("$backtrack$clear")
                    System.out.flush()
                    println()
                    it.replace("<turn|>", "").trim()
                } else {
                    println()
                    it
                }
            }
        } else {
            println("Error: Service returned status ${response.statusCode()}")
            null
        }
    } catch (e: Exception) {
        println("Error calling live Gemma service: ${e.message}")
        null
    }
}

// Logic start
val changedFilesSet = mutableSetOf<String>()

// Get staged files with --ignore-submodules
executeCommand(listOf("git", "diff", "--cached", "--name-only", "--ignore-submodules"))?.lines()?.forEach { 
    if (it.isNotBlank()) changedFilesSet.add(it.trim()) 
}

// Get unstaged files with --ignore-submodules
executeCommand(listOf("git", "diff", "--name-only", "--ignore-submodules"))?.lines()?.forEach { 
    if (it.isNotBlank()) changedFilesSet.add(it.trim()) 
}

if (changedFilesSet.isEmpty()) {
    println("No changes detected in $dir.")
} else {
    println("FILES TO PROCESS:")
    changedFilesSet.forEach { println("  - $it") }

    val fileCommitMessages = mutableMapOf<String, String>()
    var filesToProcess = changedFilesSet.toList()

    while (filesToProcess.isNotEmpty()) {
        val currentFailed = mutableListOf<String>()
        
        for (file in filesToProcess) {
            println("\nProcessing: $file")
            try {
                // Get diff relative to HEAD to cover both staged and unstaged, including --ignore-submodules
                val diff = executeCommand(listOf("git", "diff", "HEAD", "--ignore-submodules", "--", file))
                
                if (diff.isNullOrBlank()) {
                    println("No changes relative to HEAD for $file. Skipping.")
                    continue
                }

                val response = callGemmaLiveService(
                    """"
        Generate a comprehensive and detailed git commit message based on the provided diff.
        Simple explain WHAT was changed and WHY it was changed.
        Use the imperative mood (e.g., 'Add', 'Fix', 'Refactor').
        Output ONLY the message. No preamble or explanations.
        """.trimIndent(),
                    "FILE: $file\n\nDIFF:\n$diff"
                )

                if (!response.isNullOrEmpty()) {
                    fileCommitMessages[file] = response
                } else {
                    currentFailed.add(file)
                }
            } catch (e: Exception) {
                println("Diff crashed for $file: ${e.message}")
                currentFailed.add(file)
            }
        }

        if (currentFailed.isNotEmpty()) {
            println("\n!!! FAILED FILES: ${currentFailed.joinToString(", ")}")
            println("What next? [R]etry failed files, [S]kip failed and build message from successful, [Q]uit?")
            val choice = readln().trim().uppercase()
            when (choice) {
                "R" -> {
                    filesToProcess = currentFailed
                }
                "S" -> {
                    filesToProcess = emptyList()
                }
                "Q" -> {
                    println("Exiting.")
                    System.exit(0)
                }
                else -> {
                    println("Invalid choice. Exiting.")
                    System.exit(1)
                }
            }
        } else {
            filesToProcess = emptyList()
        }
    }

    if (fileCommitMessages.isNotEmpty()) {
        println("\n========================================")
        println("BUILDING FINAL COMMIT MESSAGE...")
        
        val finalMessage = fileCommitMessages.entries.joinToString("\n\n") { (path, msg) ->
            "# $path\n$msg"
        }

        println("\nFINAL MESSAGE:\n$finalMessage")
        println("\nCOMMIT THESE CHANGES? (Y/N) [Y]:")
        val confirm = readln().trim().uppercase()
        if (confirm == "Y" || confirm.isEmpty()) {
            val filesToStage = fileCommitMessages.keys.toList()
            executeCommand(listOf("git", "add") + filesToStage)
            
            val msgFile = File.createTempFile("g-commit", ".txt").apply {
                writeText(finalMessage)
                deleteOnExit()
            }
            val commitOutput = executeCommand(listOf("git", "commit", "-F", msgFile.absolutePath))
            println(commitOutput)
            println("DONE")
        } else {
            println("ABORTED")
        }
    } else {
        println("No commit messages generated.")
    }
}
