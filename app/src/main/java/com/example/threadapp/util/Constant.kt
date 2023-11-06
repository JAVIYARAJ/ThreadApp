package com.example.threadapp.util

import android.Manifest
import android.os.Build
import androidx.navigation.compose.composable
import com.example.threadapp.navigation.Routes
import com.example.threadapp.screens.HomeScreen

class Constant {
    companion object {

        val imagePermission = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        //firebase root nodes
        const val FIREBASE_STORAGE_ROOT = "users"

    }
}