package com.wallet0x.tronkit.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wallet0x.tronkit.models.ChainParameter

@Dao
interface ChainParameterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chainParameters: List<ChainParameter>)

    @Query("SELECT * FROM ChainParameter WHERE `key`=:key")
    fun getChainParameter(key: String): ChainParameter?
}
