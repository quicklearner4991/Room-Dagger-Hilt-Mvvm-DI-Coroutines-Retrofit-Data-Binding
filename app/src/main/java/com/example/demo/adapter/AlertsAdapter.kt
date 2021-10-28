package com.example.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.databinding.EachRowBinding
import com.example.demo.models.Alert

class AlertsAdapter(
    private var results: List<Alert>,
    private val itemClickListener: OnItemClickListener,
    var context: Context
) : RecyclerView.Adapter<AlertsAdapter.MyViewHolder>() {
    private lateinit var eachRowBinding: EachRowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        eachRowBinding = EachRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(eachRowBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = results[position]
        holder.eachRowBinding.expenseResponse = data
        holder.eachRowBinding.executePendingBindings()
        holder.bind(data, itemClickListener)
    }

    override fun getItemCount(): Int = results.size

    class MyViewHolder(eachRowBinding: EachRowBinding) :
        RecyclerView.ViewHolder(eachRowBinding.root) {
        var eachRowBinding: EachRowBinding
        init {
            this.eachRowBinding = eachRowBinding
        }
        fun bind(
            data: Alert,
            itemClickListener: OnItemClickListener
        ) {
            eachRowBinding.cvExpenseList.setOnClickListener {
                itemClickListener.onItemClicked(data)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(data: Alert)
    }

    fun setData(expensesResponse: List<Alert>) {
        this.results = expensesResponse
    }
}
