package com.app.bitcoinapp.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.helper.ClickItemListener
import com.app.bitcoinapp.model.helper.SetDate
import com.app.bitcoinapp.view.adapter.BitCoinAdapter
import com.app.bitcoinapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.btn_detail
import kotlinx.android.synthetic.main.activity_main.btn_main

class MainActivity : AppCompatActivity(), View.OnClickListener, ClickItemListener {

    private lateinit var mainViewModel: MainViewModel
    private val setDate = SetDate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_main_item)

        val date: TextView = findViewById(R.id.tv_date)
        date.text = setDate.getLocalDate()
        date.contentDescription = setDate.getLocalDate()

        btn_main.setOnClickListener(this)
        btn_detail.setOnClickListener(this)


        bottomColors()
        initViewModel()
        mainViewObserver()
        initSearch()
    }

    private fun initViewModel(){
        mainViewModel = ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        mainViewModel.init()
    }

    private fun mainViewObserver(){
        mainViewModel.bitCoinsList.observe(this, {  list ->
            if (list != null){
                bit_coin_list.adapter = BitCoinAdapter(this,list, this)
            }else{
                Toast.makeText(
                    this,
                    "Ops tivemos um problema, tente novamente mais tarde!",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun searchCoins(search: String){
        var searchedCoins: MutableList<Coin> = arrayListOf()
        mainViewModel.bitCoinsList.value?.forEach{
            if(it.name != null){
                if(it.name.contains(search, ignoreCase = true)){
                    searchedCoins.add(it)
                }
            }
        }
        bit_coin_list.adapter = BitCoinAdapter(this, searchedCoins, this)
    }

    private fun initSearch(){

        sv_search_bar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                searchCoins(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchCoins(newText)
                return false
            }
        })
    }

    override fun onClick(v: View) {
        val id = v.id

        when{
            (id == R.id.btn_main) -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            (id == R.id.btn_detail) -> {
                val intentFavorite = Intent(this, FavoriteActivity::class.java)
                startActivity(intentFavorite)
            }
        }
    }

    override fun ClickItemList(coin: Coin) {
        val intent = Intent(this, CoinDescriptionActivity::class.java)
        intent.putExtra("EXTRA_COIN", coin)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        mainViewObserver()
    }

    private fun bottomColors() {
        btn_main.setColorFilter(Color.parseColor("#9a9b54"))
        tv_btn_main.setTextColor(Color.parseColor("#9a9b54"))
    }
}
