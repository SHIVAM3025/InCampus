package com.demo.incampus.Activity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.demo.incampus.Adapter.CustomPageAdapter
import com.demo.incampus.Model.ScreenItem
import com.demo.incampus.R
import kotlinx.android.synthetic.main.activity_walkthrough.*
import java.util.*

class WalkthroughActivity : AppCompatActivity() {

    private lateinit var introViewPagerAdapter: CustomPageAdapter
    private var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //if the system api is below marshmallow, set status bar to default black
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window.statusBarColor = Color.BLACK
        }
        setContentView(R.layout.activity_walkthrough)

        //fill list screen
        val mList: MutableList<ScreenItem> = ArrayList()
        addtolist(mList)
        introViewPagerAdapter =
            CustomPageAdapter(this, mList)
        screen_view_pager.adapter = introViewPagerAdapter
        tab_indicator.setupWithViewPager(screen_view_pager)

        button.setOnClickListener {
            position = screen_view_pager.currentItem
            if (position < mList.size) {
                position++
                screen_view_pager.currentItem = position
            }
            if (position == mList.size) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }

    }

    private fun addtolist(mList: MutableList<ScreenItem>) {
        mList.add(
            ScreenItem(
                "Get started",
                getString(R.string.get_started),
                R.drawable.logo
            )
        )
        mList.add(
            ScreenItem(
                "Find your community",
                getString(R.string.find_comm),
                R.drawable.logo
            )
        )
        mList.add(
            ScreenItem(
                "Connect with friends",
                getString(R.string.conn_frnd),
                R.drawable.logo
            )
        )
        mList.add(
            ScreenItem(
                "Explore Around",
                getString(R.string.explore),
                R.drawable.logo
            )
        )
    }
}
