package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth
        auth = FirebaseAuth.getInstance()

        //set onclick listner on login button
        binding.btnLogin.setOnClickListener() {

            val email = binding.username.text.toString()
            val password = binding.password.text.toString()

            //checking if the input fields are empty
            if(email.isEmpty() || password.isEmpty()){

                if(email.isEmpty()){
                    binding.username.error = "Enter your email address"
                }
                if(password.isEmpty()){
                    binding.password.error = "Enter your password"
                }

                Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()

            } else if (!email.matches(emailPattern.toRegex())){
                //validate email pattern
                binding.username.error = "Enter a valid email address"

                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()

            } else if (password.length < 7){
                //validate passwords
                binding.password.error = "Password must be at least 7 characters."
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()

            } else{
                //Log in
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        intent = Intent(applicationContext, dashboard::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}