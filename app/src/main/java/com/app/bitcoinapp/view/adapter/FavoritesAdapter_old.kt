package com.app.bitcoinapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.app.bitcoinapp.R
import com.example.commons.model.Coin
import com.example.commons.model.helper.ClickItemListener
import com.squareup.picasso.Picasso

class FavoritesAdapter_old(
    private var context: Context,
    private var list: List<Coin>,
    private var listener: ClickItemListener
) :
    BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Coin {

        val coin: Coin = Coin(
            list[position].assetId,
            list[position].name,
            list[position].isCrypto,
            list[position].dataStart,
            list[position].dataEnd,
            list[position].volumeHourUsd,
            list[position].volumeDayUsd,
            list[position].volumeMthUsd,
            list[position].priceUsd!!,
            list[position].iconId,
            list[position].iconUrl,
            list[position].favorite
        )
        return coin
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

        val objCoin: Coin = getItem(position)

        convertView?.setOnClickListener {
            listener.ClickItemList(objCoin)
        }

        title?.text = objCoin.name
        title?.contentDescription = "Nome da moeda ${objCoin.name}"

        subTitle?.text = objCoin.assetId
        subTitle?.contentDescription = "Sigla da moeda ${objCoin.assetId}"

        value?.text = objCoin.priceUsd
        value?.contentDescription = "Valor da moeda ${objCoin.priceUsd}"

        Picasso.get().load("${objCoin.iconUrl}.png")
            .placeholder(R.drawable.ic_image)
            .into(image)

        return convertView
    }
}



