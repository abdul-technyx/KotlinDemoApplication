package com.example.kotlindemoapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlindemoapplication.R
import com.example.kotlindemoapplication.adapter.BookListAdapter
import com.example.kotlindemoapplication.databinding.ActivityMainBinding
import com.example.kotlindemoapplication.network.BookListModel
import com.example.kotlindemoapplication.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    //declaration
    lateinit var mainViewModel: MainActivityViewModel
    lateinit var bookListAdapter: BookListAdapter
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //initializing databinding variable and viewmodel
        mainViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        ).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = mainViewModel
        }

        //observing edittext field directly
        mainViewModel.queryString.observe(this, androidx.lifecycle.Observer {
            mainViewModel.makeApiCall(it)
        })

        //observing data with livedata
        mainViewModel.getBookListObserver().observe(this, androidx.lifecycle.Observer<BookListModel> {
            if (it != null) {
                //update adapter...
                bookListAdapter.bookList = it.items
                bookListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })

        //init recyclerview
        initRecyclerView()
    }

    fun initRecyclerView() {
        activityMainBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(decoration)
            bookListAdapter = BookListAdapter()
            adapter = bookListAdapter
        }
    }
}