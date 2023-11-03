package com.example.threadapp.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.threadapp.model.ResultModel
import com.example.threadapp.model.ThreadPostModel
import com.example.threadapp.model.UserModel
import com.example.threadapp.util.Util
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.Date
import java.util.UUID

class AddThreadViewModel : ViewModel() {

    private val firebaseStorage = Firebase.storage.reference

    private val firebaseFireStore = FirebaseFirestore.getInstance()

    private val _result = MutableLiveData<ResultModel>()
    val result: LiveData<ResultModel> = _result

    private val _showLoader = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> = _showLoader

    private val _postData = MutableLiveData<List<Pair<UserModel, ThreadPostModel>>>()
    val postData: LiveData<List<Pair<UserModel, ThreadPostModel>>> = _postData

    init {
        fetchThread {
            _postData.postValue(it)
        }
    }

    fun createPost(uid: String, description: String, images: Uri) {
        _showLoader.postValue(true)
        val postId = UUID.randomUUID()
        val storageRef = firebaseStorage.child("post/${postId}.jpg")

        storageRef.putFile(images).addOnCompleteListener {
            storageRef.downloadUrl.addOnSuccessListener {
                savePostIntoDatabase(uid, postId.toString(), description, it.toString())
            }.addOnFailureListener {
                _result.postValue(ResultModel("failed", "something went wrong"))
            }
        }
    }

    private fun savePostIntoDatabase(
        uid: String,
        potsId: String,
        description: String,
        urlList: String
    ) {
        val ref = firebaseFireStore.collection("post")
        val postModel = ThreadPostModel(uid, description, urlList, Util.convertDateToString(Date()))
        ref.document(potsId).set(postModel).addOnSuccessListener {
            _showLoader.postValue(false)
            _result.postValue(ResultModel("success", "post successfully"))
        }.addOnFailureListener {
            _result.postValue(ResultModel("failed", "something went wrong"))
            _showLoader.postValue(false)
        }
    }

    private fun fetchThread(onResultList: (List<Pair<UserModel, ThreadPostModel>>) -> Unit) {
        val ref = firebaseFireStore.collection("post").orderBy("createdAt")

        val list = mutableListOf<Pair<UserModel, ThreadPostModel>>()

        ref.get().addOnSuccessListener { querySnapshot ->

            for (threads in querySnapshot) {
                val threadPostModel = ThreadPostModel(
                    threads.get("uid").toString(),
                    threads.get("description").toString(),
                    threads.get("image").toString(),
                    threads.get("createdAt").toString(),
                )
                fetchUser(threadPostModel) {
                    list.add(0, it to threadPostModel)
                    //condition is used because firebase take some time to  return data when you are get multiple data at same time
                    if (list.size == querySnapshot.size()) {
                        onResultList(list)
                    }
                }
            }

        }
    }

    private fun fetchUser(thread: ThreadPostModel, onResult: (UserModel) -> Unit) {
        firebaseFireStore.collection("users").document(thread.uid).get().addOnSuccessListener {
            val user = it.toObject(UserModel::class.java)
            user?.let(onResult)
        }.addOnFailureListener {
            _result.postValue(ResultModel("failed", "something went wrong"))
        }
    }

}