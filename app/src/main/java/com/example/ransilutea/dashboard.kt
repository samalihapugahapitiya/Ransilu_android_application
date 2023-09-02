package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class dashboard : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users")


        binding.btnLogout.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            //redirect user to login page
            intent = Intent(applicationContext, LoginSignupActivity::class.java)
            startActivity(intent)

            //toast message
            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show()

        }

        binding.btnProfile.setOnClickListener {
            intent = Intent(applicationContext, myprofile::class.java)
            startActivity(intent)
        }

        binding.btnCart.setOnClickListener {
            intent = Intent(applicationContext, cart::class.java)
            startActivity(intent)
        }

        binding.btnBuyer.setOnClickListener {
            intent = Intent(applicationContext, AllProducts::class.java)
            startActivity(intent)
        }

        binding.btnSeller.setOnClickListener {
            intent = Intent(applicationContext, welcome::class.java)
            startActivity(intent)
        }











    }
}