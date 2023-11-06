package com.example.threadapp.model

import com.google.firebase.firestore.QueryDocumentSnapshot

class ThreadPostModel(
    val uid: String,
    val description: String,
    val image: List<String>,
    val createdAt: String
) {
    companion object {
        fun fromSnapShot(snapshot: QueryDocumentSnapshot): ThreadPostModel {
            val uid = snapshot["uid"].toString()
            val description = snapshot["description"].toString()
            val image = snapshot["image"] as List<String>
            val createdAt = snapshot["createdAt"].toString()
            return ThreadPostModel(uid, description, image, createdAt)
        }

    }
}
