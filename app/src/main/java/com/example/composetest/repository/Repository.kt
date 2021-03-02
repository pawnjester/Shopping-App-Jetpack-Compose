package com.example.composetest.repository

import com.example.composetest.models.ShoppingItem

interface Repository {

    suspend fun getListOfItems() : List<ShoppingItem>

    suspend fun addToCart(item: ShoppingItem) : List<ShoppingItem>

    suspend fun getCartItems() : List<ShoppingItem>
}