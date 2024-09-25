package com.wallet0x.tronkit.rpc

class GasPriceJsonRpc : LongJsonRpc(
        method = "eth_gasPrice",
        params = listOf()
)
