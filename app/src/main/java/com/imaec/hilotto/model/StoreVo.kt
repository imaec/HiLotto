package com.imaec.hilotto.model

import com.imaec.domain.model.StoreDto
import java.io.Serializable

data class StoreVo(
    val storeName: String = "",
    val auto: String = "",
    val address: String = ""
) : Serializable {
    companion object {
        fun dtoToVo(dto: StoreDto) = StoreVo(
            storeName = dto.storeName,
            auto = dto.auto,
            address = dto.address
        )
    }
}
