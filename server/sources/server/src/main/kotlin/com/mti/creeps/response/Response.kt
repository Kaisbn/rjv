package com.mti.creeps.response

/**
 * You may want to add a meaningful comment here.
 *
 * @author PSX
 * @since 1.0-SNAPSHOT
 */
interface Response

data class NoRespomse(val opcode: String = "noresponse") : Response