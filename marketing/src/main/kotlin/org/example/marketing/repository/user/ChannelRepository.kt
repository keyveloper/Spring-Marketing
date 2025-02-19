package org.example.marketing.repository.user

import org.example.marketing.entity.user.Channel
import org.example.marketing.table.ChannelsTable
import org.jetbrains.exposed.sql.ResultRow
import org.springframework.stereotype.Repository

@Repository
class ChannelRepository {

    private fun ResultRow.toChannel() = Channel(
        code = this[ChannelsTable.code],
        name = this[ChannelsTable.name]
    )


}