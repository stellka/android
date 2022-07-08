package edu.skillbox.datastorage

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

private const val PREFERENCE_NAME = "prefs_name"
private const val SHARED_PREFS_KEY = "shared_prefs_key"

@Parcelize
class Repository : Parcelable {

    @IgnoredOnParcel
    var localValue = ""

    private fun getDataFromSharedPreference(context : Context) :String? {
        val prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        return prefs.getString(SHARED_PREFS_KEY, null)
    }


    private fun getDataFromLocalVariable() :String? {
        return localValue
    }

    fun saveText(text: String, context: Context){
        val prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        localValue = text
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(SHARED_PREFS_KEY, text)
        editor.apply()

    }

    fun clearText(context: Context) {
        localValue = ""
        val prefs = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

    fun getText(context: Context): String {
        return when{
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            getDataFromSharedPreference(context) != null -> getDataFromSharedPreference(context)!!
            else -> "there are no sources"
        }
    }
}