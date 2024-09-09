package com.irocky.pmkisanyojna.awasyojna

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.irocky.pmkisanyojna.Khatauni
import com.irocky.pmkisanyojna.NewsActivity
import com.irocky.pmkisanyojna.R
import com.irocky.pmkisanyojna.kisanyojna.KisanYojna


class AwasYojna : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.irocky.pmkisanyojna.R.layout.activity_awas_yojna)


        supportActionBar?.apply {
            title = "Awas Yojna"
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(getDrawable(com.irocky.pmkisanyojna.R.color.teal_200))
        }


        val Aws1 = findViewById<RelativeLayout>(R.id.aws1)
        Aws1.setOnClickListener {
            startActivity(Intent(this, AwasYojnaList::class.java))
        }

        val Aws2 = findViewById<RelativeLayout>(R.id.aws2)
        Aws2.setOnClickListener {
            startActivity(Intent(this, AwasUrbanList::class.java))
        }

        val Aws3 = findViewById<RelativeLayout>(R.id.awsregurban3)
        Aws3.setOnClickListener {
            startActivity(Intent(this, AwasNewRegUrban::class.java))
        }

        val Aws4 = findViewById<RelativeLayout>(R.id.awstrackurban)
        Aws4.setOnClickListener {
            startActivity(Intent(this, AwasNewRegUrban::class.java))
        }
//bkjbjkbvkjxbvjkfsvjksfbjvbsfjvjkfsbvjsbfvj---------------------------------------------------------------------

//        val crd1 = findViewById<RelativeLayout>(R.id.aws1)
//        crd1.setOnClickListener {
////            loadInterstitialAd()
//            show_ad()
//            startActivity(Intent(this, AwasYojna::class.java))
//        }
//
//        val crd2 = findViewById<RelativeLayout>(R.id.card2)
//        crd2.setOnClickListener {
////            loadInterstitialAd()
//            show_ad()
//            startActivity(Intent(this, KisanYojna::class.java))
//        }
//
//        val crd3 = findViewById<RelativeLayout>(R.id.cardd3)
//        crd3.setOnClickListener {
////            loadInterstitialAd()
//            show_ad()
//            startActivity(Intent(this, Khatauni::class.java))
//        }



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
