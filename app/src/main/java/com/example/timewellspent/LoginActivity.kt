package com.example.timewellspent

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.timewellspent.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    companion object {
        // the values to send in intents are called Extras
        // and have the EXTRA_BLAH format for naming the key
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
        val TAG = "LoginActivity"
    }
    val startRegistrationForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            // Handle the Intent to do whatever we need with the returned info
            binding.editTextLoginUsername.setText(intent?.getStringExtra(EXTRA_USERNAME))
            binding.editTextLoginPassword.setText(intent?.getStringExtra(EXTRA_PASSWORD))
        }
    }


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // register with backendless
        // Android apps:
        // the context argument is is an instance of android.content.Context.
        // For instance, it may be your Android application main activity.
        Backendless.initApp( this, Constants.APP_ID, Constants.API_KEY)

        // launch the Registration Activity
        binding.textViewLoginSignup.setOnClickListener {

            // set up an onclick listener fo rlogging in
            //look at te example backedless code for logging in
                // !. in the handle resoionse, toast yhe username
                //2. once that works, create amn intent to go to
                // session list activity and pass the UserID as an extra

            //Go to the registartion activity & try to register a user


            // 1. Create an Intent object with the current activity
            // and the destination activity's class
            val registrationIntent = Intent(this,
                RegistrationActivity::class.java)
            // 2. optionally add information to send with the intent
            // key-value pairs just like with Bundles
            registrationIntent.putExtra(EXTRA_USERNAME,
                binding.editTextLoginUsername.text.toString())
            registrationIntent.putExtra(EXTRA_PASSWORD,
                binding.editTextLoginPassword.text.toString())
//             3a. launch the new activity using the intent
//            startActivity(registrationIntent)
            // 3b. Launch the activity for a result using the variable from the
            // register for result contract above
            startRegistrationForResult.launch(registrationIntent)
        }
        binding.buttonLoginLogin.setOnClickListener{
            val username = binding.editTextLoginUsername.text.toString()
            val password = binding.editTextLoginPassword.text.toString()
            Backendless.UserService.login(
                username,
                password,
                object : AsyncCallback<BackendlessUser?> {
                    override fun handleResponse(user: BackendlessUser?) {
                        Log.d(TAG, "handleResponse: ${user?.getProperty("username")}")
                        val sessionIntent =
                            Intent(this@LoginActivity, SessionListActivity::class.java)
                        //sessionIntent.putExtra(EXTRA_PASSWORD, password)
                        //sessionIntent.putExtra(EXTRA_USERNAME, username)
                        startActivity(sessionIntent)
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        Log.d(TAG, "handleFault: ${fault?.detail}")                    }
                })

        }
    }
}