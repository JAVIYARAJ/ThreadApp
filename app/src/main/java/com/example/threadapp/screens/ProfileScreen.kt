package com.example.threadapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.threadapp.R
import com.example.threadapp.navigation.Routes
import com.example.threadapp.viewmodels.AuthViewModel
import com.example.threadapp.widgets.ReusableProfileScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun ProfileScreen(controller: NavHostController) {
    val authViewModel = remember { AuthViewModel() }

    val profileData by authViewModel.profileData.observeAsState()

    authViewModel.getProfileData(FirebaseAuth.getInstance().currentUser!!.uid)


    if (profileData != null) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    controller.navigate(Routes.Privacy.route)
                }) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = "privacy_icon")
                }
                IconButton(onClick = {
                    controller.navigate(Routes.Setting.route)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_menu_icon),
                        contentDescription = "setting_icon"
                    )
                }
            }
            ReusableProfileScreen(userProfileData = profileData!!, threadCallBack = {

            }, callback1 = {
                controller.navigate(Routes.EditProfile.route)
            }, callback2 = {})
        }
    }

}
