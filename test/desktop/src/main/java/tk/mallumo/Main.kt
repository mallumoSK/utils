package tk.mallumo

import tk.mallumo.utils.*

fun main(args: Array<String>) {
    println("Hello world!")
    shellSH {
        run("ls -l").also {
            println(it.isSuccess)
            println(it.output())
            println(it.stderr())
        }
        shutdown()
    }
}
