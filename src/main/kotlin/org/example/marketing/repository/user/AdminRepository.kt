package org.example.marketing.repository.user

import org.example.marketing.dao.user.AdminEntity
import org.example.marketing.dto.user.request.SaveAdmin
import org.example.marketing.dto.user.request.UpdateAdmin
import org.example.marketing.exception.DuplicatedAdminException
import org.example.marketing.exception.NotFoundAdminException
import org.example.marketing.table.AdminsTable
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.springframework.stereotype.Component
import java.sql.SQLException

@Component
class AdminRepository {

    fun save(admin: SaveAdmin): Long {
        return try {
            val adminEntity = AdminEntity.new {
                loginId = admin.loginId
                password = admin.convertedPassword
            }
            adminEntity.id.value
        } catch (e: ExposedSQLException) {
            val sqlEx = e.cause as? SQLException
            if (sqlEx != null) {
                when {
                    // MySQL: errorCode 1062는 duplicate entry 오류
                    sqlEx.errorCode == 1062 ->
                        throw DuplicatedAdminException(
                            logics = "admin-insert",
                            duplicatedLoginId = admin.loginId
                        )
                    else -> throw e
                }
            } else {
                throw e
            }
        }
    }

    fun findById(targetId: Long): AdminEntity {
        val admin = AdminEntity.findById(targetId)
            ?:throw NotFoundAdminException(
                logics = "admin-get",
                targetId = targetId
            )
        return admin
    }

    fun findAll(): List<AdminEntity> {
        return AdminEntity.all().toList()
    }

    fun update(updateDto: UpdateAdmin): AdminEntity {
        val admin = AdminEntity.findById(updateDto.targetId)
            ?: throw NotFoundAdminException(
                logics = "admin-update",
                targetId = updateDto.targetId
            )

        try {
            updateDto.newLoginId?.let { admin.loginId = it }
            updateDto.nwePassword?.let { admin.password = it }
        } catch (e: ExposedSQLException) {
            val sqlEx = e.cause as? SQLException
            if (sqlEx != null) {
                when {
                    // MySQL의 경우: errorCode 1062는 duplicate entry 오류
                    sqlEx.errorCode == 1062 ->
                        throw DuplicatedAdminException(
                            logics = "admin-update",
                            duplicatedLoginId = updateDto.newLoginId!!
                        )
                    else -> throw e
                }
            } else throw e
        }
        return admin
    }

    fun deleteById(targetId: Long): Int {
        val deletedRows = AdminsTable.deleteWhere { AdminsTable.loginId eq loginId }
        if (deletedRows == 0) {
            throw NotFoundAdminException(
                logics = "admin-delete",
                targetId = targetId // 또는 loginId를 이용해 식별할 수 있는 값
            )
        }
        return deletedRows
    }
}