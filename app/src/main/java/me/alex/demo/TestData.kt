package me.alex.demo

import org.json.JSONObject


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
data class TestData(val name: String, val age: Int, val address: String) {
    override fun toString(): String {
        return "TestData : " +
                JSONObject().apply {
                    put("name", name)
                    put("age", age)
                    put("address", address)
                }.toString()
    }
}