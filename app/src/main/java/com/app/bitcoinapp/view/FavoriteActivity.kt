package com.app.bitcoinapp.view

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.bitcoinapp.R
import com.app.bitcoinapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.bit_coin_list
import kotlinx.android.synthetic.main.activity_main.btn_main
import kotlinx.android.synthetic.main.coin_favorites_recyclerview.*

class FavoriteActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mainViewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coin_favorites_recyclerview)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_main_item)

        btn_main.setOnClickListener(this)

        observerFavorites()
    }


    private fun observerFavorites(){
        mainViewModel =
            ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        mainViewModel.init()
        mainViewModel.bitCoinsList.observe(this, {  list ->
            if (list != null){
                gv_list_coin_favorite.adapter = FavoritesAdapter(this,list)
            }else{
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

        if (id == R.id.btn_main){
            finish()
        }
    }
}