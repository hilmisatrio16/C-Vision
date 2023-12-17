package com.capstoneproject.cvision.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.data.model.article.Article
import com.capstoneproject.cvision.databinding.FragmentHomeBinding
import com.capstoneproject.cvision.ui.home.adapter.ListArticleAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAnimation()

        binding.articleMenu.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_articleActivity)
        }

        //dummy
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
        setRecycleViewArticle(dummyListArticle)
    }

    private fun setRecycleViewArticle(articles: ArrayList<Article>) {
        val listArticleAdapter = ListArticleAdapter()

        binding.rvArticleCataract.apply {
            layoutManager = LinearLayoutManager(context)
            listArticleAdapter.submitData(articles)
            adapter = listArticleAdapter
            setHasFixedSize(true)
        }
    }


    private fun setAnimation() {
        val info = ObjectAnimator.ofFloat(binding.tvInfo, View.ALPHA, 1f).setDuration(300)
        val more = ObjectAnimator.ofFloat(binding.tvMore, View.ALPHA, 1f).setDuration(300)
        val image = ObjectAnimator.ofFloat(binding.imageBanner, View.ALPHA, 1f).setDuration(300)
        AnimatorSet().apply {
            playSequentially(info, image, more)
            start()
        }
    }

}