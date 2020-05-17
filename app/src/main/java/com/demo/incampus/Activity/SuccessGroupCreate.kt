package com.demo.incampus.Activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demo.incampus.R

class SuccessGroupCreate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //if the system api is below marshmallow, set status bar to default black
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window.statusBarColor = Color.BLACK
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_group_create)
    }

    fun done (view : View) {
        Toast.makeText(this, "Group Created", Toast.LENGTH_SHORT).show()
    }

    fun share (view : View) {
        Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
    }


}