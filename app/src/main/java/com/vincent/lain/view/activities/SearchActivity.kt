package com.vincent.lain.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.vincent.lain.R
import com.vincent.lain.action
import com.vincent.lain.data.model.Menu
import com.vincent.lain.snack
import com.vincent.lain.view.adapters.SearchAdapter
import com.vincent.lain.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SearchActivity : BaseActivity() {

    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }

    private var adapter = SearchAdapter(mutableListOf()) { menu -> displayConfirmation(menu) }

    private lateinit var viewModel: SearchViewModel

    private lateinit var title: String

    override fun getToolbarInstance(): Toolbar? = toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        intent?.extras?.getString("title")?.let {
            title = it
        }
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        searchRecyclerView.adapter = adapter
        searchMenus()
    }

    private fun showLoading() {
        searchProgressBar.visibility = View.VISIBLE
        searchRecyclerView.isEnabled = false
    }

    private fun hideLoading() {
        searchProgressBar.visibility = View.GONE
        searchRecyclerView.isEnabled = true
    }

    private fun showMessage(message: String) {
        searchLayout.snack(message, Snackbar.LENGTH_INDEFINITE) {
            action(getString(R.string.ok)) {
                searchMenus()
            }
        }
    }

    private fun showEmptyResponseMessage(message: String) {
        searchLayout.snack(message, Snackbar.LENGTH_INDEFINITE) {
            action(getString(R.string.ok)) {
                finish()
            }
        }
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
        showLoading()
        viewModel.searchMenu(title).observe(this, Observer { menus ->
            hideLoading()
            when {
                menus == null -> {
                    showMessage(getString(R.string.network_error))
                }
                menus.isEmpty() -> {
                    showEmptyResponseMessage(getString(R.string.response_empty))
                }
                else -> {
                    adapter.setMenus(menus)
                }
            }
        })
    }
}