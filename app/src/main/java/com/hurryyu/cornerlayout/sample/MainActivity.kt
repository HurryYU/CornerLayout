package com.hurryyu.cornerlayout.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hurryyu.cornerlayout.sample.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cornerLayout.bannerText = "HurryYu"
    }
}