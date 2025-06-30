package org.exceptos.iamreading.di

import org.exceptos.iamreading.screens.book_lists.BookListsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

fun appModule() = module {
    factory { BookListsViewModel() }
}