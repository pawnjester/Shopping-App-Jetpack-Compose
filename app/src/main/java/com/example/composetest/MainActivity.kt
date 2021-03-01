package com.example.composetest

import android.os.Bundle
import android.view.Gravity
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
            Start()
        }
    }


    @ExperimentalFoundationApi
    @Composable
    fun Start() {
        HomeScreen(viewModel = MainViewModel(repository = RepositoryImpl()))
    }

    @ExperimentalFoundationApi
    @Composable
    fun HomeScreen(viewModel: MainViewModel) {
        val listOfItems by viewModel.listOfItems.observeAsState(emptyList())
        ShoppingItemScreen(list = listOfItems, onClick = {
            viewModel.addToCart(it)
        })

    }

    @ExperimentalFoundationApi
    @Composable
    fun ShoppingItemScreen(list: List<ShoppingItem>, onClick: (ShoppingItem) -> Unit) {
        ComposeTestTheme {
            Surface {
                Column {
                    CommerceTopBar()
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(2),
                        modifier = Modifier.padding(
                            start = 12.dp,
                            end = 12.dp
                        )
                    ) {
                        items(list.size) { shoppingItem ->
                            ShoppingItemCard(item = list[shoppingItem], onClick = onClick)
                        }
                    }

                }
            }
        }
    }

    @Composable
    fun ShoppingItemCard(item: ShoppingItem, onClick: (ShoppingItem) -> Unit) {
        Card(elevation = 4.dp, modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)) {
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
                        Image(
                            painter = painterResource(R.drawable.ic_plus_green),
                            contentDescription = "add button",
                            alignment = Alignment.TopEnd,
                        )
                        Image(
                            painter = painterResource(R.drawable.ic_minus_circle),
                            contentDescription = "remove button",
                            alignment = Alignment.TopEnd,
                            modifier = Modifier.clickable { onClick(item) }
                        )
                    }
                }
            }

        }
    }

    @Composable
    fun CommerceTopBar() {
        TopAppBar(
            title = {
                Text(text = "Shop Now")
            },
            backgroundColor = MaterialTheme.colors.primary,
            actions = {
                CartItem()
            },
            elevation = 4.dp
        )
    }


    @Composable
    fun CartItem(text : String = "") {
        Box() {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.ic_shopping_cart),
                    contentDescription = "shopping cart"
                )
            }
            Circle()
            Text(
                text = text, style = MaterialTheme.typography.h6.copy(
                    color = Color.White,
                    fontSize = 18.sp
                )
            )
        }
    }


    @Composable
    fun Circle() {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(Color.Red)
        )
    }
}