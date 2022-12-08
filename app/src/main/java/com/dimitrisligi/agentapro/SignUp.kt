package com.dimitrisligi.agentapro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dimitrisligi.agentapro.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import data.User
import utils.Utilities

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding


    private var _generatedUser: User? = null

    //Fields
    private var _userName: String? = null
    private var _userLastname: String? = null
    private var _userEmail: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegisterNewUser.setOnClickListener {
            checkIfFieldsAreCorrect()
        }

    }

    private fun checkIfFieldsAreCorrect() {
        _userName = binding.etUserName.text.toString().trim()
        _userLastname = binding.etUserLastname.text.toString().trim()
        _userEmail = binding.etUserEmail.text.toString().trim()

        //Checking if the fields are null or blank
        if(_userName.isNullOrBlank()
            || _userLastname.isNullOrBlank()
            || _userEmail.isNullOrBlank()
            || binding.etUserPassword.text.isNullOrBlank()
            || binding.etUserPasswordRepeat.text.isNullOrBlank()){
            toastSomething(getString(R.string.str_one_of_the_fields_is_empty))
            return
        }//Passwords check
        else if(binding.etUserPassword.text.toString().trim()
            != binding.etUserPasswordRepeat.text.toString().trim()){
            if (binding.etUserPassword.text.length < 6){
                toastSomething("Ο κωδικός σας θα πρέπει να είναι περιέχει τουλάχιστον 7 χαρακτήρες")
                return
            }else{
                toastSomething("Οι κωδικοί που δώσατε δεν ταιριάζουν μεταξύ τους παρακαλώ ξαναπροσπαθήστε")
                return
            }
        }else{
            //---------Creating a new  User to firebase------------
            registerNewUserToFirebase(_userEmail!!, binding.etUserPassword.text.toString().trim(),
                _userName!!, _userLastname!!)
        }
    }

    private fun registerNewUserToFirebase(email: String,
                                          password: String,
                                          name: String,
                                          lastname: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {  authResult ->
                if (!authResult.isSuccessful){
                    return@addOnCompleteListener
                }
                _generatedUser = User(authResult.result.user!!.uid,name,lastname,email,null,null)
                // First database
//                saveUserToFirebaseDatabase()
                // Second database
                saveUserToFirebaseFirestore(_generatedUser)
                toastSomething(getString(R.string.str_register_successful))
                Intent(this,MainActivity::class.java).also {
                    startActivity(it)
                }
            }.addOnFailureListener {
                toastSomething(it.message.toString())
            }
    }

    private fun saveUserToFirebaseDatabase() {
        val uid = FirebaseAuth.getInstance().uid.toString()
        val ref = FirebaseDatabase.getInstance(Utilities.REALTIME_DATABASE_URL).getReference("/users/$uid")
        ref.setValue(_generatedUser).addOnCompleteListener { task ->
            if(!task.isSuccessful){
                return@addOnCompleteListener
            }
        }.addOnFailureListener {
            toastSomething(it.message.toString())
        }
    }

    private fun saveUserToFirebaseFirestore(user: User?){
        if(user == null) return
        val db = Firebase.storage(Utilities.FIREBASE_STORAGE).reference
        val userStorageRef: StorageReference? = db.child("myUsers")
    }

    private fun toastSomething(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
