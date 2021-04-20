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
import com.example.commons.model.helper.Constants.Companion.REQUEST_CODE
import com.example.commons.model.helper.Constants.Companion.RESULT_MAIN
import com.example.commons.model.viewmodel.MainViewModel
import com.example.details.view.CoinDescriptionActivity
import com.example.favorites.R
import com.example.favorites.view.adapter.FavoritesAdapter
import kotlinx.android.synthetic.main.coin_favorites_recyclerview.*

class FavoriteActivity : AppCompatActivity(), View.OnClickListener, ClickItemListener, AlertDialog.AlertDialogListener {

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

        initFavoritesList()
    }

    fun initFavoritesList(){
        val coinsList: List<Coin> = createList()
        if(coinsList.size != null){
            gv_list_coin_favorite.adapter = FavoritesAdapter(this, coinsList, this@FavoriteActivity)
        }
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btn_main) {
            finish()
        }
    }

    private fun createList(): List<Coin> {
        sharedPreferences = SharedPreferences(this)
        val newListCoin: MutableList<Coin> = sharedPreferences.getAllFavoriteCoins()

        return newListCoin
    }

    override fun ClickItemList(coin: com.example.commons.model.Coin) {
        val intent = Intent(this@FavoriteActivity, CoinDescriptionActivity::class.java)
        intent.putExtra("EXTRA_COIN", coin)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onResume() {
        super.onResume()
        initFavoritesList()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        initFavoritesList()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_MAIN){
            finish()
        }
    }

}