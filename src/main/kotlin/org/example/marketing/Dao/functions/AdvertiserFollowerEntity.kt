package org.example.marketing.dao.functions

import org.example.marketing.dao.BaseDateEntity
import org.example.marketing.dao.BaseDateEntityClass
import org.example.marketing.table.AdvertiserFollowersTable
import org.jetbrains.exposed.dao.id.EntityID

class AdvertiserFollowerEntity(id: EntityID<Long>) : BaseDateEntity(id, AdvertiserFollowersTable) {
    companion object : BaseDateEntityClass<AdvertiserFollowerEntity>(AdvertiserFollowersTable)

    var advertiserId by AdvertiserFollowersTable.advertiserId
    var influencerId by AdvertiserFollowersTable.influencerId
    var followStatus by AdvertiserFollowersTable.followStatus
}