package com.irocky.pmkisanyojna.kisanyojna

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.irocky.pmkisanyojna.R
import com.irocky.pmkisanyojna.awasyojna.AwasNewRegUrban
import com.irocky.pmkisanyojna.awasyojna.AwasTrackUrban



class KisanYojna : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.irocky.pmkisanyojna.R.layout.activity_kisan_yojna)


        supportActionBar?.apply {
            title = "PM KIsan Yojna"
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(getDrawable(com.irocky.pmkisanyojna.R.color.teal_200))
        }


        val Aws1 = findViewById<LinearLayout>(R.id.ky1)
        Aws1.setOnClickListener {
            startActivity(Intent(this, NewRegisKisan::class.java))
        }

        val Aws2 = findViewById<LinearLayout>(R.id.ky2)
        Aws2.setOnClickListener {
            startActivity(Intent(this, Ekyc::class.java))
        }

        val Aws3 = findViewById<LinearLayout>(R.id.awsregurban3)
        Aws3.setOnClickListener {
            startActivity(Intent(this, AwasNewRegUrban::class.java))
        }



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
