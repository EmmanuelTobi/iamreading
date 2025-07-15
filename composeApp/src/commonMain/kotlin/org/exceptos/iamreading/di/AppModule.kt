package org.exceptos.iamreading.di

import org.exceptos.iamreading.repo.BookRepository
import org.exceptos.iamreading.screens.book_lists.BookListsViewModel
import org.exceptos.iamreading.data.dao.BookDao
import org.exceptos.iamreading.data.dao.BookNoteDao
import org.exceptos.iamreading.data.dao.StatsDao
import org.exceptos.iamreading.data.db.AppDatabase
import org.exceptos.iamreading.data.model.BookStatus
import org.exceptos.iamreading.screens.home.HomeViewmodel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

expect fun platformModule(): Module

fun appModule() = module {

    factory { BookListsViewModel(status = BookStatus.CURRENTLY_READING) }

    factory { HomeViewmodel() }

    single<BookDao>(named("PrimaryBookDao")) {
        get<AppDatabase>().getBookDao()
    }

    single<StatsDao>(named("StatsDao")) {
        get<AppDatabase>().getStatsDao()
    }

    single<BookNoteDao>(named("BookNotesDao")) {
        get<AppDatabase>().getBookNotesDao()
    }

    includes(platformModule())

    single { BookRepository(
        bookDao = get(named("PrimaryBookDao")),
        statsDao = get(named("StatsDao")),
        noteDao = get(named("BookNotesDao"))
    )}

}