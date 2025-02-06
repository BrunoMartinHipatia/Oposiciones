package com.example.oposiciones.component

import com.example.oposiciones.R


enum class NavigationItem(
    val title: String,
    val icon: Int
) {
    Home(
        icon = R.drawable.home,
        title = "Home"
    ),
    Profile(
        icon = R.drawable.home,
        title = "Profile"
    ),
    Premium(
        icon = R.drawable.home,
        title = "Premium"
    ),
    Settings(
        icon = R.drawable.home,
        title = "Settings"
    )
}