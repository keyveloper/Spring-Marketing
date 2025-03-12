package org.example.marketing.dto.board.request

import org.example.marketing.enum.ChannelType

data class GetAdvertisementRequestByChannels(
    val channels: List<ChannelType>
)