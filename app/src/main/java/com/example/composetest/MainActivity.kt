package com.example.composetest

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.ui.graphics.Color
import com.example.composetest.models.ShoppingItem
import com.example.composetest.ui.ComposeTestTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Start()
        }
    }


    @Composable
    fun Start() {
        val listOfRestaurant = listOf(
            ShoppingItem("Chinese Restaurant"),
            ShoppingItem("Mexican Restauant"),
            ShoppingItem("FireFly seaside Food")
        )

        ComposeTestTheme {
            Surface {
                Column {
                    CommerceTopBar()
                    LazyColumn(content = {
                        items(
                            count = listOfRestaurant.size,
                            itemContent = { ShoppingItemCard(listOfRestaurant[it]) })
                    })

                }
            }
        }

    }

    @Composable
    fun ShoppingItemCard(item: ShoppingItem) {
        Card(elevation = 4.dp, modifier = Modifier.padding(top = 10.dp, bottom = 4.dp)) {
            Column(modifier = Modifier.padding(horizontal = 4.dp)) {
                Image(
                    painter = painterResource(R.drawable.random_image),
                    contentDescription = "restaurant image",
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_plus_green),
                        contentDescription = "add button",
                        alignment = Alignment.TopEnd
                    )
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
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_shopping_cart),
                        contentDescription = "shopping cart"
                    )
                }
            },
            elevation = 4.dp

        )
    }
}