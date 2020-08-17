package com.example.firebasetest.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class CheckInputLoginTest{
    @Test
    fun `empty user name and password false`(){
        val result = CheckInputLogin.validateRegistratonInput(
            "",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `input values for name and password true`(){
        val result = CheckInputLogin.validateRegistratonInput(
            "angel",
            "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `no password input values returns false`(){
        val result = CheckInputLogin.validateRegistratonInput(
            "angel",
            ""
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `no name input values returns false`(){
        val result = CheckInputLogin.validateRegistratonInput(
            "",
            "123"
        )
        assertThat(result).isFalse()
    }
}