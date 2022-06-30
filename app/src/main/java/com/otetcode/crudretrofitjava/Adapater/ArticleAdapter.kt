package com.otetcode.crudretrofitjava.Adapater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.otetcode.crudretrofitjava.Model.Article
import com.otetcode.crudretrofitjava.R
import kotlinx.android.synthetic.main.card_layout.view.*

class ArticleAdapter (private val articleList: List<Article>, private val listener: (Article) -> Unit
): RecyclerView.Adapter<ArticleAdapter.ArticleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false))

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) = holder.bind(articleList[position], listener)

    override fun getItemCount() = articleList.size

    class ArticleHolder(articleView: View): RecyclerView.ViewHolder(articleView) {

        fun bind(article: Article, listener: (Article) -> Unit) = with(itemView) {
            title.text = article.title
            body.text = article.body
            setOnClickListener { listener(article) }
        }
    }
}
