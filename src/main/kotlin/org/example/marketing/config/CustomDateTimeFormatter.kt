package org.example.marketing.config

import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Component
class CustomDateTimeFormatter {

    companion object {
        fun epochToString(epochTimeMills: Long): String {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREA)

            val localDateTime = LocalDateTime
                .ofInstant(Instant.ofEpochMilli(epochTimeMills * 1000), ZoneId.of("Asia/Seoul"))

            return localDateTime.format(formatter)
        }
    }
}