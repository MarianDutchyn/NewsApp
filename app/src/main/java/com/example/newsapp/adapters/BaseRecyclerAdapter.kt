package com.example.newsapp.adapters

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, V: RecyclerView.ViewHolder> : RecyclerView.Adapter<V>(){

    var items: MutableList<T> = ArrayList()

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int): T? = items[position]

    fun add(item: T) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun addAll(items: List<T>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun replaceAll(items: List<T>) {
        this.items = items.toMutableList()
        notifyDataSetChanged()
    }

    fun removeItem(item: T) {
        items.remove(item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        getItem(position)?.let { removeItem(it) }
        notifyItemRemoved(position)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun itemPosition(item: T): Int{
        return items.lastIndexOf(item)
    }

    fun changeItem(position: Int, newItem: T) {
        items[position] = newItem
        notifyItemChanged(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}