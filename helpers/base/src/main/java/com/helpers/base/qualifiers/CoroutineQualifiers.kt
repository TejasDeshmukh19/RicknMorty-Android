package com.helpers.base.qualifiers

import javax.inject.Qualifier

/**
 * Created by Tejas Deshmukh on 03/10/24.
 */

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher