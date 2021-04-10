package com.android.ide.common.vectordrawable

import java.io.ByteArrayOutputStream
import java.io.File

internal fun svgToXml(
    fileList: List<File>,
    outputFile: (File) -> File = {
        File(it.parent, "${it.nameWithoutExtension}.xml")
    },
    logWarning: Boolean = false,
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
