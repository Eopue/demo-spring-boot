package cn.com.demo.utils.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The type JsonUtil.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/9/25
 */
public class JsonUtil {

    /**
     * ObjectMapper
     */
    private static ObjectMapper MAPPER;

    static {
        MAPPER = generateMapper(JsonInclude.Include.ALWAYS);
    }

    private JsonUtil() {
    }

    /**
     * 将json通过类型转换成对象 <p/>
     * <pre>
     *     {@link JsonUtil JsonUtil}.fromJson("{\"username\":\"username\",
     * \"password\":\"password\"}", User.class);
     * </pre>
     *
     * @param json  json字符串
     * @param clazz 泛型类型
     * @return 返回对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return clazz.equals(String.class) ? (T) json : MAPPER.readValue(json, clazz);
        } catch (IOException ex) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    /**
     * 复杂对象转化
     */
    public static <T> T fromJson(String json, Class<T> parametrized, Class<?> parametersFor,
                                 Class... parameterClasses) {
        try {
            JavaType javaType = MAPPER.getTypeFactory()
                    .constructParametrizedType(parametrized, parametersFor, parameterClasses);
            return (T) MAPPER.readValue(json, javaType);
        } catch (IOException ex) {
            throw new RuntimeException("JSON格式转换错误。");
        }

    }

    /**
     * 将json通过类型转换成对象 <p/>
     * <pre>
     *     {@link JsonUtil JsonUtil}.fromJson("[{\"username\":\"username\",
     * \"password\":\"password\"}, {\"username\":\"username\", \"password\":\"password\"}]", new
     * TypeReference&lt;List&lt;User&gt;&gt;);
     * </pre>
     *
     * @param json          json字符串
     * @param typeReference 引用类型
     * @return 返回对象
     */
    public static <T> T fromJson(String json, TypeReference<?> typeReference) {
        try {
            return (T) (typeReference.getType().equals(String.class) ? json : MAPPER.readValue(json, typeReference));
        } catch (IOException ex) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    /**
     * 将对象转换成json <p/>
     * <pre>
     *     {@link JsonUtil JsonUtil}.toJson(user);
     * </pre>
     *
     * @param src 对象
     * @return 返回json字符串
     */
    public static <T> String toJson(T src) {
        String json = null;
        try {
            json = src instanceof String ? (String) src : MAPPER.writeValueAsString(src);
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
        return json;
    }

    /**
     * 将对象转换成格式化的json <p/>
     * <pre>
     *     {@link JsonUtil JsonUtil}.toJson(user);
     * </pre>
     *
     * @param src 对象
     * @return 返回json字符串
     */
    public static <T> String toPrettyJson(T src) {
        String json = null;
        try {
            json = src instanceof String ? (String) src : MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(src);
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
        return json;
    }

    /**
     * 将对象转换成json, 可以设置输出属性 <p/>
     * <pre>
     *     {@link JsonUtil JsonUtil}.toJson(user, {@link JsonInclude.Include Inclusion.ALWAYS});
     * </pre>
     * <p/> {@link JsonInclude.Include Inclusion 对象枚举} <ul> <li>{@link JsonInclude.Include Inclusion.ALWAYS 全部列入}</li>
     * <li>{@link JsonInclude.Include Inclusion.NON_DEFAULT 字段和对象默认值相同的时候不会列入}</li> <li>{@link JsonInclude.Include
     * Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}*</li> <li>{@link JsonInclude.Include Inclusion.NON_NULL
     * 字段为NULL时候不会列入}*</li> </ul>
     *
     * @param <T>       the type parameter
     * @param src       对象
     * @param inclusion 传入一个枚举值, 设置输出属性
     * @return 返回json字符串 string
     */
    public static <T> String toJson(T src, JsonInclude.Include inclusion) {
        try {
            if (src instanceof String) {
                return (String) src;
            } else {
                ObjectMapper customMapper = generateMapper(inclusion);
                return customMapper.writeValueAsString(src);
            }
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    /**
     * 通过Inclusion创建ObjectMapper对象 <p/> {@link JsonInclude.Include  Inclusion 对象枚举} <ul> <li>{@link JsonInclude.Include
     * Inclusion.ALWAYS 全部列入}*</li> <li>{@link JsonInclude.Include  Inclusion.NON_DEFAULT
     * 字段和对象默认值相同的时候不会列入}*</li> <li>{@link JsonInclude.Include  Inclusion.NON_EMPTY 字段为NULL或者""的时候不会列入}</li>
     * <li>{@link JsonInclude.Include  Inclusion.NON_NULL 字段为NULL时候不会列入}</li> </ul>
     *
     * @param inclusion 传入一个枚举值, 设置输出属性
     * @return 返回ObjectMapper对象 object mapper
     */
    public static ObjectMapper generateMapper(JsonInclude.Include inclusion) {

        ObjectMapper customMapper = new ObjectMapper();

        // 设置输出时包含属性的风格
        customMapper.setSerializationInclusion(inclusion);

        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        customMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 禁止使用int代表Enum的order()來反序列化Enum,非常危險
        customMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true);

        // 所有日期格式都统一为以下样式
        customMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        return customMapper;
    }

    /**
     * 将对象转换成json, 传入置对象 <p/>
     * <pre>
     *     {@link ObjectMapper ObjectMapper} mapper = new ObjectMapper();
     *     mapper.setSerializationInclusion({@link JsonInclude.Include Inclusion.ALWAYS});
     *     mapper.configure({@link JsonInclude.Include Feature.FAIL_ON_UNKNOWN_PROPERTIES}, false);
     *     mapper.configure({@link JsonInclude.Include Feature.FAIL_ON_NUMBERS_FOR_ENUMS}, true);
     *     mapper.setDateFormat(new {@link SimpleDateFormat SimpleDateFormat}("yyyy-MM-dd
     * HH:mm:ss"));
     *     {@link JsonUtil JsonUtil}.toJson(user, mapper);
     * </pre>
     * <p/> {@link ObjectMapper ObjectMapper}
     *
     * @param src    对象
     * @param mapper 配置对象
     * @return 返回json字符串
     * @see ObjectMapper
     */
    public static <T> String toJson(T src, ObjectMapper mapper) {
        try {
            if (null != mapper) {
                if (src instanceof String) {
                    return (String) src;
                } else {
                    return mapper.writeValueAsString(src);
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    /**
     * 将json转换成list<Object>, json字符串和你要转换的javabean
     *
     * @param json           对象
     * @param elementClasses 配置对象
     * @return list<Object>
     * @see ObjectMapper
     */
    public static List<?> toListObject(String json, Class<?>... elementClasses) {
        List<Class<?>> list = null;
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(ArrayList.class, elementClasses);
            list = MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
        return list;
    }

    public static <T> T fromJson(String json, TypeReference<?> typeReference, JsonInclude.Include inclusion) {
        try {
            return (T) (typeReference.getType().equals(String.class) ? json : generateMapper(inclusion)
                    .readValue(json, typeReference));
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    public static <T> T fromJson(FileReader fileReader, Class<T> clazz) {
        try {
            return MAPPER.readValue(fileReader, clazz);
        } catch (IOException e) {
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    /**
     * 将json通过类型转换成List<p/>
     * <pre>
     *
     * @param inputStream    文件读入流
     * @param typeReference 引用类型
     * @return 返回对象
     */
    public static <T> T fromJson(InputStream inputStream, TypeReference<?> typeReference) {
        try {
            return (T) (typeReference.getType().equals(String.class) ? MAPPER.readValue(inputStream, String.class) : MAPPER
                    .readValue(inputStream, typeReference));
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("JSON格式转换错误。");
        }
    }

    public static JsonNode fromJson(String json) {
        if (json == null || json.length() == 0) {
            return null;
        }
        try {
            return mapper().getJsonFactory().createJsonParser(json).readValueAsTree();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 返回{@link ObjectMapper ObjectMapper}对象, 用于定制性的操作
     *
     * @return {@link ObjectMapper ObjectMapper}对象
     */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

}