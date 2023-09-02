package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapters.MyProductsAdapter
import com.example.myapplication.Models.ProductsModel
import com.example.myapplication.databinding.ActivityMyProductListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class My_product_list : AppCompatActivity() {

    private lateinit var binding: ActivityMyProductListBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var adapter: MyProductsAdapter
    private var mList = ArrayList<ProductsModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("products")
        val query = databaseRef.orderByChild("uid").equalTo(uid)

        var recyclerView = binding.recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val product = snapshot.getValue(ProductsModel::class.java)!!
                    if( product != null){
                        mList.add(product)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@My_product_list, error.message, Toast.LENGTH_SHORT).show()
            }

        })

        adapter = MyProductsAdapter(this@My_product_list,mList)
        recyclerView.adapter = adapter


        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: MyProductsAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {

            }

        })
    }
}