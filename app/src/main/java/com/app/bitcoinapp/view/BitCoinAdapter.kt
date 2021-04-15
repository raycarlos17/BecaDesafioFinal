package com.app.bitcoinapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.helper.ClickItemListener
import com.app.bitcoinapp.model.helper.SharedPreferences
import com.squareup.picasso.Picasso

class BitCoinAdapter(private var context:Context,private var list: List<Coin>, private var listener: ClickItemListener) :
    RecyclerView.Adapter<BitCoinViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitCoinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bitcoin_item, parent, false)
        return BitCoinViewHolder(view, listener, list,context)
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
    list: List<Coin>,
    context:Context

) : RecyclerView.ViewHolder(itemView) {

    private var sharedPreferences = SharedPreferences(context)

    private val title: AppCompatTextView = itemView.findViewById(R.id.item_title)
    private val subTitle: AppCompatTextView = itemView.findViewById(R.id.item_sub_title)
    private val value: AppCompatTextView = itemView.findViewById(R.id.item_value)
    private val image: AppCompatImageView = itemView.findViewById(R.id.item_image)
    private val imageFavorite: AppCompatImageView = itemView.findViewById(R.id.image_favorite)

    init {
        itemView.setOnClickListener {
            listener.ClickItemList(list[adapterPosition])
        }
    }

    fun bind(bitCoin: Coin) {
        title.text = bitCoin.name
        subTitle.text = bitCoin.assetId
        value.text = "${bitCoin.priceUsd}"
        Picasso.get().load("${bitCoin.iconUrl}.png")
            .placeholder(R.drawable.ic_image)
            .into(image)
        if (bitCoin.favorite){
            Picasso.get().load(R.drawable.ic_baseline_star_24).into(imageFavorite)
        }
        if (sharedPreferences.getBoolean(bitCoin.assetId)){
            imageFavorite.visibility = View.VISIBLE
        }else if(!sharedPreferences.getBoolean(bitCoin.assetId)){
            imageFavorite.visibility = View.GONE
        }
    }
}