package com.example.threadapp.util

import android.content.Context

class PreferenceHelper() {

    companion object {
        fun setBioOrLinkData(context: Context, bio: String?, link: String?) {
            val pref = context.getSharedPreferences("thread_app", Context.MODE_PRIVATE)
            val editor = pref.edit()
            if (bio != null) {
                editor.putString("bio", bio)
            } else {
                editor.putString("link", link)
            }
            editor.apply()
        }

        fun getBioOrLink(context: Context, key: String): String? {
            val pref = context.getSharedPreferences("thread_app", Context.MODE_PRIVATE)
            return pref.getString(key, null)
        }

        fun clearBioOrLink(context: Context) {
            val pref = context.getSharedPreferences("thread_app", Context.MODE_PRIVATE)
            val editor = pref.edit().also {
                it.remove("bio")
                it.remove("link")
                it.apply()
            }
        }
    }
}