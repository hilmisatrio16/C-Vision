package com.capstoneproject.cvision.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstoneproject.cvision.data.model.article.Article
import com.capstoneproject.cvision.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getData = intent.getSerializableExtra("DATA_ARTICLE") as Article

        showDataArticle(getData)
    }

    private fun showDataArticle(itemArticle: Article) {
        binding.apply {
            imageArticle.setImageResource(itemArticle.image)
            tvTitle.text = itemArticle.title
            tvContent.text = itemArticle.content
        }
    }
}