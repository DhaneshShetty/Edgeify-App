package com.ddevs.edgeify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    val viewModel:MainViewModel by lazy{
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val loading:CircularProgressIndicator = findViewById(R.id.loading)
        viewModel.loading.observe(this){
            if(it)
                loading.visibility= View.VISIBLE
            else
                loading.visibility = View.GONE
        }
        viewModel.error.observe(this){
            Snackbar.make(this.findViewById(R.id.view),it,Snackbar.LENGTH_SHORT).show()
        }
    }
}