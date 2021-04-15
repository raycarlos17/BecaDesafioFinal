package com.app.bitcoinapp.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.helper.SetDate
import com.app.bitcoinapp.model.helper.SharedPreferences
import com.app.bitcoinapp.view.adapter.FavoritesAdapter
import com.app.bitcoinapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.btn_main
import kotlinx.android.synthetic.main.coin_favorites_recyclerview.*

class FavoriteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val setDate = SetDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_favorites_recyclerview)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_main_item)

        val date:TextView = findViewById(R.id.tv_date_favorites)
        date.text = setDate.getLocalDate()
        date.contentDescription = setDate.getLocalDate()

        btn_main.setOnClickListener(this)

        observerFavorites()
    }


    private fun observerFavorites() {
        mainViewModel =
            ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        mainViewModel.init()

        mainViewModel.bitCoinsList.observe(this, { list ->
            if (list != null) {
                gv_list_coin_favorite.adapter = FavoritesAdapter(this, createList(list, this))
            } else {
                Toast.makeText(
                    this,
                    "Ops tivemos um problema, tente novamente mais tarde!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onClick(v: View) {
        val id = v.id

        if (id == R.id.btn_main) {
            finish()
        }
    }

    private fun createList(list: List<Coin>, context: Context): List<Coin> {
        val newListCoin: MutableList<Coin> = arrayListOf()
        sharedPreferences = SharedPreferences(context)

        list.forEach {
            if (sharedPreferences.getBoolean(it.assetId)) {
                newListCoin.add(it)
            }
        }

        return newListCoin
    }
}