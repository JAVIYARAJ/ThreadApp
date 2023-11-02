package com.example.threadapp.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threadapp.model.ThreadPostModel
import com.example.threadapp.model.UserModel
import com.example.threadapp.util.Constant
import com.example.threadapp.validation.Validation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class AuthViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseRef = firebaseDatabase.getReference(Constant.FIREBASE_DATABASE_ROOT)

    private val firebaseStorage = Firebase.storage.reference
    private val storageRef =
        firebaseStorage.child("${Constant.FIREBASE_STORAGE_ROOT}/${UUID.randomUUID()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser>()
    val firebaseUser: LiveData<FirebaseUser> = _firebaseUser

    private val _profileData = MutableLiveData<UserModel>()
    val profileData: LiveData<UserModel> = _profileData

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        _firebaseUser.postValue(firebaseAuth.currentUser)
    }

    fun register(
        name: String,
        username: String,
        email: String,
        password: String,
        bio: String,
        imageUri: Uri
    ) {

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {

            saveDataToFirebase(
                name,
                username,
                email,
                password,
                bio,
                imageUri,
                it.user?.uid
            )
        }.addOnFailureListener {
            _errorMessage.postValue(it.message)
        }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            _errorMessage.postValue("Login Successfully")
        }.addOnFailureListener {
            _errorMessage.postValue(it.message)
        }
    }

    private fun saveDataToFirebase(
        name: String,
        username: String,
        email: String,
        password: String,
        bio: String,
        imageUri: Uri,
        uid: String?
    ) {
        storageRef.putFile(imageUri).addOnCompleteListener {
            storageRef.downloadUrl.addOnCompleteListener {
                saveData(
                    name = name,
                    username = username,
                    email = email,
                    password = password,
                    bio = bio,
                    imageUrl = it.toString(),
                    uid = uid
                )
            }
        }
    }

    private fun saveData(
        name: String,
        username: String,
        email: String,
        password: String,
        bio: String,
        imageUrl: String,
        uid: String?
    ) {
        val user = UserModel(
            uid = uid,
            name = name,
            username = username,
            email = email,
            password = password,
            bio = bio,
            imageUrl = imageUrl
        )

        databaseRef.child(uid!!).setValue(user).addOnSuccessListener {
            _errorMessage.postValue("Register successfully")
        }.addOnFailureListener {
            _errorMessage.postValue(it.message)
        }

    }

    fun logout() {
        firebaseAuth.signOut()
        _firebaseUser.postValue(null)
    }

    fun getProfileData() {
        databaseRef.child(firebaseAuth.uid!!).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                _errorMessage.postValue(error.message)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                _profileData.postValue(snapshot.getValue(UserModel::class.java))
            }
        })
    }

    fun createPost(uid: String, description: String, images: List<Uri>) {

        val storageRef = firebaseStorage.child("post/${UUID.randomUUID()}.jpg")
        val urlList = mutableListOf<String>()
        images.forEach { uri ->
            storageRef.putFile(uri).addOnCompleteListener {
                storageRef.downloadUrl.addOnCompleteListener {
                    Log.e("TAG", "createPost: $it", )
                    urlList.add(it.toString())
                }
            }
        }

        savePostIntoDatabase(uid, description, urlList)

    }

    private fun savePostIntoDatabase(
        uid: String,
        description: String,
        urlList: MutableList<String>
    ) {
        Log.e("TAG", "savePostIntoDatabase: ${urlList}", )
        val ref = firebaseDatabase.getReference("post")
        val postModel = ThreadPostModel(uid, description, urlList)
        ref.setValue(postModel).addOnCompleteListener {
            if (it.isSuccessful) {
                _errorMessage.postValue("Post Successfully")
            }
        }
    }

}