package com.wallet0x.tronkit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wallet0x.tronkit.models.Balance
import com.wallet0x.tronkit.models.ChainParameter
import com.wallet0x.tronkit.models.InternalTransaction
import com.wallet0x.tronkit.models.LastBlockHeight
import com.wallet0x.tronkit.models.Transaction
import com.wallet0x.tronkit.models.TransactionSyncState
import com.wallet0x.tronkit.models.TransactionTag
import com.wallet0x.tronkit.models.Trc20EventRecord

@Database(
    entities = [
        LastBlockHeight::class,
        Balance::class,
        TransactionSyncState::class,
        Transaction::class,
        InternalTransaction::class,
        Trc20EventRecord::class,
        TransactionTag::class,
        ChainParameter::class,
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class MainDatabase : RoomDatabase() {

    abstract fun lastBlockHeightDao(): LastBlockHeightDao
    abstract fun balanceDao(): BalanceDao
    abstract fun transactionDao(): TransactionDao
    abstract fun tagsDao(): TransactionTagDao
    abstract fun chainParameterDao(): ChainParameterDao

    companion object {
        fun getInstance(context: Context, databaseName: String): MainDatabase {
            return Room.databaseBuilder(context, MainDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }
}
