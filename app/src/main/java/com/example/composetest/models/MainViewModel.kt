package com.example.composetest.models

import androidx.lifecycle.*
import com.example.composetest.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _listOfItems = MutableLiveData<List<ShoppingItem>>()
    val listOfItems: LiveData<List<ShoppingItem>> = _listOfItems

    private val _numberOfCartItems = MutableLiveData<Int>()
    val numberOfCartItems: LiveData<Int> = _numberOfCartItems

    private val _listOfCartItems = MutableLiveData<List<ShoppingItem>>()
    val listOfCartItems: LiveData<List<ShoppingItem>> = _listOfCartItems


    init {
        getListOfShoppingItems()
    }

    private fun getListOfShoppingItems() {
        viewModelScope.launch {
            _listOfItems.value = repository.getListOfItems()
        }
    }

    fun addOrRemoveFromCart(item: ShoppingItem) {
        viewModelScope.launch {
            _numberOfCartItems.value = repository.addToCart(item).size
        }
    }

    fun getCartItems() {
        viewModelScope.launch {
            _listOfCartItems.value = repository.getCartItems()
        }
    }
}