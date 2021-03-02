package com.example.composetest

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetest.models.MainViewModel
import com.example.composetest.repository.RepositoryImpl
import com.example.composetest.models.ShoppingItem
import com.example.composetest.ui.ComposeTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingItemScreen(viewModel = MainViewModel(repository = RepositoryImpl()))
        }
    }

    @ExperimentalFoundationApi
    @Composable
    fun ShoppingItemScreen(
        viewModel: MainViewModel
    ) {
        val listOfItems by viewModel.listOfItems.observeAsState(emptyList())
        val listOfCartItems by viewModel.numberOfCartItems.observeAsState(0)

        ComposeTestTheme {
            Surface {
                Column {
                    CommerceTopBar(listOfCartItems) { }
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(2),
                        modifier = Modifier.padding(
                            start = 12.dp,
                            end = 12.dp
                        )
                    ) {
                        items(listOfItems.size) { shoppingItem ->
                            ShoppingItemCard(item = listOfItems[shoppingItem],
                                onClick = {
                                    viewModel.addOrRemoveFromCart(it)
                                })
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun ShoppingItemCard(
        item: ShoppingItem,
        onClick: (ShoppingItem) -> Unit
    ) {
        var isAdd by rememberSaveable { mutableStateOf(true) }
        Card(
            elevation = 4.dp,
            modifier = Modifier.padding(top = 10.dp, bottom = 4.dp, end = 8.dp)
        ) {
            Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                Image(
                    painter = painterResource(R.drawable.random_image),
                    contentDescription = "shopping item image",
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Box {

                        if (isAdd) {
                            Image(
                                painter = painterResource(R.drawable.ic_plus_green),
                                contentDescription = "add button",
                                alignment = Alignment.TopEnd,
                                modifier = Modifier.clickable {
                                    isAdd = !isAdd
                                    onClick(item)
                                }
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.ic_minus_circle),
                                contentDescription = "remove button",
                                alignment = Alignment.TopEnd,
                                modifier = Modifier.clickable {
                                    isAdd = !isAdd
                                    onClick(item)
                                }
                            )

                        }
                    }
                }
            }

        }
    }

    @Composable
    fun CommerceTopBar(number: Int, onChange: () -> Unit) {
        TopAppBar(
            title = {
                Text(text = "Place Order", textAlign = TextAlign.Center)
            },
            backgroundColor = MaterialTheme.colors.primary,
            actions = {
                Cart(number, onChange)
            },
            elevation = 4.dp
        )
    }


    @Composable
    fun Cart(items: Int, onChange: () -> Unit) {
        Box() {
            IconButton(onClick = onChange) {
                Icon(
                    painter = painterResource(R.drawable.ic_shopping_cart),
                    contentDescription = "shopping cart"
                )
            }
            if (items > 0) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(Color.Red).fillMaxWidth()
                ) {
                    Text(
                        text = items.toString(), style = MaterialTheme.typography.h6.copy(
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }
    }

    @Composable
    fun CartScreen() {

    }
}