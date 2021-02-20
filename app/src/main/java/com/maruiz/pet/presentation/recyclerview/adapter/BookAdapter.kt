package com.maruiz.pet.presentation.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maruiz.pet.databinding.RowBookBinding
import com.maruiz.pet.presentation.model.BookPresentationModel
import com.maruiz.pet.presentation.viewmodel.BookListener
import kotlin.properties.Delegates

class BookAdapter : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    var renderables: List<BookPresentationModel> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    var bookClickListener: BookListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RowBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.run {
            model = renderables[position]
            clickListener = bookClickListener
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int = renderables.size

    inner class ViewHolder(val binding: RowBookBinding) : RecyclerView.ViewHolder(binding.root)
}