package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityUpdateItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class update_item : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateItemBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("products")

        var name = intent.getStringExtra("name").toString()
        var description = intent.getStringExtra("description").toString()
        var quantity = intent.getStringExtra("quantity").toString()
        var price = intent.getStringExtra("price").toString()
        var id = intent.getStringExtra("id").toString()

        binding.etProductName.setText(name)
        binding.etDesc.setText(description)
        binding.etQuantity.setText(quantity)
        binding.etPrice.setText(price)

        binding.btnUpdate.setOnClickListener {

            name = binding.etProductName.text.toString()
            description = binding.etDesc.text.toString()
            quantity = binding.etQuantity.text.toString()
            price = binding.etPrice.text.toString()


            if(name.isEmpty() || description.isEmpty() || quantity.isEmpty() || price.isEmpty()){

                if(name.isEmpty()){
                    binding.etProductName.error = "Enter product name"
                }
                if(description.isEmpty()){
                    binding.etDesc.error = "Enter Description"
                }
                if(price.isEmpty()){
                    binding.etPrice.error = "Enter Price"
                }
                if(quantity.isEmpty()){
                    binding.etQuantity.error = "Enter Quantity"
                }

            } else {
                val map = HashMap<String,Any>()

                //add data to hashMap
                map["name"] = name
                map["description"] = description
                map["quantity"] = quantity
                map["price"] = price


                //update database from hashMap
                databaseRef.child(id).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        intent = Intent(applicationContext, My_product_list::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }

    }
}