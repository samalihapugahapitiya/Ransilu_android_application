package com.example.myapplication.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Models.CartModel
import com.example.myapplication.R
import com.example.myapplication.update_item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class CartAdapter(private val context: Context, var mList: List<CartModel>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class ViewHolder(itemView: View, listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val pName: TextView = itemView.findViewById(R.id.tvName)
        val pPrice: TextView = itemView.findViewById(R.id.tvPrice)
        val btnDlt:Button = itemView.findViewById(R.id.btnDlt)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_cart, parent, false)
        return ViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pName.text = mList[position].itemName
        holder.pPrice.text = mList[position].price

        holder.btnDlt.setOnClickListener {
            var auth = FirebaseAuth.getInstance()
            var uid = auth.currentUser?.uid.toString()
            val databaseRef = FirebaseDatabase.getInstance().reference.child("cart").child(uid)
            var id = mList[position].cartId!!
            databaseRef.child(id).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}