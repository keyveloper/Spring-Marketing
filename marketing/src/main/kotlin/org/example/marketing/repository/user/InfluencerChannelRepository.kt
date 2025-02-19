package org.example.marketing.repository.user

import org.example.marketing.dto.user.request.InsertInfluencerChannel
import org.example.marketing.entity.user.InfluencerChannel
import org.example.marketing.table.InfluencerChannelsTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class InfluencerChannelRepository {

    private fun ResultRow.toInfluencerChannel() = InfluencerChannel(
        id = this[InfluencerChannelsTable.id].value,
        influencerId = this[InfluencerChannelsTable.influencerId],
        channelCode = this[InfluencerChannelsTable.channelCode],
        url = this[InfluencerChannelsTable.url],
    )

    fun insert(influencerChannel: InsertInfluencerChannel): Long? = transaction {
        val newEntityId = InfluencerChannelsTable.insertAndGetId {
            it[influencerId] = influencerChannel.influencerId
            it[channelCode] = influencerChannel.channelCode
            it[url] = influencerChannel.url
        }

        newEntityId.value
    }

    fun insertAll(channels: List<InsertInfluencerChannel>): Int = transaction {
        InfluencerChannelsTable.batchInsert(channels) { channel ->
            this[InfluencerChannelsTable.influencerId] = channel.influencerId
            this[InfluencerChannelsTable.channelCode] = channel.channelCode
            this[InfluencerChannelsTable.url] = channel.url
        }.size
    }
}