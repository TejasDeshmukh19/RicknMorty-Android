package com.ricknmorty.characters.presentation.character_details.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.sp
import com.ricknmorty.data.ui_models.CharacterDetailsUIModel
import com.ricknmorty.ui_resources.R

/**
 * Created by Tejas Deshmukh on 14/10/24.
 */

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InformationContainer(
    isExpanded: Boolean,
    characterDetails: CharacterDetailsUIModel,
    onToggle: () -> Unit
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
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .then(toggleAction)
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1F),
                    text = stringResource(R.string.personal_information),
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
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                FlowRow(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    maxItemsInEachRow = 2
                ) {
                    val itemModifier = Modifier
                        .padding(8.dp)
                        .weight(1f)
                    repeat(6) {
                        InfoItem(
                            modifier = itemModifier,
                            label = stringResource(id = characterDetails.personalInfo[it].label),
                            data = characterDetails.personalInfo[it].data
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InfoItem(modifier: Modifier, label: String, data: String?) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = label,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = data ?: "",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}