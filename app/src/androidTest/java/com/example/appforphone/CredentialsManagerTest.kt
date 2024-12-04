package com.example.appforphone

import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert
import org.junit.Test

class CredentialsManagerTest {
    //Register tests
    //Email tests

    val credentialManager = CredentialsManager()
    @Test
    fun emailExists_ReturnTrue(){
        val email = "test@te.st"
        assertTrue(credentialManager.isEmailTaken(email))

    }

    @Test
    fun emailDoesNotExist_ReturnFalse(){
        val email = "amog@us.sus"
        assertFalse(credentialManager.isEmailTaken(email))
    }
}