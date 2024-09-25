package com.wallet0x.tronkit.decoration

import com.wallet0x.tronkit.models.InternalTransaction
import com.wallet0x.tronkit.models.TriggerSmartContract

interface ITransactionDecorator {
    fun decoration(
        contract: TriggerSmartContract,
        internalTransactions: List<InternalTransaction>,
        events: List<Event>
    ): TransactionDecoration?
}
