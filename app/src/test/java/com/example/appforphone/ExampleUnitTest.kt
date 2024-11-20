package com.example.appforphone

import org.junit.Test

import org.junit.Assert.*

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class CredentialsOk {
    @Test
    fun givenEmptyEmail_thenReturnFalse(){
        val credentialsManager = CredentialsManager()
        val email = ""

        val result = credentialsManager.isEmailValid(email)
        assertFalse(result)
    }

    @Test
    fun givenGoodEmailFormat_thenReturnTrue(){
        val credentialsManager = CredentialsManager()
        val email = "tested@te.st"

        val result = credentialsManager.isEmailValid(email)
        assertTrue(result)
    }

    @Test
    fun givenBadEmailFormat_thenReturnTrue(){
        val credentialsManager = CredentialsManager()
        val email = "wrongEmailFormat"

        val result = credentialsManager.isEmailValid(email)
        assertTrue(result)
    }

}


