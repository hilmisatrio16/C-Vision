package com.capstoneproject.cvision.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstoneproject.cvision.data.model.article.Article
import com.capstoneproject.cvision.databinding.ItemArticleCataractBinding

class ListArticleAdapter(var onClickItemArticle: ((Article) -> Unit)? = null) :
    RecyclerView.Adapter<ListArticleAdapter.ViewHolder>() {

    private var diffCallbackUser = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    var differ = AsyncListDiffer(this, diffCallbackUser)

    fun submitData(valueList: ArrayList<Article>) {
        differ.submitList(valueList)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListArticleAdapter.ViewHolder {
        val view =
            ItemArticleCataractBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(var binding: ItemArticleCataractBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ListArticleAdapter.ViewHolder, position: Int) {
        val dataArticle = differ.currentList[position]

        with(holder.binding) {
            Glide.with(holder.itemView.context)
                .load(dataArticle.imageUrl)
                .into(imageArticle)
            tvTitle.text = dataArticle.title
            tvShortDescription.text = dataArticle.urlArt
        }

        holder.itemView.setOnClickListener {
            onClickItemArticle?.invoke(dataArticle)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}