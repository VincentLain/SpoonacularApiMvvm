package com.vincent.lain.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.vincent.lain.R
import com.vincent.lain.action
import com.vincent.lain.data.model.Menu
import com.vincent.lain.snack
import com.vincent.lain.view.adapters.MenuPagedListAdapter
import com.vincent.lain.viewmodel.SearchViewModel
import com.vincent.lain.viewmodel.SearchViewModelFactory
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SearchActivity : BaseActivity() {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    private val adapter by lazy { MenuPagedListAdapter(){menu -> displayConfirmation(menu)} }

    private lateinit var viewModel: SearchViewModel
    private lateinit var viewModelFactory: SearchViewModelFactory

    private lateinit var title: String

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        intent?.extras?.getString("title")?.let {
            title = it
        }
        viewModelFactory = SearchViewModelFactory(title)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        searchRecyclerView.adapter = adapter
        searchMenus()

        viewModel.networkState.observe(this, Observer { networkState ->
            Log.e("NetworkState: ", networkState.getMsg()  + " " + networkState.getStatus())
            adapter.setNetworkState(networkState)
        })
    }

    private fun displayConfirmation(menu: Menu) {
        searchLayout.snack("Add ${menu.title} to your list?", Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
                viewModel.saveMenu(menu)
                startActivity(intentFor<MainActivity>().newTask().clearTask())
            }
        }
    }

    private fun searchMenus() {
        viewModel.menus.observe(this, Observer { menus ->
            adapter.submitList(menus)
        })
    }
}