package com.dimitrisligi.agentapro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dimitrisligi.agentapro.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnRegisterNewUser.setOnClickListener {
            checkIfFieldAreCorrect()
        }

    }

    private fun registerNewUserToFirebase(email: String, password: String){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful){
                    return@addOnCompleteListener
                }
                toastSomething(getString(R.string.str_register_successful))
                //Starting MAIN ACTIVITY
                startActivity(Intent(this,MainActivity::class.java))
            }.addOnFailureListener {
                toastSomething(it.message.toString())
            }
    }

    private fun checkIfFieldAreCorrect() {
        val name: String = binding.etUserName.text.toString().trim()
        val lastname: String = binding.etUserLastname.text.toString().trim()
        val email: String = binding.etUserEmail.text.toString().trim()
        val password: String = binding.etUserPassword.text.toString().trim()
        val passwordVerification: String = binding.etUserPasswordRepeat.text.toString().trim()

        if(binding.etUserName.text.isNullOrBlank()
            || binding.etUserLastname.text.isNullOrBlank()
            || binding.etUserEmail.text.isNullOrBlank()
            || binding.etUserPassword.text.isNullOrBlank()
            || binding.etUserPasswordRepeat.text.isNullOrBlank()){
            toastSomething(getString(R.string.str_one_of_the_fields_is_empty))
            return
        }else if(binding.etUserPassword.text.toString().trim() != binding.etUserPasswordRepeat.text.toString().trim()){
                toastSomething("Οι κωδικοί που δώσατε δεν ταιριάζουν μεταξύ τους παρακαλώ ξαναπροσπαθήστε")
            return
        }else{
                registerNewUserToFirebase(email, password)
        }
    }


    private fun toastSomething(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
