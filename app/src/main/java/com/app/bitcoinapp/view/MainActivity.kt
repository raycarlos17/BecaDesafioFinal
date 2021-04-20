package com.app.bitcoinapp.view

import com.example.commons.model.helper.ConnectionState
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.app.bitcoinapp.R
import com.example.commons.model.helper.AlertDialog
import com.example.commons.model.helper.ClickItemListener
import com.example.commons.model.helper.SetDate
import com.app.bitcoinapp.view.adapter.BitCoinAdapter
import com.example.commons.model.Coin
import com.example.commons.model.helper.BottomNavigation
import com.example.commons.model.viewmodel.MainViewModel
import com.example.details.view.CoinDescriptionActivity
import com.example.favorites.view.FavoriteActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_navigation_view_component.btn_detail
import kotlinx.android.synthetic.main.bottom_navigation_view_component.btn_main
import kotlinx.android.synthetic.main.bottom_navigation_view_component.tv_btn_main

class MainActivity : AppCompatActivity(), View.OnClickListener, ClickItemListener, AlertDialog.AlertDialogListener {

    private lateinit var mainViewModel: MainViewModel
    private val setDate = SetDate()
    private val bottomNavigation = BottomNavigation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_main_item)

        val date: TextView = findViewById(R.id.tv_date)
        date.text = setDate.getLocalDate()
        date.contentDescription = setDate.getLocalDate()
        bottomNavigation.colorsNavigation(btn_main, tv_btn_main, "#9a9b54")

        btn_main.setOnClickListener(this)
        btn_detail.setOnClickListener(this)

        initViewModel()
        mainViewObserver()
        initSearch()
    }

    private fun initViewModel(){
        mainViewModel = ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        mainViewModel.init(this.supportFragmentManager, ConnectionState().isConnected(this))
    }

    private fun mainViewObserver(){
        mainViewModel.bitCoinsList.observe(this, {  list ->
            if (list != null){
                bit_coin_list.adapter = BitCoinAdapter(this, list, this)
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
        val searchedCoins: MutableList<Coin> = arrayListOf()
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
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun ClickItemList(coin: com.example.commons.model.Coin) {
        val intent = Intent(this, CoinDescriptionActivity::class.java)
        intent.putExtra("EXTRA_COIN", coin)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        mainViewObserver()
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        initViewModel()
        mainViewObserver()
    }

}
