package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.Models.CartModel
import com.example.myapplication.Models.ProductsModel
import com.example.myapplication.databinding.ActivityAllProductsBinding
import com.example.myapplication.databinding.ActivitySelectProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class select_product : AppCompatActivity() {
    private lateinit var binding: ActivitySelectProductBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("cart").child(uid)

        var name = intent.getStringExtra("name").toString()
        val desc = intent.getStringExtra("desc").toString()
        val price = intent.getStringExtra("price").toString()
        val pid = intent.getStringExtra("id").toString()
        val qty = intent.getStringExtra("qty").toString()
        val sellerId = intent.getStringExtra("sellerId").toString()

        //bind values to editTexts
        binding.tvTitle.text = name
        binding.tvDesc.text = desc
        binding.tvPrice.text = price

        binding.btnAddToCart.setOnClickListener {
            var id = databaseRef.push().key!!
            val data = CartModel( id,pid,name,desc,qty,price,sellerId,uid)
            databaseRef.child(id).setValue(data).addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Item added cart", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnBuy.setOnClickListener {
            intent = Intent(applicationContext, place_order::class.java).also {
                it.putExtra("pid", pid)
                it.putExtra("name", name)
                it.putExtra("price", price)
                it.putExtra("sellerId", sellerId)
                startActivity(it)
            }
        }


    }
}