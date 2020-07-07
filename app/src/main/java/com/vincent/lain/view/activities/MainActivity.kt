package com.vincent.lain.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vincent.lain.R
import com.vincent.lain.view.adapters.MainAdapter
import com.vincent.lain.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : BaseActivity() {
    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    private val adapter = MainAdapter(mutableListOf())
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        menusRecyclerView.adapter = adapter
        showLoading()
        viewModel.getSavedMenus().observe(this, Observer { menus ->
            hideLoading()
            menus?.let {
                adapter.setMenus(menus)
            }
        })
    }

    private fun showLoading() {
        menusRecyclerView.isEnabled = false
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        menusRecyclerView.isEnabled = true
        progressBar.visibility = View.GONE
    }

    private fun deleteMenusClicked() {
        for (menu in adapter.selectedMenus) {
            viewModel.deleteSavedMenus(menu)
        }
    }

    override fun getToolbarInstance(): Toolbar? = toolbar

    fun goToAddActivity(view: View) = startActivity<AddMenuActivity>()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> this.deleteMenusClicked()
            else -> toast(getString(R.string.error))
        }
        return super.onOptionsItemSelected(item)
    }

}