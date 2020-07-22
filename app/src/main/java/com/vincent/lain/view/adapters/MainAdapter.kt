package com.vincent.lain.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vincent.lain.R
import com.vincent.lain.data.model.Menu
import com.vincent.lain.databinding.ItemMenuMainBinding
import com.vincent.lain.setImageUrl
import java.util.*


class MainAdapter(private val menuList: MutableList<Menu>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val selectedMenus = HashSet<Menu>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            R.layout.nothing_yet ->EmptyHolder(layoutInflater.inflate(R.layout.nothing_yet, parent, false))
            R.layout.item_menu_main -> MenusHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_menu_main, parent, false))
            else -> throw Exception("Not a valid view type")
        }
    }

    override fun getItemCount(): Int {
        return if (menuList.size > 0) menuList.size else 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (menuList.size) {
            0 -> R.layout.nothing_yet
            else -> R.layout.item_menu_main
        }
    }

    fun setMenus(menuList: List<Menu>) {
        this.menuList.clear()
        this.menuList.addAll(menuList)
        notifyDataSetChanged()
    }

    inner class MenusHolder(val binding: ItemMenuMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu) {
            binding.menu = menu
        if (!menu.image.isNullOrBlank()) {
            binding.menuImageview.setImageUrl(menu.image)
        } else {
            binding.menuImageview.setImageUrl(R.drawable.ic_baseline_room_service_24)
        }

        binding.checkbox.isChecked = selectedMenus.contains(menu)
        binding.checkbox.setOnCheckedChangeListener { checkbox, isChecked ->
            if (!selectedMenus.contains(menu) && isChecked) {
                selectedMenus.add(menu)
            } else {
                selectedMenus.remove(menu)
            }
        }
        }
    }

    inner class EmptyHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            R.layout.nothing_yet -> {
               holder as EmptyHolder
            }
            R.layout.item_menu_main -> {
                val menuHolder = holder as MenusHolder
                val menu = menuList[position]
                menuHolder.bind(menu)
            }
        }
    }
}