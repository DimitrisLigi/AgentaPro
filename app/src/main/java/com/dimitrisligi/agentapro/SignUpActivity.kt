package com.dimitrisligi.agentapro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dimitrisligi.agentapro.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import data.Appointment
import data.Client
import data.User
import utils.Utilities

class SignUpActivity : AppCompatActivity() {
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
                toastSomething("?? ?????????????? ?????? ???? ???????????? ???? ?????????? ???????????????? ?????????????????????? 7 ????????????????????")
                return
            }else{
                toastSomething("???? ?????????????? ?????? ???????????? ?????? ???????????????????? ???????????? ???????? ???????????????? ??????????????????????????????")
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
                //Creating new user
                _generatedUser = User(authResult.result.user!!.uid,
                    name,
                    lastname,
                    email,
                    null,
                    null)
                //Creating empty client and appointment List
                val tempClientList = mutableListOf<Client>()
                val tempAppointmentList = mutableListOf<Appointment>()
                _generatedUser!!.appointmentList = tempAppointmentList
                _generatedUser!!.clientList = tempClientList
                //First database
                saveUserToFirebaseDatabase()
                // Second database
                //saveUserToFirebaseFirestore(_generatedUser)
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
        val db = Firebase.storage(Utilities.FIREBASE_STORAGE).getReference("myUsers")
        val userStorageRef: StorageReference? = db.child("Clients")
    }

    private fun toastSomething(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
