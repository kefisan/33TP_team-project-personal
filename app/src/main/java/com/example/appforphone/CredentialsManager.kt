package com.example.appforphone

import java.util.regex.Pattern

class CredentialsManager {

    private val credentials: MutableMap<String,String> = mutableMapOf(
        //Pair("test@te.st","1234")
        "test@te.st" to "1234"
    )

    //pattern for the email
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    //validation functions

    //validating password
    fun isPasswordValid(pass: String):Boolean{
        if(pass.length>=8 && pass.count(Char::isDigit)>0 && pass.any {it in "!,+^-_"} || pass == "1234")
            return true
        return false
    }

    //validating email
    fun isEmailValid(mail: String): Boolean{
        return EMAIL_ADDRESS_PATTERN.matcher(mail).matches() || mail == "test@te.st"
    }

    fun isEmailTaken(email: String):Boolean{
        return credentials.containsKey(email.lowercase())
    }

    fun doesPasswordMatchEmail(email: String,password: String):Boolean{
        return credentials[email] == password
    }

    fun addEmailToMap(email: String,pass: String) {
        credentials.put(email.lowercase(), pass)
    }
}