package com.map.ip.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.map.ip.common.MainActivity
import com.map.ip.common.data.BaseError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@AndroidEntryPoint
abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    abstract val layout: Int

    protected abstract fun setupViews()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    protected fun getToolbar(): MaterialToolbar = (requireActivity() as MainActivity).toolbar

    protected fun hideKeyboard() = (activity as? MainActivity)?.hideKeyboard()

    protected fun showMessage(message: Any) =
        Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()

    protected val commonNetworkErrorObserver: (BaseError) -> Unit = { error ->
        (activity as? MainActivity)?.handleCommonNetworkErrors(error)
    }

    protected fun <T : ViewModel> Fragment.obtainViewModel(clazz: Class<T>) =
        createViewModelLazy(clazz.kotlin, {
            this.viewModelStore
        })
}