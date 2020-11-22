package com.example.newsapp.view

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapters.ArticleRecyclerAdapter
import com.example.newsapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModel<MainViewModel>()

    private lateinit var articleAdapter: ArticleRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        articleAdapter = ArticleRecyclerAdapter(this)
        initListeners()
        observeData()
        customStyleSearchView()
    }

    private fun initListeners() {
        svArticles.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                articleAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun observeData() {
        val loader = getLoader()
        mainViewModel.loadNews()

        mainViewModel.isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                loader.show()
            } else {
                loader.dismiss()
            }
        })

        mainViewModel.newsList.observe(this, Observer { articles ->
            rvArticles.also {
                it.layoutManager = LinearLayoutManager(this)
                it.adapter = articleAdapter.also {
                    it.addAll(articles)
                }
            }
        })
    }

    private fun getLoader(): Dialog {
        val loadingDialog = Dialog(this)
        loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        loadingDialog.setCancelable(false)
        loadingDialog.setContentView(R.layout.dialog_loader)
        loadingDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return loadingDialog
    }

    private fun customStyleSearchView() {
        val searchIcon = svArticles.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)
        val cancelIcon = svArticles.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)
        val searchText = svArticles.findViewById<TextView>(R.id.search_src_text)
        searchText.setTextColor(Color.WHITE)
        searchText.setHintTextColor(Color.WHITE)
    }
}