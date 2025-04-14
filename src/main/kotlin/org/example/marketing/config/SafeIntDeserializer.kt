package org.example.marketing.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class SafeIntDeserializer: JsonDeserializer<Int>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Int {
        return when {
            p.currentToken.isNumeric -> p.intValue
            p.currentToken == JsonToken.VALUE_STRING -> 1
            else -> -1
        }
    }
}