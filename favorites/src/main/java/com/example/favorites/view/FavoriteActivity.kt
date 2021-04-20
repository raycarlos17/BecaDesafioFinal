package com.example.favorites.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.commons.model.Coin
import com.example.commons.model.helper.*
import com.example.commons.model.viewmodel.MainViewModel
import com.example.details.view.CoinDescriptionActivity
import com.example.favorites.R
import com.example.favorites.view.adapter.FavoritesAdapter
import kotlinx.android.synthetic.main.coin_favorites_recyclerview.*

class FavoriteActivity : AppCompatActivity(), View.OnClickListener, ClickItemListener, AlertDialog.AlertDialogListener {

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
        val btnMain: ImageButton = findViewById(R.id.btn_main)
        val btnDetail: ImageButton = findViewById(R.id.btn_detail)
        val tvBtnDetail: TextView = findViewById(R.id.tv_btn_detail)

        date.text = setDate.getLocalDate()
        date.contentDescription = setDate.getLocalDate()
        bottomNavigation.colorsNavigation(btnDetail, tvBtnDetail, "#9a9b54")

        btnMain.setOnClickListener(this)

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
                gv_list_coin_favorite.adapter =
                    FavoritesAdapter(this, createList(list, this),this@FavoriteActivity)
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

    override fun ClickItemList(coin: com.example.commons.model.Coin) {
        val intent = Intent(this@FavoriteActivity, CoinDescriptionActivity::class.java)
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