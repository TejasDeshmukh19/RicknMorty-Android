package com.ricknmorty

import com.helpers.network.ApiResponse
import com.helpers.network.Loading
import com.helpers.network.Success
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.CharactersListViewModel
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.models.CharactersListEvents
import com.ricknmorty.characters.presentation.characters_listing.viewmodel.models.CharactersListState
import com.ricknmorty.data.domain.usecases.characters.IGetAllCharactersUseCase
import com.ricknmorty.data.mappers.CharacterToCharacterUIModel
import com.ricknmorty.data.responses.Character
import com.ricknmorty.data.responses.CharactersPageDto
import com.ricknmorty.data.responses.PaginationInfo
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersListViewModelTest : StringSpec({
    val getAllCharactersUseCase: IGetAllCharactersUseCase = mockk()
    val characterToCharacterUIModel: CharacterToCharacterUIModel = spyk()
    val dispatcher = UnconfinedTestDispatcher()

    "Test Fetch Characters List Page" {
        val viewModel = CharactersListViewModel(
            ioDispatcher = dispatcher,
            getAllCharactersUseCase = getAllCharactersUseCase,
            characterToCharacterUIModel = characterToCharacterUIModel
        )

        val channel = Channel<ApiResponse<CharactersPageDto>>()
        val data = CharactersPageDto(
            info = PaginationInfo(826, 20, "2", "1"),
            results = (1..20).map { index ->
                Character(
                    id = index,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            })
        coEvery { getAllCharactersUseCase.invoke(1) }.returns(channel.consumeAsFlow())

        channel.trySend(Loading(true))

        viewModel.onEvent(CharactersListEvents.OnLoadNextPage)

        channel.trySend(Success(data))

        viewModel.state.first() shouldBe CharactersListState(
            characters = ((data.results ?: emptyList()).mapNotNull {
                characterToCharacterUIModel.map(it)
            }), isLoading = false)
    }
})