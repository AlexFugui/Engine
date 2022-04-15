package me.alex.engine.interceptor


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
object InterceptLog {

    var enabled = false

    fun logRequest(
        generateId: String,
        toString: String,
        method: String,
        toMultimap: Map<String, List<String>>,
        requestString: String
    ) {
        TODO("Not yet implemented")
    }



}