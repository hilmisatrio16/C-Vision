package com.capstoneproject.cvision.ui.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneproject.cvision.data.model.article.Article
import com.capstoneproject.cvision.databinding.ActivityArticleBinding
import com.capstoneproject.cvision.ui.article.adapter.ListArticlesAdapter
import com.capstoneproject.cvision.utils.ConstantValue.DATA_ARTICLE

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    private val articleVM by viewModels<ArticleViewModel> {
        ArticleViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressed()
        }

        articleVM.getArticles().observe(this, Observer {
            if (it != null) {
                setRecycleviewArticle(it as ArrayList<Article>)
            }
        })


    }

    private fun setRecycleviewArticle(itemArticle: ArrayList<Article>) {
        val listArticlesAdapter = ListArticlesAdapter {
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra(DATA_ARTICLE, it)
            startActivity(intent)
        }

        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(context)
            listArticlesAdapter.submitData(itemArticle)
            adapter = listArticlesAdapter
            setHasFixedSize(true)
        }

    }
}