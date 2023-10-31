package com.example.threadapp.navigation

sealed class Routes(val route:String){

    object Home:Routes("home")
    object AddThread:Routes("add_thread")
    object SearchThread:Routes("search")
    object Profile:Routes("profile")
    object Notification:Routes("notification")
    object Splash:Routes("splash")
    object BottomNav:Routes("bottom_nav")

}
