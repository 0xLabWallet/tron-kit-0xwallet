package com.wallet0x.tronkit.database

import android.content.Context
import com.wallet0x.tronkit.network.Network

internal object TronDatabaseManager {

    fun getMainDatabase(context: Context, network: Network, walletId: String): MainDatabase {
        return MainDatabase.getInstance(context, getDatabaseName(network, walletId))
    }

    fun clear(context: Context, network: Network, walletId: String) {
        synchronized(this) {
            context.deleteDatabase(getDatabaseName(network, walletId))
        }
    }

    private fun getDatabaseName(network: Network, walletId: String): String {
        return "Tron-${network.name}-$walletId"
    }

}
