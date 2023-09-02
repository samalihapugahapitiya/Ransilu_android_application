package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.Models.ProductsModel
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAddProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class add_product : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("products")

        binding.btnAdd.setOnClickListener {
            val name = binding.etProductName.text.toString()
            val description = binding.etShopName.text.toString()
            val qty = binding.etQuantity.text.toString()
            val price = binding.etPrice.text.toString()

            if(name.isEmpty() || description.isEmpty() || qty.isEmpty() || price.isEmpty()){

                if(name.isEmpty()){
                    binding.etProductName.error = "Enter product name"
                }
                if(description.isEmpty()){
                    binding.etShopName.error = "Enter Description"
                }
                if(price.isEmpty()){
                    binding.etPrice.error = "Enter Price"
                }
                if(qty.isEmpty()){
                    binding.etQuantity.error = "Enter Quantity"
                }

            } else {
                //Id for new record
                var id = databaseRef.push().key!!
                //create a FundraisingData object
                val product = ProductsModel(name,description,qty,price,id,uid)
                databaseRef.child(id).setValue(product).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(applicationContext, My_product_list::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Product added.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}