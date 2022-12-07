package com.dimitrisligi.agentapro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dimitrisligi.agentapro.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import data.User

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    private var generatedUser: User? = null

    //Fields
    private var _name: String? = null
    private var _lastname: String? = null
    private var _email: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegisterNewUser.setOnClickListener {
            checkIfFieldAreCorrect()
        }

    }

    private fun registerNewUserToFirebase(email: String, password: String, name: String, lastname: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {  authResult ->
                if (!authResult.isSuccessful){
                    return@addOnCompleteListener
                }
                generatedUser = User(authResult.result.user!!.uid,name,lastname,email,null,null)
                toastSomething(getString(R.string.str_register_successful))
                Intent(this,MainActivity::class.java).also {
                    it.putExtra("user_name", name)
                    it.putExtra("user_lastname", lastname)
                    it.putExtra("user_email", email)
                    it.putExtra("user_UID", authResult.result.user!!.uid)
                    startActivity(it)
                }
            }.addOnFailureListener {
                toastSomething(it.message.toString())
            }
    }

    private fun checkIfFieldAreCorrect() {
        _name = binding.etUserName.text.toString().trim()
        _lastname = binding.etUserLastname.text.toString().trim()
        _email = binding.etUserEmail.text.toString().trim()

        if(_name.isNullOrBlank()
            || _lastname.isNullOrBlank()
            || _email.isNullOrBlank()
            || binding.etUserPassword.text.isNullOrBlank()
            || binding.etUserPasswordRepeat.text.isNullOrBlank()){
            toastSomething(getString(R.string.str_one_of_the_fields_is_empty))
            return
        }else if(binding.etUserPassword.text.toString().trim() != binding.etUserPasswordRepeat.text.toString().trim()){
                toastSomething("Οι κωδικοί που δώσατε δεν ταιριάζουν μεταξύ τους παρακαλώ ξαναπροσπαθήστε")
            return
        }else{
            //---------Creating a new  User to firebase------------
            registerNewUserToFirebase(_email!!, binding.etUserPassword.text.toString().trim(),
                _name!!, _lastname!!
            )
        }
    }


    private fun toastSomething(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
