package com.dimitrisligi.agentapro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import classes.Motor
import com.dimitrisligi.agentapro.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import data.Item
import enums.MotorBrand
import enums.MotorNm
import utils.Utilities

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var userAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}