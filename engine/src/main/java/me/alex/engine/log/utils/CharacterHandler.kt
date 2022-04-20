package me.alex.engine.log.utils

import android.text.InputFilter
import android.text.Spanned
import android.text.TextUtils
import okhttp3.internal.and
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.StringReader
import java.io.StringWriter
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.xml.transform.Source
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * ================================================
 * <p>
 * Created by Alex on 2021/12/6
 * <p>
 * Description:
 * <p>
 * ================================================
 */
object CharacterHandler {
    val EMOJI_FILTER: InputFilter = object : InputFilter {
        //emoji过滤器
        var emoji: Pattern = Pattern.compile(
            "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
            Pattern.UNICODE_CASE or Pattern.CASE_INSENSITIVE
        )

        override fun filter(
            source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int,
            dend: Int
        ): CharSequence? {
            val emojiMatcher: Matcher = emoji.matcher(source)
            return if (emojiMatcher.find()) {
                ""
            } else null
        }
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    fun str2HexStr(str: String): String {
        val chars = "0123456789ABCDEF".toCharArray()
        val sb = StringBuilder()
        val bs = str.toByteArray()
        var bit: Int
        for (b in bs) {
            bit = b and 0x0f0 shr 4
            sb.append(chars[bit])
            bit = b and 0x0f
            sb.append(chars[bit])
        }
        return sb.toString().trim { it <= ' ' }
    }

    /**
     * json 格式化
     *
     * @param json
     * @return
     */
    fun jsonFormat(json: String): String {
        if (TextUtils.isEmpty(json)) {
            return "Empty/Null json content"
        }
        var message = ""
        try {
            val json = json.trim { it <= ' ' }
            message = when {
                json.startsWith("{") -> {
                    val jsonObject = JSONObject(json)
                    jsonObject.toString(4)
                }
                json.startsWith("[") -> {
                    val jsonArray = JSONArray(json)
                    jsonArray.toString(4)
                }
                else -> {
                    json
                }
            }
        } catch (e: JSONException) {
            message = json
        } catch (error: OutOfMemoryError) {
            message = "Output omitted because of Object size"
        }
        return message
    }

    /**
     * xml 格式化
     *
     * @param xml
     * @return
     */
    fun xmlFormat(xml: String?): String? {
        if (TextUtils.isEmpty(xml)) {
            return "Empty/Null xml content"
        }
        val message: String? = try {
            val xmlInput: Source = StreamSource(StringReader(xml))
            val xmlOutput = StreamResult(StringWriter())
            val transformer: Transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)
            xmlOutput.writer.toString().replaceFirst(">", ">\n")
        } catch (e: TransformerException) {
            xml
        }
        return message
    }

}