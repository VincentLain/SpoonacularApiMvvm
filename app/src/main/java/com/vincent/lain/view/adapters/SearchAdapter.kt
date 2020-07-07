package com.vincent.lain.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.vincent.lain.R
import com.vincent.lain.data.model.Menu
import kotlinx.android.synthetic.main.item_menu_main.view.*

class SearchAdapter(var menuList: MutableList<Menu>,
                    var listener: (Menu) -> Unit) :
    RecyclerView.Adapter<SearchAdapter.MenuHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
       val view = LayoutInflater.from(parent.context)
           .inflate(R.layout.item_menu_search, parent, false)
        return MenuHolder(view)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(menuList[position], position)
    }

    override fun getItemCount(): Int = menuList.size

    fun setMenus(menuList: List<Menu>) {
        this.menuList.clear()
        this.menuList.addAll(menuList)
        notifyDataSetChanged()
    }

    inner class MenuHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(menu: Menu, position: Int) = with(view) {
            title_textview.text = menu.title
            view.setOnClickListener { listener(menuList[position]) }
            if (menu.image != null)
                Picasso.get().load(menu.image).into(menu_imageview)
            else {
                menu_imageview.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_room_service_24, null))
            }
        }
    }
}