package com.dimitrisligi.agentapro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dimitrisligi.agentapro.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import data.User
import utils.Utilities

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private var _currentUser: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadUserData()
    }

    private fun loadUserData() {
        getCurrentUser()
        postUserDetails()
    }

    private fun postUserDetails() {

        binding.tvUIDBanner.text = _currentUser.toString()
    }

    private fun getCurrentUser() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.email
        _currentUser = firebaseAuth.currentUser?.uid.toString()
        val ref = FirebaseDatabase.getInstance(Utilities.REALTIME_DATABASE_URL).getReference("/users/$_currentUser")
        val user = ref.database.reference.key
        binding.tvEmail.text = user.toString()
    }

    private fun toastAMessage(message: String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }
}