package com.workspaceandroid.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.workspaceandroid.R
import com.workspaceandroid.data.remote.responses.FavoritesResponse
import kotlinx.android.synthetic.main.item_favorite_row.view.*


class AdapterFavoritesItems(private val items: List<FavoritesResponse>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private val TAG = "AdapterFavoritesItems";

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_favorite_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteItem = items[position]
        holder.tvFavoriteTitle.text = favoriteItem.name
        holder.tvFavoriteBody.text = favoriteItem.content

        Picasso.get()
            .load(favoriteItem.image)
            .into(holder.ivFavoritePicture)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvFavoriteTitle: TextView = view.tv_favorite_header
    val tvFavoriteBody: TextView = view.tv_favorite_body
    val ivFavoritePicture: ImageView = view.iv_picture
}