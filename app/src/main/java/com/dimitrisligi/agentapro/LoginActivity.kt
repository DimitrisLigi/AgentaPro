package com.dimitrisligi.agentapro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dimitrisligi.agentapro.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        setUpViews()
    }

    private fun setUpViews() {
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }
        binding.btnLogin.setOnClickListener {
           login()
        }
    }
    private fun login(){
        val email = binding.etEmailLoginActivity.text.toString().trim()
        val password = binding.etPasswordLoginActivity.text.toString().trim()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener { auth ->
            if (!auth.isSuccessful){
                return@addOnCompleteListener
            }
            val myIntent = Intent(this, MainActivity::class.java)
            toastAMessage("Ola kala")
            startActivity(myIntent)
        }.addOnFailureListener {
            toastAMessage(it.message.toString())
        }
    }

    private fun toastAMessage(message: String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }
}