package com.dimitrisligi.agentapro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dimitrisligi.agentapro.databinding.ActivityCreateNewClientBinding

class CreateNewClient : AppCompatActivity() {
    private lateinit var binding: ActivityCreateNewClientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNewClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}