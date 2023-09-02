package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapters.AllProductsAdapter
import com.example.myapplication.Models.ProductsModel
import com.example.myapplication.databinding.ActivityAllProductsBinding
import com.example.myapplication.databinding.ActivityMyProductListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AllProducts : AppCompatActivity() {

    private lateinit var binding: ActivityAllProductsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var adapter: AllProductsAdapter
    private var mList = ArrayList<ProductsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("products")

        var recyclerView = binding.recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        databaseRef.addValueEventListener(object : ValueEventListener {
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
                Toast.makeText(this@AllProducts, error.message, Toast.LENGTH_SHORT).show()
            }

        })

        adapter = AllProductsAdapter(this@AllProducts,mList)
        recyclerView.adapter = adapter


        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: AllProductsAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, select_product::class.java).also {
                    it.putExtra("name", mList[position].name)
                    it.putExtra("desc", mList[position].description)
                    it.putExtra("price", mList[position].price)
                    it.putExtra("id", mList[position].id)
                    it.putExtra("qty", mList[position].quantity)
                    it.putExtra("sellerId", mList[position].uid)
                    startActivity(it)
                }

            }

        })
    }
}
