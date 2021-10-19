package com.example.kotlindemoapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlindemoapplication.R
import com.example.kotlindemoapplication.adapter.BookListAdapter
import com.example.kotlindemoapplication.databinding.ActivityMainBinding
import com.example.kotlindemoapplication.network.BookListModel
import com.example.kotlindemoapplication.viewmodel.MainActivityViewModel
import io.reactivex.Observer

class MainActivity : AppCompatActivity() {

    //declaration
    lateinit var viewModel: MainActivityViewModel
    lateinit var bookListAdapter: BookListAdapter
    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //initializing databinding variable
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //init searching for edit text
        initSearchBox()
        //init recyclerview
        initRecyclerView()
    }

    fun initSearchBox() {
        activityMainBinding.inputBookName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadAPIData(s.toString())
            }
        })
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

    fun loadAPIData(input: String) {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getBookListObserver().observe(this, androidx.lifecycle.Observer<BookListModel> {
            if (it != null) {
                //update adapter...
                bookListAdapter.bookList = it.items
                bookListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in fetching data", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall(input)
    }
}