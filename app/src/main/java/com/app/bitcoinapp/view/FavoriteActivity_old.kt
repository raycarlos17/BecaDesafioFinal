package com.app.bitcoinapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.app.bitcoinapp.R
import com.app.bitcoinapp.view.adapter.FavoritesAdapter_old
import com.example.commons.model.Coin
import com.example.commons.model.helper.*
import com.example.commons.model.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.bottom_navigation_view_component.btn_detail
import kotlinx.android.synthetic.main.bottom_navigation_view_component.tv_btn_detail
import kotlinx.android.synthetic.main.bottom_navigation_view_component.btn_main

class FavoriteActivity_old : AppCompatActivity(), View.OnClickListener, ClickItemListener, AlertDialog.AlertDialogListener {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private val setDate = SetDate()
    private val bottomNavigation = BottomNavigation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_favorites_recyclerview)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_main_item)

        val date:TextView = findViewById(R.id.tv_date)
        date.text = setDate.getLocalDate()
        date.contentDescription = setDate.getLocalDate()
        bottomNavigation.colorsNavigation(btn_detail, tv_btn_detail, "#9a9b54")

        btn_main.setOnClickListener(this)

        initViewModel()
        observerFavorites()
    }

    private fun initViewModel(){
        mainViewModel =
            ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        mainViewModel.init(this.supportFragmentManager, ConnectionState().isConnected(this))
    }

    private fun observerFavorites() {
        mainViewModel.bitCoinsList.observe(this, { list ->
            if (list != null) {
//                gv_list_coin_favorite.adapter =
//                    FavoritesAdapter_old(this, createList(list, this),this@FavoriteActivity_old)
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
        val intent = Intent(this, MainActivity::class.java)
        if (id == R.id.btn_main) {
            startActivity(intent)
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

    override fun ClickItemList(coin: com.example.commons.model.Coin) {
        val intent = Intent(this@FavoriteActivity_old,CoinDescriptionActivity_old::class.java)
        intent.putExtra("EXTRA_COIN", coin)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        observerFavorites()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        initViewModel()
        observerFavorites()
    }

}