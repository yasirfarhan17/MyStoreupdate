package com.example.mystoreupdate

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mystoreupdate.databinding.IndiviewMyorderBinding

class NewOrderAdapter(
    val callback: MyOrderCallBack
) : RecyclerView.Adapter<NewOrderAdapter.MYOrderViewHolder>() {
    private val items = ArrayList<checkOutModel>()


    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: ArrayList<checkOutModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class MYOrderViewHolder(private val binding: IndiviewMyorderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: checkOutModel) {
            with(binding) {
                tvAmount.text = "₹ " + item.amount
                tvDate.text = "Delivery Date" + item.date
                tvItem.text = "${item.list?.size.toString()}items"
                tvOrderId.text = "OrderId:-" + item.orderId
                //tvPayment.text="Not Paid"
                tvStatus.text = "Status:-delivered"
                cardView3.setOnClickListener {
                    callback.onItemClick(item.orderId.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MYOrderViewHolder {
        val binding =
            IndiviewMyorderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MYOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MYOrderViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}

interface MyOrderCallBack {
    fun onItemClick(orderId: String)
}