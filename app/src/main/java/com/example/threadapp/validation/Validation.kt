package com.example.threadapp.validation

import androidx.compose.ui.text.input.TextFieldValue

class Validation {

    companion object {

        fun isFormDataValid(filedList: ArrayList<TextFieldValue>): Boolean {
            return filedList.all { it.text.trim().isNotEmpty() }
        }
    }
}