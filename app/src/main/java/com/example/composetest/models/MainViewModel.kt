package com.example.composetest.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    init {
        getListOfShoppingItems()
    }

    private fun getListOfShoppingItems() {
        viewModelScope.launch {
            _listOfItems.value = repository.getListOfItems()
        }
    }

    fun addToCart(item: ShoppingItem) {
        viewModelScope.launch {
            _listOfItems.value = repository.addToCart(item)
        }
    }
}