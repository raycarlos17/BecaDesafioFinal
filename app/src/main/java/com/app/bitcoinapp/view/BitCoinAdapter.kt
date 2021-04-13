package com.app.bitcoinapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.bitcoinapp.R

class BitCoinAdapter(private var list: List<String>) : RecyclerView.Adapter<BitCoinViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitCoinViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.bitcoin_item, parent, false)
        return BitCoinViewHolder(view)
    }

    override fun onBindViewHolder(holder: BitCoinViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class BitCoinViewHolder(
        itemView: View

) : RecyclerView.ViewHolder(itemView) {

    private val title: AppCompatTextView = itemView.findViewById(R.id.item_title)
    private val subTitle: AppCompatTextView = itemView.findViewById(R.id.item_sub_title)
    private val value: AppCompatTextView = itemView.findViewById(R.id.item_value)
    private val image: AppCompatImageView = itemView.findViewById(R.id.item_image)

    fun bind(bitCoin: String) {
        title.text = bitCoin.toString()
        subTitle.text = bitCoin.toString()
        value.text = "$" + bitCoin.toString()
        image.load(R.drawable.ic_image) {
            placeholder(R.drawable.ic_image)
            fallback(R.drawable.ic_image)
        }
    }
}