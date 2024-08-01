package com.ihcl.payment.util

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.security.MessageDigest
import java.security.spec.AlgorithmParameterSpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.CipherOutputStream
import javax.crypto.CipherInputStream
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AesCryptUtil {
    private var ecipher: Cipher? = null
    private var dcipher: Cipher? = null

    /**
     * Input a string that will be md5 hashed to create the key.
     * @return void, cipher initialized
     */
    constructor() {
        try {
            val kgen = KeyGenerator.getInstance("AES")
            kgen.init(128)
            setupCrypto(kgen.generateKey())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    constructor(key: String) {
        val skey = SecretKeySpec(getMD5(key), "AES")
        setupCrypto(skey)
    }

    private fun setupCrypto(key: SecretKey) {
        // Create an 8-byte initialization vector
        val iv = byteArrayOf(
            0x00,
            0x01,
            0x02,
            0x03,
            0x04,
            0x05,
            0x06,
            0x07,
            0x08,
            0x09,
            0x0a,
            0x0b,
            0x0c,
            0x0d,
            0x0e,
            0x0f
        )
        val paramSpec: AlgorithmParameterSpec = IvParameterSpec(iv)
        try {
            ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding")

            // CBC requires an initialization vector
            ecipher!!.init(Cipher.ENCRYPT_MODE, key, paramSpec)
            dcipher!!.init(Cipher.DECRYPT_MODE, key, paramSpec)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Buffer used to transport the bytes from one stream to another
    private var buf = ByteArray(1024)
    fun encrypt(`in`: InputStream, outputStream: OutputStream) {
        var out = outputStream
        try {
            // Bytes written to out will be encrypted
            out = CipherOutputStream(out, ecipher)

            // Read in the cleartext bytes and write to out to encrypt
            var numRead: Int
            while (`in`.read(buf).also { numRead = it } >= 0) {
                out.write(buf, 0, numRead)
            }
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * Input is a string to encrypt.
     * @return a Hex string of the byte array
     */
    fun encrypt(plaintext: String): String? {
        return try {
            val ciphertext = ecipher!!.doFinal(plaintext.toByteArray(charset("UTF-8")))
            byteToHex(ciphertext)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun decrypt(inputStream: InputStream, out: OutputStream) {
        var `in` = inputStream
        try {
            // Bytes read from in will be decrypted
            `in` = CipherInputStream(`in`, dcipher)

            // Read in the decrypted bytes and write the cleartext to out
            var numRead: Int
            while (`in`.read(buf).also { numRead = it } >= 0) {
                out.write(buf, 0, numRead)
            }
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private val String.hexAsByteArray
        inline get() = this.chunked(2).map { it.uppercase(Locale.getDefault()).toInt(16).toByte() }.toByteArray()

    /**
     * Input encrypted String represented in HEX
     * @return a string decrypted in plain text
     */
    @Throws(Exception::class)
    fun decrypt(hexCipherText: String?): String {
        return String(dcipher!!.doFinal(hexCipherText?.hexAsByteArray))
    }

    fun decrypt(ciphertext: ByteArray?): String? {
        return try {
            String(dcipher!!.doFinal(ciphertext))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        private fun getMD5(input: String): ByteArray? {
            return try {
                val bytesOfMessage = input.toByteArray(charset("UTF-8"))
                val md = MessageDigest.getInstance("MD5")
                md.digest(bytesOfMessage)
            } catch (e: Exception) {
                null
            }
        }

        const val HEXES = "0123456789ABCDEF"
        fun byteToHex(raw: ByteArray?): String? {
            if (raw == null) {
                return null
            }
            var result = ""
            for (i in raw.indices) {
                result += ((raw[i].toInt() and 0xff) + 0x100).toString(16).substring(1)

            }
            return result
        }

        fun hexToByte(hexString: String): ByteArray {
            val len = hexString.length
            val ba = ByteArray(len / 2)
            var i = 0
            while (i < len) {
                ba[i / 2] = ((hexString[i].digitToIntOrNull(16)
                    ?: (-1 shl 4)) + hexString[i + 1].digitToIntOrNull(16)!!).toByte()
                i += 2
            }
            return ba
        }

        fun asHex(bytes : ByteArray): String {
            val stringBuffer = StringBuffer(bytes.size * 2)
            for (i in bytes.indices) {
                if (bytes[i].toInt() and 0xff < 16) stringBuffer.append("0")
                stringBuffer.append(((bytes[i].toInt() and 0xFF).toLong()).toString(16))
            }
            return stringBuffer.toString()
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val (key, data, action) = args.takeIf { it.size >= 3 } ?: run {
                println("error: missing one or more arguments. Usage: AesCryptUtil key data <enc|dec>")
                return
            }

            val err = when {
//                key == null -> "error: no key"
                key.length < 32 -> "error: key length less than 32 bytes"
//                data == null || action == null -> "error: no data"
                action !in listOf("enc", "dec") -> "error: invalid action"
                else -> null
            }

            val result = err ?: try {
                val encrypter = AesCryptUtil(key)
                if (action == "enc") encrypter.encrypt(data) else encrypter.decrypt(data)
            } catch (e: Exception) {
                "error : Exception in performing the requested operation : $e"
            }

            println(result)
        }

    }
}