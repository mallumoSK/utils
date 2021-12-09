package tk.mallumo.utils


object MimeMatcher {

    val documentMS = sortedMapOf(
        "application/vnd.openxmlformats-officedocument.presentationml.presentation" to "pptx",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document" to "docx",
        "application/vnd.openxmlformats-officedocument.presentationml.slideshow" to "ppsx",
        "application/vnd.openxmlformats-officedocument.presentationml.template" to "potx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.template" to "xltx",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" to "xlsx",
        "application/vnd.ms-powerpoint.presentation.macroEnabled.12" to "pptm",
        "application/vnd.ms-powerpoint.slideshow.macroEnabled.12" to "ppsm",
        "application/vnd.ms-excel.sheet.binary.macroEnabled.12" to "xlsb",
        "application/vnd.ms-powerpoint.addin.macroEnabled.12" to "ppam",
        "application/vnd.ms-excel.template.macroEnabled.12" to "xltm",
        "application/vnd.ms-word.document.macroEnabled.12" to "docm",
        "application/vnd.ms-excel.sheet.macroEnabled.12" to "xlsm",
        "application/vnd.ms-excel.addin.macroEnabled.12" to "xlam",
        "application/vnd.ms-powerpoint" to "ppt",
        "application/vnd.ms-excel" to "xls",
        "application/msword" to "doc"
    )

    val documentOPEN = sortedMapOf(
        "application/vnd.oasis.opendocument.presentation-template" to "otp",
        "application/vnd.oasis.opendocument.spreadsheet-template" to "ots",
        "application/vnd.oasis.opendocument.graphics-template" to "otg",
        "application/vnd.oasis.opendocument.text-template" to "ott",
        "application/vnd.oasis.opendocument.presentation" to "odp",
        "application/vnd.oasis.opendocument.spreadsheet" to "ods",
        "application/vnd.oasis.opendocument.text-master" to "odm",
        "application/vnd.oasis.opendocument.text-web" to "oth",
        "application/vnd.oasis.opendocument.graphics" to "odg",
        "application/vnd.stardivision.impress-packed" to "sdp",
        "application/vnd.oasis.opendocument.database" to "odb",
        "application/vnd.stardivision.writer-global" to "sgl",
        "application/vnd.oasis.opendocument.formula" to "odf",
        "application/vnd.oasis.opendocument.chart" to "odc",
        "application/vnd.oasis.opendocument.text " to "odt",
        "application/vnd.oasis.opendocument.image" to "odi",
        "application/vnd.sun.xml.impress.template" to "sti",
        "application/vnd.sun.xml.writer.template" to "stw",
        "application/vnd.openofficeorg.extension" to "oxt",
        "application/vnd.sun.xml.draw.template" to "std",
        "application/vnd.sun.xml.calc.template" to "stc",
        "application/vnd.sun.xml.writer.global" to "sxg",
        "application/vnd.stardivision.impress" to "sdd",
        "application/vnd.stardivision.writer" to "sdw",
        "application/vnd.stardivision.chart" to "sds",
        "application/vnd.stardivision.mail" to "sdm",
        "application/vnd.stardivision.math" to "smf",
        "application/vnd.stardivision.calc" to "sdc",
        "application/vnd.stardivision.draw" to "sda",
        "application/vnd.sun.xml.impress" to "sxi",
        "application/vnd.sun.xml.writer" to "sxw",
        "application/vnd.sun.xml.math" to "sxm",
        "application/vnd.sun.xml.calc" to "sxc",
        "application/vnd.sun.xml.draw" to "sxd",
        "application/x-starimpress" to "sdd",
        "application/x-starwriter" to "sdw",
        "application/x-starchart" to "sds",
        "application/x-stardraw" to "sda",
        "application/x-starmath" to "smf",
        "application/x-starcalc" to "sdc"
    )

    val rawText = sortedMapOf(
        "text/plain" to "txt",
        "application/json" to "json",
        "text/html" to "html"
    )

    val documentAll = sortedMapOf<String, String>().apply {
        putAll(documentMS)
        putAll(documentOPEN)
        putAll(rawText)
    }


    val images = sortedMapOf(
        "image/bmp" to "bmp",
        "image/gif" to "gif",
        "image/jpeg" to "jpeg",
        "image/jpg" to "jpg",
        "image/png" to "png",
        "image/webp" to "webp"
    )

    val audio = sortedMapOf(
        "audio/3gpp" to "3gpp",
        "audio/ac3" to "ac3",
        "audio/AMR" to "amr",
        "audio/AMR-WB" to "awb",
        "audio/basic" to "au",
        "audio/mp4" to "mp4",
        "audio/mpeg" to "mpeg",
        "audio/mpegurl" to "mpeg",
        "audio/vnd.rn-realaudio" to "rn",
        "audio/x-ape" to "ape",
        "audio/x-flac" to "flac",
        "audio/x-it" to "it",
        "audio/x-m4a" to "m4a",
        "audio/x-matroska" to "mkv",
        "audio/x-mod" to "mod",
        "audio/x-mp3" to "mp3",
        "audio/x-mpeg" to "mpeg",
        "audio/x-mpegurl" to "mpegurl",
        "audio/x-ms-asf" to "asf",
        "audio/x-ms-asx" to "asx",
        "audio/x-ms-wax" to "wax",
        "audio/x-ms-wma" to "wma",
        "audio/x-musepack" to "musepack",
        "audio/x-pn-aiff" to "aiff",
        "audio/x-pn-au" to "au",
        "audio/x-pn-realaudio" to "realaudio",
        "audio/x-pn-realaudio-plugin" to "realaudio",
        "audio/x-pn-wav" to "wav",
        "audio/x-pn-windows-acm" to "acm",
        "audio/x-realaudio" to "realaudio",
        "audio/x-real-audio" to "audio",
        "audio/x-scpls" to "scpls",
        "audio/x-tta" to "tta",
        "audio/x-wav" to "wav",
        "audio/x-wavpack" to "wav"
    )

    val video = sortedMapOf(
        "application/ogg" to "ogg",
        "application/ram" to "ram",
        "application/smil" to "smil",
        "application/vnd.rn-realmedia" to "rn",
        "application/x-extension-m4a" to "m4a",
        "application/x-extension-mp4" to "mp4",
        "application/x-flac" to "flac",
        "application/x-flash-video" to "flash",
        "application/x-matroska" to "mkv",
        "application/x-ogg" to "ogg",
        "application/x-quicktime-media-link" to "qt",
        "application/x-quicktimeplayer" to "qt",
        "application/x-shorten" to "shorten",
        "application/x-smil" to "smil",
        "application/xspf+xml" to "xspf",
        "misc/ultravox" to "vox",
        "text/google-video-pointer" to "gvp",
        "text/x-google-video-pointer" to "gvp",
        "video/3gpp" to "3gpp",
        "video/dv" to "dv",
        "video/fli" to "fli",
        "video/flv" to "flv",
        "video/mp4" to "mp4",
        "video/mp4v-es" to "mp4",
        "video/mpeg" to "mpeg",
        "video/msvideo" to "msvideo",
        "video/quicktime" to "qt",
        "video/vivo" to "vivo",
        "video/vnd.divx" to "mp4",
        "video/vnd.rn-realvideo" to "rn",
        "video/vnd.vivo" to "vivo",
        "video/x-anim" to "anim",
        "video/x-avi" to "avi",
        "video/x-flc" to "flc",
        "video/x-fli" to "fli",
        "video/x-flic" to "flic",
        "video/x-m4v" to "m4v",
        "video/x-matroska" to "mkv",
        "video/x-mpeg" to "mpeg",
        "video/x-ms-asf" to "asf",
        "video/x-msvideo" to "msvideo",
        "video/x-ms-wm" to "wm",
        "video/x-ms-wmv" to "wmv",
        "video/x-ms-wmx" to "wmx",
        "video/x-ms-wvx" to "wvx",
        "video/x-nsv" to "nsv",
        "video/x-ogm+ogg" to "ogg",
        "video/x-theora+ogg" to "ogg",
        "image/vnd.rn-realpix" to "rn",
        "image/x-pict" to "pict"
    )

    val pdf = sortedMapOf(
        "application/pdf" to "pdf",
        "application/epub+zip" to "epub"
    )
    val all = sortedMapOf<String, String>().apply {
        putAll(documentAll)
        putAll(images)
        putAll(video)
        putAll(audio)
        putAll(pdf)
    }

}