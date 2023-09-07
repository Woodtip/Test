package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.test.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrieveOrder()
        binding.btnGoBack.setOnClickListener { finish() }
    }

    @SuppressLint("SetTextI18n")
    fun retrieveOrder() {
        val order = intent.getParcelableExtra<Order>("EXTRA_ORDER")

        binding.tvTemp.text = order.toString()
    }

}