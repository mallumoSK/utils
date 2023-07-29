@file:Suppress("unused")

package tk.mallumo.utils


import java.io.*


val String.file: File
    get() = File(this)

