package com.practicum.appinspector.util


import java.io.File
import java.security.MessageDigest

object HashUtils {

    fun sha1(path: String): String {
        val file = File(path)
        val digest = MessageDigest.getInstance("SHA-1")

        file.inputStream().use { inStream ->
            val buffer = ByteArray(8192)
            var read: Int
            while (inStream.read(buffer).also { read = it } != -1) {
                digest.update(buffer, 0, read)
            }
        }

        return digest.digest().joinToString("") { "%02x".format(it) }
    }
}
