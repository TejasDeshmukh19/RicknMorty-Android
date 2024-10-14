package com.ricknmorty.characters.presentation.character_details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ricknmorty.data.ui_models.EpisodeUIModel
import com.ricknmorty.ui_resources.R

/**
 * Created by Tejas Deshmukh on 01/10/24.
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SelectedEpisodeSheet(episode: EpisodeUIModel, onDismiss: () -> Unit) {

    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Chip(
                modifier = Modifier.padding(start = 16.dp),
                onClick = { },
                colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = episode.code ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
            Text(
                modifier = Modifier.padding(16.dp, vertical = 8.dp),
                text = episode.name ?: "",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )

            val characterImages =
                episode.characterIds?.map { "https://rickandmortyapi.com/api/character/avatar/${it}.jpeg" }
            if (characterImages.isNullOrEmpty().not()) {
                Text(
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                    text = stringResource(id = R.string.characters),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )

                ImagesHorizontalList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp), images = characterImages ?: emptyList()
                )
            }
        }
    }


}

@Composable
fun ImagesHorizontalList(modifier: Modifier, images: List<String>) {
    LazyRow(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(12.dp)) {
        items(images) {
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1F),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                AsyncImage(modifier = Modifier.fillMaxSize(), model = it, contentDescription = null)
            }
        }
    }
}