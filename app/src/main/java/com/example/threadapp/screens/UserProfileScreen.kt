package com.example.threadapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.example.threadapp.viewmodels.AuthViewModel
import com.example.threadapp.widgets.ReusableProfileScreen

@Composable
fun UserProfileScreen(controller: NavHostController,uid: String) {
    val authViewModel = remember { AuthViewModel() }

    val profileData by authViewModel.profileData.observeAsState()

    authViewModel.getProfileData(uid)
    if (profileData != null) {
        ReusableProfileScreen(userProfileData = profileData!!)
    }
}