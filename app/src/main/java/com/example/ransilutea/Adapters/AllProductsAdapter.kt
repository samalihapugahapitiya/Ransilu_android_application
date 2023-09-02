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
import com.example.myapplication.Models.ProductsModel
import com.example.myapplication.R
import com.example.myapplication.update_item
import com.google.firebase.database.FirebaseDatabase

class AllProductsAdapter(private val context: Context, var mList: List<ProductsModel>) :
    RecyclerView.Adapter<AllProductsAdapter.ViewHolder>() {

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
        val pQty: TextView = itemView.findViewById(R.id.tvQty)
        val pPrice: TextView = itemView.findViewById(R.id.tvPrice)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_products, parent, false)
        return ViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pName.text = mList[position].name
        holder.pQty.text = mList[position].quantity
        holder.pPrice.text = mList[position].price
    }
}