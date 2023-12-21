package com.capstoneproject.cvision.ui.article

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.capstoneproject.cvision.data.model.article.Article
import com.capstoneproject.cvision.databinding.ActivityDetailArticleBinding
import com.capstoneproject.cvision.utils.ConstantValue.DATA_ARTICLE

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getData = intent.getSerializableExtra(DATA_ARTICLE) as Article

        if (getData.toString().isNotEmpty()) {
            showDataArticle(getData)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun showDataArticle(itemArticle: Article) {
        binding.apply {
            Glide.with(this@DetailArticleActivity)
                .load(itemArticle.imageUrl)
                .into(imageArticle)
            tvTitle.text = itemArticle.title
            tvContent.text = itemArticle.urlArt
        }
    }
}