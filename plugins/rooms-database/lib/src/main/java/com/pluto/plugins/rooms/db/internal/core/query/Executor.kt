package com.pluto.plugins.rooms.db.internal.core.query

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.pluto.plugins.rooms.db.internal.TableContents

/**
 * A class which is responsible for performing db operations.
 *
 */
internal class Executor private constructor(private val database: SupportSQLiteDatabase) {

    companion object {
        val instance: Executor
            get() = returnInstance()

        private fun returnInstance(): Executor {
            _instance?.let {
                return it
            }
            throw IllegalStateException("session not initialised")
        }

        private var _instance: Executor? = null

        /**
         * initialization function for [Executor].
         * Initializes [database] by using provided [databaseClass] and [databaseName].
         *
         * @param context [Context] of the accessing class
         * @param databaseClass a subclass of [RoomDatabase] registered in [Room] with @Database annotation
         * @param databaseName name of [RoomDatabase] class
         */
        @Synchronized
        fun initSession(context: Context, databaseName: String, databaseClass: Class<out RoomDatabase>): Executor {
            _instance?.let {
                throw IllegalStateException("session already initialised")
            } ?: run {
                val roomDatabase = Room.databaseBuilder(context, databaseClass, databaseName).build()
                _instance = Executor(roomDatabase.openHelper.writableDatabase)
                return _instance as Executor
            }
        }

        fun destroySession() {
            _instance = null
        }
    }

    /**
     * Query the db with given [query].
     * This method should be called on background thread
     *
     * @param query SQL query
     */
    @SuppressWarnings("TooGenericExceptionThrown")
    @Throws(Exception::class)
    internal fun query(query: String): TableContents {
        val c = database.query(query, null)
        c?.let {
            val columnNames = arrayListOf<String>()
            for (i in 0 until c.columnCount) columnNames.add(c.getColumnName(i))
            val rows = mutableListOf<ArrayList<String>>()
            c.moveToFirst()
            do {
                val rowValues = arrayListOf<String>()
                for (i in 0 until c.columnCount) {
                    val value = c.getString(i)
                    rowValues.add(value)
                }
                if (rowValues.isNotEmpty()) {
                    rows.add(rowValues)
                }
            } while (c.moveToNext())
            c.close()
            return TableContents(columnNames, rows)
        }
        throw Exception()
    }

    /**
     * Executes the given [query].
     * This method should be called on background thread
     *
     * @param query SQL query
     */
    @Throws(Exception::class)
    internal fun execSQL(query: String) {
        database.execSQL(query)
    }
}
