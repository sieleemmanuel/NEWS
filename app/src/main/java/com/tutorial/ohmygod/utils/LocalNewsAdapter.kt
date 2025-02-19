package com.tutorial.ohmygod.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tutorial.ohmygod.R
import com.tutorial.ohmygod.databinding.NewsViewHolderBinding
import com.tutorial.ohmygod.db.Article
import com.tutorial.ohmygod.db.SavedArticle

class LocalNewsAdapter : ListAdapter<SavedArticle, LocalNewsAdapter.ViewHolder>(diffObject) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = NewsViewHolderBinding.bind(view)
        fun bind(article: SavedArticle) {
            binding.apply {
                imgUrl.load(article.urlToImage) {
                    placeholder(R.drawable.ic_baseline_image_24)
                    error(R.drawable.ic_baseline_broken_image_24)
                }
                tvTitle.text = article.title
                tvDescription.text = article.description
                tvPublishedAt.text = article.publishedAt
                tvSourceName.text = article.source?.name
            }
            binding.root.setOnClickListener {
                listener?.let {
                    it(article)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_view_holder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pos = getItem(position)
        if (pos != null)
            holder.bind(pos)

    }

    companion object {
        val diffObject = object : DiffUtil.ItemCallback<SavedArticle>() {
            override fun areItemsTheSame(oldItem: SavedArticle, newItem: SavedArticle): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: SavedArticle, newItem: SavedArticle): Boolean {
                return oldItem.url == newItem.url
            }
        }
    }
    private var listener: ((SavedArticle) -> Unit)? = null

    fun adapterClickListener(listener: (SavedArticle) -> Unit) {
        this.listener = listener
    }
}
