package com.android.ide.common.vectordrawable

import java.io.ByteArrayOutputStream
import java.io.File
import java.util.Locale

internal fun svgToXml(
    fileList: List<File>,
    logWarning: Boolean = false,
    outputFile: (File) -> File = {
        File(it.parent, "${it.nameWithoutExtension.toLowerCase(Locale.ROOT).replace(Regex("[^0-9_a-z]"), "_")}.xml")
    },
) {
    var success = 0
    var failure = 0
    val svgLogLevel = if (logWarning) SvgTree.SvgLogLevel.WARNING else SvgTree.SvgLogLevel.ERROR
    fileList.forEach {
        val outStream = ByteArrayOutputStream()
        val error = Svg2Vector.parseSvgToXml(it, outStream, svgLogLevel)
        if (error.first.isNotEmpty()) {
            println(error.first)
        }
        val bytes = outStream.toByteArray()
        if (error.second || bytes.isEmpty()) {
            ++failure
            return@forEach
        }
        outputFile(it).writeBytes(bytes)
        ++success
    }
    println("success: $success, failure: $failure")
}
