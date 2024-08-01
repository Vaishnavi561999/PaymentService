package com.ihcl.payment.util

import com.ihcl.payment.exception.InternalServerException
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMKeyPair
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import javax.xml.bind.DatatypeConverter
import java.io.FileReader
import java.io.IOException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.PrivateKey
import java.security.Signature
import java.security.NoSuchAlgorithmException
import java.security.InvalidKeyException
import java.security.SignatureException
import java.security.Security
import java.security.KeyPair

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class JuspaySignatureGenerationUtil {
    val log: Logger = LoggerFactory.getLogger(javaClass)
     fun sign(payload:String, filePath:String,  urlEncodeSignature:Boolean):String{
        try {
            val privateKey: PrivateKey? = readPrivateKeyFromFile(filePath)
            val privateSignature: Signature = Signature.getInstance("SHA256withRSA")
            privateSignature.initSign(privateKey)
            privateSignature.update(payload.toByteArray(StandardCharsets.UTF_8))
            val signature: ByteArray = privateSignature.sign()
            log.info("signature generated as $signature")
            return if (urlEncodeSignature) {
                URLEncoder.encode(DatatypeConverter.printBase64Binary(signature), "UTF-8")
            } else {
                DatatypeConverter.printBase64Binary(signature)
            }
        } catch (e: IOException) {
            log.info("Exception occured as ${e.printStackTrace()}")
            throw InternalServerException(e.message)
        } catch (e: NoSuchAlgorithmException) {
            log.info("Exception occured as ${e.printStackTrace()}")
            throw InternalServerException(e.message)
        } catch (e: InvalidKeyException) {
            log.info("Exception occured as ${e.printStackTrace()}")
            throw InternalServerException(e.message)
        } catch (e: SignatureException) {
            log.info("Exception occured as ${e.printStackTrace()}")
            throw InternalServerException(e.message)
        }
        return "sign-failed"
    }

    @Throws(IOException::class)
    private fun readPrivateKeyFromFile(filePath: String): PrivateKey? {
        log.info("Private Key file found at: $filePath")
        Security.addProvider(BouncyCastleProvider())
        val pemParser = PEMParser(
            FileReader(filePath)
        )
        val converter: JcaPEMKeyConverter = JcaPEMKeyConverter().setProvider("BC")
        val pemKeyPair: PEMKeyPair = pemParser.readObject() as PEMKeyPair
        val keyPair: KeyPair = converter.getKeyPair(pemKeyPair)
        return keyPair.private
    }
}