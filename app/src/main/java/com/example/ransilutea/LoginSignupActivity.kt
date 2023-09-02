package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityLoginSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginSignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()

        if( auth.currentUser != null ) {
            intent = Intent(applicationContext, dashboard::class.java)
            startActivity(intent)
        }


        binding.btnLogin.setOnClickListener {
            intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener {
            intent = Intent(applicationContext, signup::class.java)
            startActivity(intent)
        }




    }
}