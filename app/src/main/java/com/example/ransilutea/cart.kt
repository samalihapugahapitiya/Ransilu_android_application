package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapters.CartAdapter
import com.example.myapplication.Models.CartModel
import com.example.myapplication.databinding.ActivityAllProductsBinding
import com.example.myapplication.databinding.ActivityCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class cart : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var adapter: CartAdapter
    private var mList = ArrayList<CartModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("cart").child(uid)

        var recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val product = snapshot.getValue(CartModel::class.java)!!
                    if( product != null){
                        mList.add(product)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@cart, error.message, Toast.LENGTH_SHORT).show()
            }

        })

        adapter = CartAdapter(this@cart,mList)
        recyclerView.adapter = adapter


        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: CartAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, select_product::class.java).also {
                    it.putExtra("name", mList[position].itemName)
                    it.putExtra("desc", mList[position].description)
                    it.putExtra("price", mList[position].price)
                    it.putExtra("id", mList[position].itemId)
                    it.putExtra("qty", mList[position].quantity)
                    it.putExtra("sellerId", mList[position].sellerId)
                    startActivity(it)
                }
            }
        })
    }
}

