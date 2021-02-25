package com.map.ip.common

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import com.map.ip.R
import com.map.ip.common.data.base.BaseError
import com.map.ip.common.data.base.NetworkError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getNavController().addOnDestinationChangedListener { _, _, _ -> hideKeyboard() }
    }

    private fun getNavController() = findNavController(R.id.nav_host_fragment)

    fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(rootActivityView.windowToken, 0)
    }

    fun handleCommonNetworkErrors(cause: BaseError) {
        changeProgressBarVisibility(false)
        when (cause) {
            is NetworkError.NoConnection -> showErrorDialog(getString(R.string.no_connection_message))
            is NetworkError -> showErrorDialog(
                cause.message ?: getString(R.string.network_error_message)
            )
            else -> showErrorDialog(cause.message ?: getString(R.string.something_went_wrong))
        }
    }

    private fun showErrorDialog(message: String) = Toast.makeText(
        this,
        message,
        Toast.LENGTH_SHORT
    ).show()

    fun changeProgressBarVisibility(isVisible: Boolean) {
        progressBar.isVisible = isVisible
    }
}