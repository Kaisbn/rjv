package com.psyclik.ai.data

import io.vertx.core.buffer.Buffer
import io.vertx.core.eventbus.MessageCodec
import io.vertx.core.json.Json

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
class JsonCodec<TYPE>(val forClass: Class<TYPE>) : MessageCodec<TYPE, TYPE> {
    override fun encodeToWire(buffer: Buffer?, input: TYPE?) {
        buffer?.appendString(Json.encode(input))
    }

    override fun name(): String = "${forClass}_CODEC"

    override fun decodeFromWire(size: Int, buffer: Buffer?): TYPE = Json.decodeValue(buffer?.getString(0, size), forClass)

    override fun systemCodecID(): Byte = -1

    override fun transform(input: TYPE?): TYPE = input!!

}