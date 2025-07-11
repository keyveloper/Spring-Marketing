package org.example.marketing.dao.user

import org.example.marketing.dao.BaseDateUUIDEntity
import org.example.marketing.dao.BaseDateUUIDEntityClass
import org.example.marketing.table.UserProfileDraftsTable
import org.jetbrains.exposed.dao.id.EntityID
import java.util.UUID

class UserProfileDraftEntity(id: EntityID<UUID>) : BaseDateUUIDEntity(id, UserProfileDraftsTable) {
    companion object : BaseDateUUIDEntityClass<UserProfileDraftEntity>(UserProfileDraftsTable)

    var userId by UserProfileDraftsTable.userId
    var userType by UserProfileDraftsTable.userType
    var draftStatus by UserProfileDraftsTable.draftStatus
    var expiredAt by UserProfileDraftsTable.expiredAt
}