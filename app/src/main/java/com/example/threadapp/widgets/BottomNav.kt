package com.example.threadapp.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.threadapp.model.BottomNavItem
import com.example.threadapp.navigation.Routes
import com.example.threadapp.screens.AddThreadScreen
import com.example.threadapp.screens.HomeScreen
import com.example.threadapp.screens.NotificationScreen
import com.example.threadapp.screens.ProfileScreen
import com.example.threadapp.screens.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavbar(navController: NavHostController) {
    val controller = rememberNavController() //local scope controller for bottom icon routing

    Scaffold(bottomBar = { CustomBottomNavBar(controller) }) {
        NavHost(
            navController = controller,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(it)
        ) {

            composable(Routes.Home.route) {
                HomeScreen()
            }
            composable(Routes.SearchThread.route) {
                SearchScreen()
            }
            composable(Routes.AddThread.route) {
                AddThreadScreen(navController)
            }
            composable(Routes.Notification.route) {
                NotificationScreen()
            }
            composable(Routes.Profile.route) {
                ProfileScreen(navController)
            }

        }
    }
}

@Composable
fun CustomBottomNavBar(navController: NavHostController) {

    val backStackEntry = navController.currentBackStackEntryAsState()

    val bottomNavItems = listOf(
        BottomNavItem("Home", Routes.Home.route, Icons.Rounded.Home),
        BottomNavItem("Search", Routes.SearchThread.route, Icons.Rounded.Search),
        BottomNavItem("Add Thread", Routes.AddThread.route, Icons.Rounded.Add),
        BottomNavItem("Notification", Routes.Notification.route, Icons.Rounded.Notifications),
        BottomNavItem("Profile", Routes.Profile.route, Icons.Rounded.Person)
    )

    BottomAppBar(containerColor = Color.Black, contentColor = MaterialTheme.colorScheme.primary) {
        bottomNavItems.forEach {
            val selected = it.route == backStackEntry.value?.destination?.route

            NavigationBarItem(selected = selected, onClick = {
                navController.navigate(it.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }, icon = { Icon(imageVector = it.icon, contentDescription = it.title) })

        }
    }

}