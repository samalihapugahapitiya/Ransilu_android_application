package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.Models.User
import com.example.myapplication.databinding.ActivityMyprofileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


class myprofile : AppCompatActivity() {

    private lateinit var binding: ActivityMyprofileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users")

        databaseRef.child(auth.currentUser!!.uid).addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                var user = snapshot.getValue(User::class.java)!!
                binding.tvName.text = user.name
                binding.tvName.text = user.email
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@myprofile, "Faild to fetch user", Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnMyCart.setOnClickListener {
            intent = Intent(applicationContext, cart::class.java)
            startActivity(intent)
        }

    }
}