package com.ricknmorty.data.mappers

/**
 * Created by Tejas Deshmukh on 29/09/24.
 */

interface Mapper<I, O> {
    infix fun map(from: I): O
}