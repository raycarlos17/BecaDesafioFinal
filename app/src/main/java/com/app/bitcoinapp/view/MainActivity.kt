package com.app.bitcoinapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.app.bitcoinapp.R
import com.app.bitcoinapp.model.Coin
import com.app.bitcoinapp.model.helper.ClickItemListener
import com.app.bitcoinapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), View.OnClickListener, ClickItemListener {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(R.layout.abs_main_item)

        btn_main.setOnClickListener(this)
        btn_detail.setOnClickListener(this)

        val date:TextView = findViewById(R.id.tv_date)
        date.text = getLocalDate()

        mainViewObserver()
    }

    private fun mainViewObserver(){
        mainViewModel =
            ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
        mainViewModel.init()
        mainViewModel.bitCoinsList.observe(this, {  list ->
            if (list != null){
                bit_coin_list.adapter = BitCoinAdapter(list, this)
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

        when{
            (id == R.id.btn_main) -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            (id == R.id.btn_detail) -> {}
        }
    }

    private fun getLocalDate(): String{
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        return now.format(formatter).toString()
    }

    override fun ClickItemList(coin: Coin) {
        val intent = Intent(this, CoinDescription::class.java)
        intent.putExtra("EXTRA_COIN", coin)
        startActivity(intent)
    }
}