package com.sampleapp.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun ViewGroup.inflate(layoutResId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutResId, this, attachToRoot)

@SuppressWarnings("PrintStackTrace")
fun Context.openBrowser(url: String) {
    try {
        val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(myIntent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "No application can handle this request. Please install a browser", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }
}

fun Context.getScreen(): Pair<Int, Int> {
    val dm = resources.displayMetrics
    return Pair(dm.widthPixels, dm.heightPixels)
}
