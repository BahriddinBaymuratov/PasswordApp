package com.example.passwordapp.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.ByteArrayOutputStream

object Objects {
    val regions = listOf(
        "Andijan",
        "Buxoro",
        "Fargona",
        "Guliston",
        "Namangan",
        "Samarqand",
        "Toshkent",
        "Jizzax",
        "Qashqadaryo",
        "Surxondaryo",
        "Xorazm",
        "Navoiy",
        "Qoraqalpog'iston"
    )
    val genders = arrayOf("Erkak", "Ayol")
}

fun Fragment.toast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun ImageView.toByteArray(): ByteArray {
    val bitmap = (this.drawable as BitmapDrawable).bitmap
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}
fun ByteArray.toBitmap(): Bitmap? {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}
