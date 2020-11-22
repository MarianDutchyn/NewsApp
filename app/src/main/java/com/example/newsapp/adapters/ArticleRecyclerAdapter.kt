package com.example.newsapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.api.load
import com.example.newsapp.R
import com.example.newsapp.domain.Article
import kotlinx.android.synthetic.main.list_item_recycler_article.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ArticleRecyclerAdapter(val context: Context) : BaseRecyclerAdapter<Article, ArticleRecyclerAdapter.ArticleViewHolder>(), Filterable {

    var articleFilterList = mutableListOf<Article>()

    init {
        articleFilterList = items
    }

    override fun getItemCount(): Int {
        return articleFilterList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_recycler_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.title.text = articleFilterList[position].title
        holder.source.text = articleFilterList[position].source?.name
        holder.description.text = articleFilterList[position].description
        holder.time.text = parseHourAndMinute(articleFilterList[position].publishedAt)
        holder.arrow.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articleFilterList[position].url))
            context.startActivity(intent)
        }
        val imageUrl = articleFilterList[position].urlToImage
        if (imageUrl != null) {
            holder.image.load(imageUrl)
        } else {
            holder.image.visibility = View.GONE
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(value: CharSequence?): FilterResults {
                val searchValue = value.toString()
                if (searchValue.isEmpty()) {
                    articleFilterList = items
                } else {
                    val resultList = mutableListOf<Article>()
                    for (item in items) {
                        if (item.title?.toLowerCase()?.contains(searchValue.toLowerCase())!!) {
                            resultList.add(item)
                        }
                    }
                    articleFilterList = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = articleFilterList
                return filterResult
            }

            override fun publishResults(value: CharSequence?, results: FilterResults?) {
                articleFilterList = results?.values as MutableList<Article>
                notifyDataSetChanged()
            }
        }
    }

    private fun parseHourAndMinute(date: String?): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        try {
            val dateTime = dateFormat.parse(date)
            val calendar = GregorianCalendar.getInstance().also { it.time = dateTime }
            val hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
            var minute = calendar.get(Calendar.MINUTE).toString()
            if (minute.length == 1) {
                minute = "0$minute"
            }
            return "$hour:$minute"
        } catch (e: ParseException) {
            Log.w("newsApp", "can not parse the date $date: ${e.message}")
            return "--:--"
        }
    }

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.tvTitle
        val source = view.tvSource
        val description = view.tvDescription
        val time = view.tvTime
        val arrow = view.btnGoToWebSite
        val image = view.imgArticle
    }

}