package com.example.appforphone

import java.util.regex.Pattern

class CredentialsManager {
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun isEmailValid(mail: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(mail).matches()
    }



    fun isPasswordValid(pass: String):Boolean{
        if(pass.length>=8 && pass.count(Char::isDigit)>0 && pass.any {it in "!,+^-_"})
            return true
        return false
    }
}