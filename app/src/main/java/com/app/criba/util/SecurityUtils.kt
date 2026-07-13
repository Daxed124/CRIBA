package com.app.criba.util

import java.security.MessageDigest

/** Utilidades de seguridad para el manejo de credenciales. */
object SecurityUtils {

    /**
     * Devuelve el hash SHA-256 (en hexadecimal) de la contraseña.
     * Las contraseñas nunca se guardan en texto plano: se almacena solo su hash.
     */
    fun sha256(input: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
