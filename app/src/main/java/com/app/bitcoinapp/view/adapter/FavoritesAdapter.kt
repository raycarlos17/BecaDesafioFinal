package com.app.bitcoinapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.squareup.picasso.Picasso

class FavoritesAdapter(private var context: Context, private var list: List<Coin>) :
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): List<String> {

        val listCoin: MutableList<String> = arrayListOf()
        listCoin.add(list[position].assetId)
        listCoin.add(list[position].name)
        listCoin.add(list[position].priceUsd!!)
        listCoin.add(list[position].iconUrl.toString())

        return listCoin
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {

        var convertView = convertView

        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.coin_favorites_item, null)
        }

        val title: AppCompatTextView? = convertView?.findViewById(R.id.tv_coin_name_favorite)
        val subTitle: AppCompatTextView? = convertView?.findViewById(R.id.tv_coin_sigla_favorite)
        val value: AppCompatTextView? = convertView?.findViewById(R.id.tv_coin_value_favorite)
        val image: AppCompatImageView? = convertView?.findViewById(R.id.iv_favorite_icon)

        val list = getItem(position)


        title?.text = list[0]
        title?.contentDescription = "Nome da moeda ${list[0]}"

        subTitle?.text = list.get(1)
        subTitle?.contentDescription = "Sigla da moeda ${list[1]}"

        value?.text = list[2]
        value?.contentDescription = "Valor da moeda ${list[2]}"

        Picasso.get().load("${list[3]}.png")
            .placeholder(R.drawable.ic_image)
            .into(image)

        return convertView
    }
}



