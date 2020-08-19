package com.example.firebasetest.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class CheckForAFileTest {
    @Test
    fun `Check for invalid directory returns false`() {

        var result = CheckForAFile.fileExists("", "someName")
        assertThat(result).isFalse()
    }

    @Test
    fun `Check for invalid fileName returns false`() {

        var result = CheckForAFile.fileExists("/", "")
        assertThat(result).isFalse()
    }

    @Test
    fun `Check for valid directory and fileName returns true`() {

        var result = CheckForAFile.fileExists("/", "someName")
        assertThat(result).isTrue()
    }

}