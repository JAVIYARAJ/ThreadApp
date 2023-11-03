package com.example.threadapp.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.threadapp.navigation.Routes
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Singleton

class Util {

    companion object {
        fun goTo(
            navHostController: NavHostController,
            route: String,
        ) {
            navHostController.navigate(route) {
                popUpTo(navHostController.graph.startDestinationId) {
                    inclusive = true
                }
            }
        }

        fun showToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        fun generateHashPassword(password: String): String {
            return BCrypt.withDefaults().hashToString(12, password.toCharArray())
        }

        fun verifyPassword(password: String, hashedPassword: String): Boolean {
            return BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
        }

        fun convertDateToString(date: Date): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            return inputFormat.format(date)
        }


    }

}