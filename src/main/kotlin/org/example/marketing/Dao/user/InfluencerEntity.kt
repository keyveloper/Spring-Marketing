package org.example.marketing.dao.user

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.InfluencersTable
import org.jetbrains.exposed.dao.id.EntityID


class InfluencerEntity(id: EntityID<Long>) : BaseDateEntity(id, InfluencersTable) {
    companion object : BaseDateEntityClass<InfluencerEntity>(InfluencersTable)

    var loginId by InfluencersTable.loginId
    var password by InfluencersTable.password
    var liveStatus by InfluencersTable.liveStatus
    var birthday by InfluencersTable.birthday
    var blogUrl by InfluencersTable.blogUrl
    var instagramUrl by InfluencersTable.instagramUrl
    var threadUrl by InfluencersTable.threadUrl
    var youtuberUrl by InfluencersTable.youtuberUrl
}