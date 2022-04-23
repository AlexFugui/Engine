package me.alex.demo.bean


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
    val errorMsg: String,
    val errorCode: Int,
    val success: Boolean
)

