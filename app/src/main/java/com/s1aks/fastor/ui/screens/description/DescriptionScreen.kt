package com.s1aks.fastor.ui.screens.description

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.s1aks.fastor.R
import com.s1aks.fastor.data.entities.DataModel
import com.s1aks.fastor.ui.MainViewModel

@Composable
fun DescriptionScreen(viewModel: MainViewModel) {
    viewModel.showHistoryIcon = false
    viewModel.clickedData?.let { DescriptionCard(it) }
}

@Composable
fun DescriptionCard(data: DataModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 4.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                text = data.text.toString()
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = data.meanings?.joinToString { it.translation?.translation.toString() }
                    .toString()

            )
            AsyncImage(
                model = "https:${data.meanings?.get(0)?.imageUrl}",
                placeholder = painterResource(R.drawable.ic_placeholder_vector),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
                    .clip(RoundedCornerShape(5.dp)),
                contentDescription = stringResource(R.string.description_picture_content_description)
            )
        }
    }
}