package org.exceptos.iamreading.di

import org.exceptos.iamreading.repo.BookRepository
import org.exceptos.iamreading.screens.book_lists.BookListsViewModel
import org.exceptos.iamreading.data.dao.BookDao
import org.exceptos.iamreading.data.db.AppDatabase
import org.exceptos.iamreading.data.model.BookStatus
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect fun platformModule(): Module

fun appModule() = module {

    factory { BookListsViewModel(status = BookStatus.CURRENTLY_READING) }

    single<BookDao>(named("PrimaryBookDao")) {
        get<AppDatabase>().getBookDao()
    }

    includes(platformModule())

    single { BookRepository(
        bookDao = get(named("PrimaryBookDao"))
    )}

}