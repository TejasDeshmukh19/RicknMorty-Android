package com.ricknmorty.characters.presentation.character_details.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ricknmorty.data.ui_models.EpisodeUIModel
import com.ricknmorty.ui_resources.R
import com.ricknmorty.ui_resources.composables.NoInternetLayout
import com.ricknmorty.ui_resources.composables.ProgressIndicator

/**
 * Created by Tejas Deshmukh on 14/10/24.
 */

@Composable
fun EpisodesContainer(
    isExpanded: Boolean,
    isNetworkAvailable: Boolean,
    episodes: List<EpisodeUIModel>,
    onToggle: () -> Unit,
    onRefreshEpisodes: () -> Unit,
    onEpisodeClicked: (Int) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val toggleAction = Modifier.clickable(
        interactionSource = interactionSource,
        indication = LocalIndication.current,
        onClick = onToggle
    )

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(toggleAction)
                    .padding(horizontal = 18.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1F),
                    text = stringResource(R.string.episodes),
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .then(toggleAction),
                    painter = painterResource(id = if (isExpanded) R.drawable.ic_collapse else R.drawable.ic_expand),
                    contentDescription = null
                )
            }
            AnimatedVisibility(visible = isExpanded) {
                Divider()
                NoInternetLayout(
                    modifier = Modifier,
                    isNetworkAvailable,
                    action = onRefreshEpisodes
                ) {
                    if (episodes.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            ProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    } else {
                        Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                            repeat(episodes.size) {
                                EpisodeItem(
                                    episode = episodes[it],
                                    onEpisodeClicked = onEpisodeClicked
                                )
                                Divider(color = MaterialTheme.colorScheme.outlineVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EpisodeItem(episode: EpisodeUIModel, onEpisodeClicked: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                episode.id?.let(onEpisodeClicked)
            }.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = episode.name ?: "",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(18.dp),
                painter = painterResource(id = R.drawable.ic_released_on),
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 4.dp),
                text = "${episode.releasedOn}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = episode.code ?: "",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}