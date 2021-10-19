package com.example.kotlindemoapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemoapplication.databinding.BookRowItemBinding
import com.example.kotlindemoapplication.network.VolumeInfo
import java.util.ArrayList

class BookListAdapter : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {

    //declaration
    var bookList: ArrayList<VolumeInfo>? = ArrayList<VolumeInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        //creating layout
        val inflater = LayoutInflater.from(parent.context)
        val bookRowItemBinding = BookRowItemBinding.inflate(inflater)
        return BookViewHolder(bookRowItemBinding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        //binding data
        holder.bind(bookList?.get(position))
    }

    //for size of list
    override fun getItemCount(): Int {
        return bookList?.size ?: 0
    }

    class BookViewHolder(val bookRowItemBinding: BookRowItemBinding) :
        RecyclerView.ViewHolder(bookRowItemBinding.root) {

        //binding book data to layout
        fun bind(data: VolumeInfo?) {
            with(bookRowItemBinding)
            {
                bookRowItemBinding.volumeInfo = data
            }
        }
    }
}