package me.alex.demo


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
data class BaseResponse<T>(
    val data: T,
    val message: String,
    val status: Int,
    val success: Boolean
)

data class Reg(
    val clientId: String,
    val deptName: String,
    val hospitalName: String,
    val timeDivision: Int,
    val type1: String,
    val type2: String
)