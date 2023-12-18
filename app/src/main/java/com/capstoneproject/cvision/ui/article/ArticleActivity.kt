package com.capstoneproject.cvision.ui.article

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.data.model.article.Article
import com.capstoneproject.cvision.databinding.ActivityArticleBinding
import com.capstoneproject.cvision.ui.article.adapter.ListArticlesAdapter

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setOnClickListener {
            onBackPressed()
        }

        setRecycleviewArticle()

    }

    private fun setRecycleviewArticle() {
        val listArticlesAdapter = ListArticlesAdapter{
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra("DATA_ARTICLE", it)
            startActivity(intent)
            Toast.makeText(this, it.title, Toast.LENGTH_LONG).show()
        }

        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(context)
            listArticlesAdapter.submitData(dummyListArticle())
            adapter = listArticlesAdapter
            setHasFixedSize(true)
        }

    }

    private fun dummyListArticle(): ArrayList<Article>{
        val dummyListArticle: ArrayList<Article> = ArrayList()
        dummyListArticle.add(
            Article(
                R.drawable.image_info_temporary,
                "Caract title 1",
                "Description article cataract 1"
            )
        )
        dummyListArticle.add(
            Article(
                R.drawable.image_info_temporary,
                "Caract title 2",
                "Description article cataract 2"
            )
        )
        dummyListArticle.add(
            Article(
                R.drawable.image_info_temporary,
                "Caract title 3",
                "Description article cataract 3"
            )
        )
        dummyListArticle.add(
            Article(
                R.drawable.image_info_temporary,
                "Caract title 4",
                "Description article cataract 4"
            )
        )
        dummyListArticle.add(
            Article(
                R.drawable.image_info_temporary,
                "Caract title 5",
                "Description article cataract 5"
            )
        )

        return dummyListArticle
    }
}