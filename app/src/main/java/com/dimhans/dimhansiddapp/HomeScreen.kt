package com.dimhans.dimhansiddapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

data class HomeCardItem(
    val title: String,
    val imageRes: Int,
    val route: String
)

@Composable
fun HomeCard(item: HomeCardItem, onCardClick: (String) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .fillMaxWidth()
            .clickable { onCardClick(item.route) }
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                ),
                color = Color(0xFF1565C0) // soft blue
            )
        }
    }
}

@Composable
fun HomeScreen(onCardClick: (String) -> Unit) {
    val brush = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.background
        )
    )

    val cardItems = listOf(
        HomeCardItem("How to Brush Your Teeth", R.drawable.brushteeth, Screen.BrushTeeth.route),
        HomeCardItem("Bathing", R.drawable.bath, Screen.WashHands.route),
        HomeCardItem("Dressing", R.drawable.clothess, Screen.ReadyForSchool.route),
        HomeCardItem("Eating Healthy", R.drawable.eating, Screen.EatingHealthy.route)
    )

    Scaffold(
//        topBar = { MyTopAppBar() }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(brush)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(cardItems) { item ->
                        HomeCard(item = item, onCardClick = onCardClick)
                    }
                }
            }
        }
    }
}
