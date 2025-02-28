package org.example.marketing.dto.user.request

data class UpdateAdmin(
    val targetId: Long,
    val newLoginId: String?,
    val nwePassword: String?
) {
    companion object{
        fun of(
            request: UpdateAdminRequest
        ): UpdateAdmin {
            return UpdateAdmin(
                request.targetId,
                request.newLoginId,
                request.newPassword
            )
        }
    }
}