package com.demo.incampus.Activity

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.demo.incampus.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        login_btn.setOnClickListener() {
            val int = Intent(this, SignInActivity::class.java)
            startActivity(int, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }
        signup_btn.setOnClickListener() {
            val int = Intent(this, UsernameActivity::class.java)
            startActivity(int, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        //Relative layout covering the amount of screen
        val relLayout = findViewById<RelativeLayout>(R.id.relativeLayout)
        val loginRelLayout = findViewById<RelativeLayout>(R.id.loginRelativeLayout)
        val signupRelLayout = findViewById<RelativeLayout>(R.id.signupRelativeLayout)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val height = size.y
        val params = arrayOf(
            relLayout.layoutParams,
            loginRelLayout.layoutParams,
            signupRelLayout.layoutParams
        )
        params[0].height = (height * 0.70).toInt()
        params[1].height = (height * 0.20).toInt()
        params[2].height = (height * 0.10).toInt()

        relLayout.layoutParams = params[0]
        loginRelLayout.layoutParams = params[1]
        signupRelLayout.layoutParams = params[2]
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
