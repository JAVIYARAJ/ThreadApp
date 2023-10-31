package com.example.threadapp.util

import androidx.navigation.NavHostController
import com.example.threadapp.navigation.Routes

class Util {

    companion object {

        fun goTo(navHostController: NavHostController, route: String) {
            navHostController.navigate(route) {
                popUpTo(navHostController.graph.startDestinationId) {
                    saveState = true
                }
            }
        }

    }

}