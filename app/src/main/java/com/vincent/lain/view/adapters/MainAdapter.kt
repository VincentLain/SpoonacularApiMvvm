package com.vincent.lain.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vincent.lain.R
import com.vincent.lain.databinding.ItemMenuMainBinding
import com.vincent.lain.data.model.Menu
import com.vincent.lain.setImageUrl

import java.util.HashSet


class MainAdapter(private val menuList: MutableList<Menu>) :
    RecyclerView.Adapter<MainAdapter.MenusHolder>() {

    val selectedMenus = HashSet<Menu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenusHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMenuMainBinding>(
            layoutInflater,
            R.layout.item_menu_main, parent, false
        )
        return MenusHolder(binding)
    }

    override fun onBindViewHolder(holder: MenusHolder, position: Int) {
        val menu = menuList[position]
        holder.binding.menu = menu
        if (!menu.image.isNullOrBlank()) {
            holder.binding.menuImageview.setImageUrl(menu.image)
        } else {
            holder.binding.menuImageview.setImageUrl(R.drawable.ic_baseline_room_service_24)
        }

        holder.binding.checkbox.isChecked = selectedMenus.contains(menu)
        holder.binding.checkbox.setOnCheckedChangeListener { checkbox, isChecked ->
            if (!selectedMenus.contains(menu) && isChecked) {
                selectedMenus.add(menu)
            } else {
                selectedMenus.remove(menu)
            }
        }
    }

    override fun getItemCount(): Int = menuList.size

    fun setMenus(menuList: List<Menu>) {
        this.menuList.clear()
        this.menuList.addAll(menuList)
        notifyDataSetChanged()
    }

    inner class MenusHolder(val binding: ItemMenuMainBinding) :
        RecyclerView.ViewHolder(binding.root)
}