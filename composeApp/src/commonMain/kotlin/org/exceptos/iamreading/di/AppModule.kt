package org.exceptos.iamreading.di

import org.exceptos.iamreading.repo.BookRepository
import org.exceptos.iamreading.screens.book_lists.BookListsViewModel
import org.koin.dsl.module

fun appModule() = module {

    factory { BookListsViewModel() }

    single { BookRepository(
        bookDao = get()
    )}

}