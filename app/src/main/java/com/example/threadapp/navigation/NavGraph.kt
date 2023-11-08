package com.example.threadapp.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.threadapp.screens.AddThreadScreen
import com.example.threadapp.screens.EditProfileBioLinkScreen
import com.example.threadapp.screens.EditProfileScreen
import com.example.threadapp.screens.HomeScreen
import com.example.threadapp.screens.LoginScreen
import com.example.threadapp.screens.NotificationScreen
import com.example.threadapp.screens.PrivacyScreen
import com.example.threadapp.screens.ProfileScreen
import com.example.threadapp.screens.RegisterScreen
import com.example.threadapp.screens.SearchScreen
import com.example.threadapp.screens.SettingScreen
import com.example.threadapp.screens.SplashScreen
import com.example.threadapp.screens.UserProfileScreen
import com.example.threadapp.widgets.BottomNavbar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) {
            SplashScreen(navHostController)
        }

        composable(Routes.Home.route) {
            HomeScreen(bottomController = null, mainController = navHostController)
        }

        composable(Routes.Notification.route) {
            NotificationScreen()
        }
        composable(Routes.AddThread.route) {
            AddThreadScreen(navHostController)
        }
        composable(Routes.SearchThread.route) {
            SearchScreen(navHostController)
        }
        composable(Routes.Profile.route) {
            ProfileScreen(navHostController)
        }
        composable(Routes.EditProfile.route) {
            EditProfileScreen(navHostController)
        }

        composable(Routes.EditBioLinkProfile.route) {
            val isEdit = it.arguments?.getString("data")
            val value=it.arguments?.getString("value")
            EditProfileBioLinkScreen(navHostController, isEdit == "true", value = value!!)
        }

        composable(Routes.BottomNav.route) {
            BottomNavbar(navHostController)
        }

        composable(Routes.Login.route) {
            LoginScreen(navHostController = navHostController)
        }

        composable(Routes.Register.route) {
            RegisterScreen(navHostController = navHostController)
        }

        composable(Routes.Privacy.route) {
            PrivacyScreen(controller = navHostController)
        }

        composable(Routes.Setting.route) {
            SettingScreen(controller = navHostController)
        }

        composable(Routes.UserProfile.route) {
            val uid = it.arguments?.getString("data");
            UserProfileScreen(controller = navHostController, uid = uid!!)
        }

    }

}