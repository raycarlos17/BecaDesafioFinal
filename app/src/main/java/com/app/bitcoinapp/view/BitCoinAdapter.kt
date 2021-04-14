package com.app.bitcoinapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.helper.ClickItemListener
import com.squareup.picasso.Picasso

class BitCoinAdapter(private var list: List<Coin>, private var listener: ClickItemListener) : RecyclerView.Adapter<BitCoinViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitCoinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bitcoin_item, parent, false)
        return BitCoinViewHolder(view, listener, list)
    }

    override fun onBindViewHolder(holder: BitCoinViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class BitCoinViewHolder(
    itemView: View,
    listener: ClickItemListener,
    list: List<Coin>

) : RecyclerView.ViewHolder(itemView) {

    private val title: AppCompatTextView = itemView.findViewById(R.id.item_title)
    private val subTitle: AppCompatTextView = itemView.findViewById(R.id.item_sub_title)
    private val value: AppCompatTextView = itemView.findViewById(R.id.item_value)
    private val image: AppCompatImageView = itemView.findViewById(R.id.item_image)

    init {
        itemView.setOnClickListener {
            listener.ClickItemList(list[adapterPosition])
        }
    }

    fun bind(bitCoin: Coin) {
        title.text = bitCoin.name
        subTitle.text = bitCoin.assetId
        value.text = "${bitCoin.priceUsd}"
        Picasso.get().load("${bitCoin.iconUrl}.png").placeholder(R.drawable.ic_image).into(image)
    }
}