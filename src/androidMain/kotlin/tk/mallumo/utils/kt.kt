@file:Suppress("unused")

package tk.mallumo.utils

import android.net.*
import android.webkit.*
import java.io.*


val String.fileUri: Uri
    get() = Uri.fromFile(file)


val File.uri: Uri
    get() = Uri.fromFile(this)


private fun getMIME(file: File)
    : List<String> = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
    MimeTypeMap.getFileExtensionFromUrl(
        file.toURI()
            .toString()
    ).lowercase()
)?.split("/") ?: arrayListOf("", "")

val File.mime: MimeType
    get() = MimeType(this.absolutePath)

val File.mimeMain: String
    get() = MimeType(this.absolutePath).main


val File.mimeSub: String
    get() = MimeType(this.absolutePath).sup


class MimeType(path: String = "") : Serializable {
    var main = "unknown"
    var sup = "unknown"
    var full = "unknown"

    object Main {
        val image = "image"
        val video = "video"
        val audio = "audio"
    }

    object Sub {
        val pdf = "pdf"

        val bmp = "bmp"
        val gif = "gif"
        val jpg = "jpg"
        val webp = "webp"
        val png = "png"
    }

    init {
        if (path.isNotEmpty()) {
            getMIME(path.file).also {
                main = it[0]
                sup = it[1]
                full = "$main/$sup"
            }
        }
    }
}
