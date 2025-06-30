package org.exceptos.iamreading.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.exceptos.iamreading.data.db.BookDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = BookDatabase.Schema,
            name = "book.db"
        )
    }
}