package com.dimitrisligi.agentapro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dimitrisligi.agentapro.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase
import data.User
import utils.Utilities

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private var _currentUser: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance(Utilities.REALTIME_DATABASE_URL)
        val ref = firebaseDatabase.reference
        val name: String? = intent.getStringExtra("user_name")
        val lastname: String? = intent.getStringExtra("user_lastname")
        val email: String? = intent.getStringExtra("user_email")
        val uid: String? = intent.getStringExtra("user_UID")
        _currentUser = User(uid!!,name!!,lastname!!,email!!,clientList = null, appointmentList = null)
        binding.tvUIDBanner.text = _currentUser!!.UID
        ref.child("Users").push().setValue(_currentUser).addOnCompleteListener {
            if (!it.isSuccessful){
                return@addOnCompleteListener
            }
           // toastAMessage("O logariasmos stin vasi dedomenwn dimiourgithike me epitixia")
        }.addOnFailureListener {
            toastAMessage(it.message.toString())
        }
    }
    private fun toastAMessage(message: String){
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }
}