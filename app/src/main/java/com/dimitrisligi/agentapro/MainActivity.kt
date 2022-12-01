package com.dimitrisligi.agentapro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dimitrisligi.agentapro.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}