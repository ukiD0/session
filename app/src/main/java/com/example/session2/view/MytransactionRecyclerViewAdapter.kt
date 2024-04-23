package com.example.session2.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import com.example.session2.databinding.FragmentTransactionBinding
import com.example.session2.model.Transactions


class MytransactionRecyclerViewAdapter(
    private val value: List<Transactions>,
    private val transactionViewModel: FragmentActivity
) : RecyclerView.Adapter<MytransactionRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(

            FragmentTransactionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }
    //A function for connecting list item layout and array item data.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = value[position]
        holder.money.text = item.sum
        holder.nameTranc.text = item.title
        holder.date.text = item.created_at
    }

    override fun getItemCount(): Int {
        return value.size
    }

    inner class ViewHolder(binding: FragmentTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val money: TextView = binding.money
        val nameTranc: TextView = binding.NameTranc
        val date: TextView = binding.date
    }

}