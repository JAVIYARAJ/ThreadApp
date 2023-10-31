package com.example.threadapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.threadapp.navigation.Routes

@Composable
fun LoginScreen(navHostController: NavHostController) {
    Text(text = "login screen", Modifier.clickable {
        navHostController.navigate(Routes.Register.route){
            popUpTo(navHostController.graph.startDestinationId){
                saveState=true
            }
        }
    })
}