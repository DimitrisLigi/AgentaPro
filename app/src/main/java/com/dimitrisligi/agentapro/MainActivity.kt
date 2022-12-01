package com.dimitrisligi.agentapro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import classes.Motor
import com.dimitrisligi.agentapro.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import data.Item
import enums.MotorBrand
import enums.MotorNm
import utils.Utilities

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef = FirebaseDatabase.getInstance("https://agentapro-e0f33-default-rtdb.europe-west1.firebasedatabase.app/").getReference(Utilities.DATABASE_NAME)

        //Save Data
        binding.btnSaveDataToFirebase.setOnClickListener {
            saveDataToFirebase()
        }
        //Display Data
        binding.btnDisplayData.setOnClickListener {
            readData(MotorBrand.Spanish.toString())
        }
        //Delete DB
        binding.btnDeleteAllDataFromDatabase.setOnClickListener{
            dbRef.database.reference.removeValue().addOnCompleteListener {
                Toast.makeText(this,"Database deleted!", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun readData(itemID: String){
        dbRef.child(itemID).get().addOnCompleteListener {
            if (!it.isSuccessful){
                return@addOnCompleteListener
            }else{
                binding.tvResult.text = it.result.child(itemID).value.toString()
            }
        }
    }


    private fun saveDataToFirebase() {
        val itemID = dbRef.push().key!!
        //val x = Item(itemID, Motor(MotorBrand.Somfy, MotorNm.Nm50, 100.00))
        val y = Item(itemID,Motor(MotorBrand.Spanish,MotorNm.Nm10,200.00))
        dbRef.child(MotorBrand.Spanish.toString()).setValue(y)
            .addOnCompleteListener {
                if (!it.isSuccessful){
                    return@addOnCompleteListener
                }else {
                    Toast.makeText(this,"Successful",Toast.LENGTH_LONG).show()
                    binding.tvResult.text = y.item.printDetails()
                }
            }.addOnFailureListener {
            Toast.makeText(this,"${it.message}",Toast.LENGTH_LONG).show()
            }





    }
}