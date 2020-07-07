package com.vincent.lain.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.vincent.lain.R
import com.vincent.lain.action
import com.vincent.lain.databinding.ActivityAddMenuBinding
import com.vincent.lain.snack
import com.vincent.lain.viewmodel.AddViewModel
import kotlinx.android.synthetic.main.activity_add_menu.*
import kotlinx.android.synthetic.main.toolbar_view_custom_layout.*
import org.jetbrains.anko.intentFor

class AddMenuActivity : BaseActivity() {
    private val toolbar: Toolbar by lazy { toolbar_toolbar_view as Toolbar }
    private lateinit var viewModel: AddViewModel

    override fun getToolbarInstance(): Toolbar? = toolbar

    fun searchMenuClicked(view: View) {
        if (titleEditText.text.toString().isNotBlank()) {
            startActivity(intentFor<SearchActivity>("title" to titleEditText.text.toString()))
        } else {
            showMessage(getString(R.string.enter_title))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddMenuBinding>(this, R.layout.activity_add_menu)
        viewModel = ViewModelProviders.of(this).get(AddViewModel::class.java)
        binding.viewModel = viewModel
        configureLiveDataObservers()
    }

    private fun showMessage(msg: String) {
        addLayout.snack((msg), Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
            }
        }
    }

    private fun configureLiveDataObservers() {
        viewModel.getSaveLiveData().observe(this, Observer { saved ->
            saved?.let {
                if (saved) {
                    finish()
                } else {
                    showMessage(getString(R.string.enter_title))
                }
            }
        })
    }
}