package com.android.ricknmorty

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//

        val flow = flow<Int> {
            repeat(5) {
                delay(2000L)
                emit(it)
            }
        }.flowOn(Dispatchers.IO)



        GlobalScope.launch(Dispatchers.Default) {
            flow.collectLatest {
                print(it)
            }
            assertEquals(4, 2 + 2)
        }
    }
}