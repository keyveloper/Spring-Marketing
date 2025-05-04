package org.example.marketing.repository.board

import org.example.marketing.dao.board.AdvertisementDraftEntity
import org.example.marketing.table.AdvertisementDraftsTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class AdvertisementImageDslRepository {
    fun setImageAdvertisementIdByDraftId(targetDraftId: Long) {
        transaction {
            exec(
                """
                 UPDATE  advertisement_images ai
                 JOIN    advertisement_drafts ad ON ai.draft_id = ad.id
                 JOIN    advertisements       a  ON a.draft_id  = ad.id
                 SET     ai.advertisement_id     = a.id
                 """.trimIndent()
            )
        }
    }

    fun deleteDraftAndImagesByDraftId(targetDraftId: Long, advertiserId: Long) {
        transaction {
            exec(
                """
                    DELETE d, i 
                    FROM advertisement_drafts d
                    JOIN advertisement_images i ON d.id = i.draft_id
                    WHERE d.id = $targetDraftId AND d.draft_status = 'DRAFT' AND d.advertisement_id = $advertiserId
                   
                """.trimIndent()
            )
        }
    }

    // delete image, draft
    fun deleteAdvertisement(targetAdvertisementId: Long) {
        transaction {
            exec(
                """
                    DELETE a, d, i 
                    FROM advertisements a 
                    JOIN advertisement_drafts d ON a.draft_id = d.id
                    JOIN advertisement_images i ON a.id = i.advertisement_id
                    WHERE a.id = $targetAdvertisementId AND a.status = 'LIVE' 
                """.trimIndent()
            )
        }
    }

    // 👉 나중에 만들기 - cron ???
    fun purgeExpireDrafts() {
        val expiredIds = AdvertisementDraftEntity.find {
            AdvertisementDraftsTable.expiredAt lessEq Instant.now().epochSecond
        }.map { it.id}
    }
}