package com.dimitrisligi.agentapro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dimitrisligi.agentapro.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import data.User
import utils.Utilities

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private var _currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadUserData()
    }

    private fun loadUserData() {
        getCurrentUser()
        setUpViews()
        postUserDetails()
    }

    private fun setUpViews() {
        binding.btnCreateNewClient.setOnClickListener {
            Intent(this,CreateNewClient::class.java).also {
                startActivity(it)
            }
        }
        binding.btnLogOut.setOnClickListener {
            Intent(this,LoginActivity::class.java).also {
                firebaseAuth.signOut()
                startActivity(it)
                finish()
            }
        }
    }

    private fun postUserDetails() {
        binding.tvEmail.text = _currentUser?.email

    }

    private fun getCurrentUser() {
        firebaseAuth = FirebaseAuth.getInstance()
        _currentUser = firebaseAuth.currentUser
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn(){
        if(_currentUser == null){
            Intent(this,LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
    }

    private fun toastAMessage(message: String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }
}