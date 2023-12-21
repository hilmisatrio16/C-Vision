package com.capstoneproject.cvision.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstoneproject.cvision.R
import com.capstoneproject.cvision.data.model.article.Article
import com.capstoneproject.cvision.databinding.FragmentHomeBinding
import com.capstoneproject.cvision.ui.article.DetailArticleActivity
import com.capstoneproject.cvision.ui.home.adapter.ListArticleAdapter
import com.capstoneproject.cvision.utils.ConstantValue.DATA_ARTICLE

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeVM by viewModels<HomeViewModel> {
        HomeViewModelFactory.getInstance(requireContext())
    }

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

        homeVM.getArticles().observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                setRecycleViewArticle(it.take(5) as ArrayList<Article>)
            }
        }

        homeVM.getNameUser().observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                binding.topAppBar.title = "Halo, $it"
            }
        }

    }

    private fun setRecycleViewArticle(articles: ArrayList<Article>) {
        val listArticleAdapter = ListArticleAdapter {
            moveToDetailArticle(it)
        }

        binding.rvArticleCataract.apply {
            layoutManager = LinearLayoutManager(context)
            listArticleAdapter.submitData(articles)
            adapter = listArticleAdapter
            setHasFixedSize(true)
        }
    }

    private fun moveToDetailArticle(itemArticle: Article) {
        val intent = Intent(context, DetailArticleActivity::class.java).apply {
            putExtra(DATA_ARTICLE, itemArticle)
        }
        startActivity(intent)

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