package com.app.bitcoinapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.helper.ClickItemListener
import java.text.NumberFormat
import java.util.*

class BitCoinAdapter(private var list: List<Coin>, private var listener: ClickItemListener) : RecyclerView.Adapter<BitCoinViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitCoinViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bitcoin_item, parent, false)
        return BitCoinViewHolder(view, listener)
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
    listener: ClickItemListener

) : RecyclerView.ViewHolder(itemView) {

    private val title: AppCompatTextView = itemView.findViewById(R.id.item_title)
    private val subTitle: AppCompatTextView = itemView.findViewById(R.id.item_sub_title)
    private val value: AppCompatTextView = itemView.findViewById(R.id.item_value)
    private val image: AppCompatImageView = itemView.findViewById(R.id.item_image)
    private val usd = Currency.getInstance("USD")
    private var formatUSDCurrency = NumberFormat.getCurrencyInstance(Locale.US)

    private fun formatCurrency(value: String?): String?{
        formatUSDCurrency.currency = usd

        return if(value != null){
            formatUSDCurrency.format(value.toDouble())
        }else{
            value
        }
    }

    init {
        itemView.setOnClickListener {
            listener.ClickItemList()
        }
    }

    fun bind(bitCoin: Coin) {
        title.text = bitCoin.name
        subTitle.text = bitCoin.assetId
        value.text = "${formatCurrency(bitCoin.priceUsd)}"
        image.load(R.drawable.ic_image) {
            placeholder(R.drawable.ic_image)
            fallback(R.drawable.ic_image)
        }
    }
}