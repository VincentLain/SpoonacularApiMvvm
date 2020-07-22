package com.vincent.lain.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vincent.lain.R
import com.vincent.lain.data.NetworkState
import com.vincent.lain.data.Status
import com.vincent.lain.data.model.Menu
import kotlinx.android.synthetic.main.item_menu_main.view.*
import kotlinx.android.synthetic.main.item_network_state.view.*

class MenuPagedListAdapter(var listener: (Menu) -> Unit)
    :PagedListAdapter<Menu, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.item_network_state -> NetworkStateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_network_state, parent, false))
            R.layout.item_menu_search -> MenuHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_menu_search, parent, false))
            else -> throw Exception("Not a valid view type")
        }

    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return when{
            hasExtraRow() && position == itemCount - 1 -> R.layout.item_network_state
            else -> R.layout.item_menu_search
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
        return super.getItemCount()
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = networkState
        val hadExtraRow = hasExtraRow()
        networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if(hadExtraRow != hasExtraRow) {
            if(hadExtraRow) {
                notifyItemChanged(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if(hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            R.layout.item_network_state -> {
                val nsHolder = holder as NetworkStateViewHolder
                networkState?.let { nsHolder.bind(it) }
            }
            R.layout.item_menu_search -> {
                val menuHolder = holder as MenuHolder
                getItem(position)?.let { menuHolder.bind(it) }
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(oldItem: Menu, newItem: Menu) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Menu, newItem: Menu) = oldItem == newItem
        }
    }

    inner class MenuHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(menu: Menu) = with(view) {
            Log.e(this.javaClass.simpleName, "bind: MenuHolder ")
            title_textview.text = menu.title
            view.setOnClickListener { listener(menu) }
            if (menu.image != null)
                Picasso.get().load(menu.image).into(menu_imageview)
            else {
                menu_imageview.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_room_service_24, null))
            }
        }
    }

    inner class NetworkStateViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(networkState: NetworkState) = with(view) {
            Log.e(this.javaClass.simpleName, "bind: NetworkStateViewHolder ")
            if (networkState != null && networkState.getStatus() == Status.RUNNING) {
                progress_bar.visibility = View.VISIBLE
            } else {
                progress_bar.visibility = View.GONE
            }

            if (networkState != null && networkState.getStatus() == Status.FAILED) {
                tv_error.visibility = View.VISIBLE
                tv_error.text = networkState.getMsg()
            } else {
                tv_error.visibility = View.GONE
            }
        }
    }
}