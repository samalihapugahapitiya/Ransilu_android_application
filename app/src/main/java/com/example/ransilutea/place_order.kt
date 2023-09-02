package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.Models.Order
import com.example.myapplication.databinding.ActivityPlaceOrderBinding
import com.example.myapplication.databinding.ActivitySelectProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class place_order : AppCompatActivity() {

    private lateinit var binding: ActivityPlaceOrderBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("cart").child(uid)

        var name = intent.getStringExtra("name").toString()
        val price = intent.getStringExtra("price").toString()
        val pid = intent.getStringExtra("pid").toString()
        val sellerId = intent.getStringExtra("sellerId").toString()

        //bind values to editTexts
        binding.tvTitle.text = name
        binding.tvPrice.text = price


        var shipping = 1500
        var tot = price.toInt() + shipping
        binding.tvTotal.text = tot.toString()

        binding.btnPlaceOrder.setOnClickListener {
            val orderRef = FirebaseDatabase.getInstance().reference.child("Orders")

            var id = databaseRef.push().key!!
            //create a object
            val data = Order( id, pid, uid)
            orderRef.child(id).setValue(data).addOnCompleteListener {
                if (it.isSuccessful){
                    intent = Intent(applicationContext, order_success::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Order placed successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        }


    }
}