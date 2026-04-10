package com.example.timewellspent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.timewellspent.databinding.ActivityRegistrationBinding


class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // retrieve any information from the intent using the extras keys
        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD) ?: ""

        // prefill the username & password fields
        // for EditTexts, you actually have to use the setText functions
        binding.editTextRegistrationUsername.setText(username)
        binding.editTextTextPassword.setText(password)

        // register an account and send back the username & password
        // to the login activity to prefill those fields
        binding.buttonRegistrationRegister.setOnClickListener {
            val password = binding.editTextTextPassword.text.toString()
            val confirm = binding.editTextRegistrationConfirmPassword.text.toString()
            val username = binding.editTextRegistrationUsername.text.toString()
            val name = binding.editTextRegistrationName.text.toString()
            val email = binding.editTextRegistrationEmail.text.toString()



//            if(RegistrationUtil.validatePassword(password, confirm) &&
//                RegistrationUtil.validateUsername(username))  {  // && do the rest of the validations
//                // apply lambda will call the functions inside it on the object
//                // that apply is called on
//
//                // register the user on backendless following the documentation
//                // and in the handleResponse, that's where we make the resultIntent and go back
//                // in the handleFailure, toast the failure and don't go back.
//

//            }
        }

    }
}