package com.enesyildirim.notes.domain.model

import android.annotation.SuppressLint
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val isEdited: String = "",
    val color: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFormatted(dateInMillis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        return formatter.format(Date(dateInMillis))
    }
}

val RedOrange = "Color(0xffffab91)"
val RedPink = "Color(0xfff48fb1)"
val BabyBlue = "Color(0xff81deea)"
val Violet = "Color(0xffcf94da)"
val LightGreen = "Color(0xffe7ed9b)"

class NoteException(message: String) : Exception(message)