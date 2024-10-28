package com.ricknmorty

import com.helpers.network.Success
import com.ricknmorty.characters.presentation.character_details.viewmodel.CharacterDetailsViewModel
import com.ricknmorty.characters.presentation.character_details.viewmodel.models.CharacterDetailsEvents
import com.ricknmorty.characters.presentation.character_details.viewmodel.models.CharacterDetailsState
import com.ricknmorty.data.domain.usecases.characters.IGetCharacterDetailsUseCase
import com.ricknmorty.data.domain.usecases.episodes.IGetSelectedEpisodesUseCase
import com.ricknmorty.data.mappers.CharacterToCharacterDetailsUIModel
import com.ricknmorty.data.mappers.EpisodeDtoEpisodeUIModel
import com.ricknmorty.data.responses.Character
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import java.lang.Exception

/**
 * Created by Tejas Deshmukh on 28/10/24.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModelTest : StringSpec({

    val dispatcher = UnconfinedTestDispatcher()
    val getCharacterDetailsUseCase: IGetCharacterDetailsUseCase = spyk()
    val getSelectedEpisodesUseCase: IGetSelectedEpisodesUseCase = spyk()
    val characterToCharacterDetailsUIModel: CharacterToCharacterDetailsUIModel = spyk()
    val episodeDtoEpisodeUIModel: EpisodeDtoEpisodeUIModel = spyk()
    var viewModel: CharacterDetailsViewModel? = null

    beforeTest {
        viewModel = CharacterDetailsViewModel(dispatcher, getCharacterDetailsUseCase, getSelectedEpisodesUseCase, characterToCharacterDetailsUIModel, episodeDtoEpisodeUIModel)
    }

    "Test Fetch Character Details" {
        val characterDetails = Character(1, null, null, null, null, null, null, null, null, null, null, null)

        coEvery { getCharacterDetailsUseCase.invoke(1) }.returns(Success(characterDetails))

        viewModel?.onEvent(CharacterDetailsEvents.FetchCharacterDetails(1))

        viewModel?.state?.value shouldBe CharacterDetailsState(isLoading = false, characterDetails = characterToCharacterDetailsUIModel.map(characterDetails))
    }
})