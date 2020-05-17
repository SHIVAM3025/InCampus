package com.demo.incampus.Activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.incampus.Adapter.CategoriesAdapter
import com.demo.incampus.Model.BottomSheetDialog
import com.demo.incampus.Model.Categories
import com.demo.incampus.R
import kotlinx.android.synthetic.main.activity_categories.*
import java.util.*


class CategoriesActivity : AppCompatActivity(), CategoriesAdapter.onItemListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        //if the system api is below marshmallow, set status bar to default black
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window.statusBarColor = Color.BLACK
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        //Linear layout covering the amount of screen
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val height: Int = size.y
        val params: ViewGroup.LayoutParams = ll.layoutParams
        params.height = (height * 0.15).toInt()
        ll.layoutParams = params

        //Recycler View Code
        val categoriesList: ArrayList<Categories> = ArrayList<Categories>()
        val recyclerView: RecyclerView = findViewById(R.id.categoiesRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        categoriesList.add(Categories("Arts", "120 topics - 4k articles", R.drawable.arts))
        categoriesList.add(
            Categories(
                "Technology",
                "100 topics - 3k articles",
                R.drawable.technology
            )
        )
        categoriesList.add(Categories("Business", "56 topics - 1k articles", R.drawable.business))
        categoriesList.add(
            Categories(
                "Dance & Music",
                "78 topics - 700 articles",
                R.drawable.dancemusic
            )
        )
        categoriesList.add(
            Categories(
                "Entertainment",
                "45 topics - 980 articles",
                R.drawable.entertainment
            )
        )
        categoriesList.add(
            Categories(
                "Educational",
                "156 topics - 2k articles",
                R.drawable.educational
            )
        )
        categoriesList.add(Categories("Politics", "54 topics - 150 articles", R.drawable.politics))
        categoriesList.add(Categories("Sports", "220 topics - 5k articles", R.drawable.sports))

        val adapter = CategoriesAdapter(this, categoriesList, this)
        recyclerView.adapter = adapter

        //bottomsheet dialog

        //when this activity is about to be launched we need to check if intro has opened before or not
        val letsgo = findViewById<Button>(R.id.letsgo)
        letsgo.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            if (bottomSheetDialog.restorePrefData()) {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            } else {
                bottomSheetDialog.show(supportFragmentManager, "BottomSheet")
            }

        }

    }

    override fun onItemClick(position: Int, heading: String) {
        val intent = Intent(applicationContext, SocietiesActivity::class.java)
        intent.putExtra("title", heading)
        startActivity(intent)
    }
}
