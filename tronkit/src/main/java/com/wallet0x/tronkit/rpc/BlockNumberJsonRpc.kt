package com.wallet0x.tronkit.rpc

class BlockNumberJsonRpc : LongJsonRpc(
        method = "eth_blockNumber",
        params = listOf()
)
