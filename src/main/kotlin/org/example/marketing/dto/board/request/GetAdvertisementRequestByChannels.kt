package org.example.marketing.dto.board.request

import org.example.marketing.enums.ChannelType

data class GetAdvertisementRequestByChannels(
    val channels: List<ChannelType>
)