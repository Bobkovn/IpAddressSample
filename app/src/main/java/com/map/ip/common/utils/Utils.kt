package com.map.ip.common.utils

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestinationDsl
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.map.ip.R

fun <ND : NavDirections> Activity.safeNavigate(
    @NavDestinationDsl currentDestination: Int,
    navDirection: ND? = null,
    @IdRes navDirectionId: Int? = null,
    bundle: Bundle? = null
) =
    findNavController(R.id.nav_host_fragment).takeIf { it.currentDestination?.id == currentDestination }
        ?.apply {
            navDirection?.let { navigate(it) }
                ?: navDirectionId?.let { navigate(it, bundle) }
        }

fun <ND : NavDirections> Fragment.safeNavigate(
    @NavDestinationDsl currentDestination: Int,
    navDirection: ND? = null,
    @IdRes navDirectionId: Int? = null,
    bundle: Bundle? = null
) =
    requireActivity().safeNavigate(currentDestination, navDirection, navDirectionId, bundle)
