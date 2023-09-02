package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.Models.User
import com.example.myapplication.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        /*uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users")*/

        binding.signupbtn.setOnClickListener{
            var name = binding.Username.text.toString()
            var email = binding.email.text.toString()
            var password = binding.password.text.toString()
            var confirmPwd = binding.Repassword.text.toString()


            //checking if the input fields are empty
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPwd.isEmpty()) {
                if (name.isEmpty()) {
                    binding.Username.error = "Enter your name"
                }
                if (email.isEmpty()) {
                    binding.email.error = "Enter your email"
                }
                if (password.isEmpty()) {
                    binding.password.error = "Enter your password"
                }
                if (confirmPwd.isEmpty()) {
                    binding.Repassword.error = "Re-enter your password"
                }
            }

            //validate email pattern
            else if (!email.matches(emailPattern.toRegex())) {
                binding.email.error = "Enter a valid email address"
            }
            //validate passwords
            else if (password.length < 7) {
                binding.password.error = "Password must be at least 7 characters."
            } else if (confirmPwd != password) {
                binding.Repassword.error = "Passwords do not match. Please try again."
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    if (it.isSuccessful) {
                        //store user details in the database
                        databaseRef = FirebaseDatabase.getInstance().reference.child("users").child(auth.currentUser!!.uid)
                        val user: User = User(name, email, auth.currentUser!!.uid)
                        databaseRef.setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                //redirect user to login activity
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this, "Faild to create user", Toast.LENGTH_SHORT).show()
                            }
                        }

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
}