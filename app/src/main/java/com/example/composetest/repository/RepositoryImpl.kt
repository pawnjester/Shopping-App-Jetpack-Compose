package com.example.composetest.repository

import com.example.composetest.models.ShoppingItem

class RepositoryImpl : Repository {

    private val listOfCart = mutableListOf<ShoppingItem>()


    override suspend fun getListOfItems(): List<ShoppingItem> {
        return shoppingLists()
    }

    override suspend fun addToCart(item: ShoppingItem): List<ShoppingItem> {
        val itemFound = listOfCart.contains(item)
        if (itemFound) {
            listOfCart.remove(item)
        } else {
            listOfCart.add(item)
        }
        return listOfCart
    }

    private fun shoppingLists(): List<ShoppingItem> {
        return listOf(
            ShoppingItem("1", "Bag", "This is a gucci bag"),
            ShoppingItem("2", "Mexican food", "Here you can eat anything you want"),
            ShoppingItem("3", "Food", "Eat and enjoy the sea breeze"),
            ShoppingItem("4", "Laptop", "Eat and enjoy the sea breeze"),
            ShoppingItem("5", "Pliers", "This is a pliers"),
            ShoppingItem("6", "Scanner", "Use the scanner to play around")
        )
    }
}