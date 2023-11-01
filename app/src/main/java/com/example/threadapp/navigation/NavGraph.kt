package com.example.threadapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.threadapp.screens.AddThreadScreen
import com.example.threadapp.screens.HomeScreen
import com.example.threadapp.screens.LoginScreen
import com.example.threadapp.screens.NotificationScreen
import com.example.threadapp.screens.ProfileScreen
import com.example.threadapp.screens.RegisterScreen
import com.example.threadapp.screens.SearchScreen
import com.example.threadapp.screens.SplashScreen
import com.example.threadapp.widgets.BottomNavbar

@Composable
fun NavGraph(navHostController: NavHostController){

    NavHost(navController = navHostController, startDestination = Routes.Splash.route){
        composable(Routes.Splash.route){
            SplashScreen(navHostController)
        }

        composable(Routes.Home.route){
            HomeScreen()
        }
        composable(Routes.Notification.route){
            NotificationScreen()
        }
        composable(Routes.AddThread.route){
            AddThreadScreen()
        }
        composable(Routes.SearchThread.route){
            SearchScreen()
        }
        composable(Routes.Profile.route){
            ProfileScreen(navHostController)
        }
        composable(Routes.BottomNav.route){
            BottomNavbar(navHostController)
        }

        composable(Routes.Login.route){
            LoginScreen(navHostController = navHostController)
        }

        composable(Routes.Register.route){
            RegisterScreen(navHostController = navHostController)
        }

    }

}