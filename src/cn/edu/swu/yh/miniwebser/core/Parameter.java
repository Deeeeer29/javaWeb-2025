package cn.edu.swu.yh.miniwebser.core;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP参数处理类
 */
public class Parameter {
    private Map<String, String> parameters;
    
    public Parameter() {
        this.parameters = new HashMap<>();
    }
    
    public Parameter(Map<String, String> parameters) {
        this.parameters = parameters != null ? parameters : new HashMap<>();
    }
    
    /**
     * 添加参数
     */
    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }
    
    /**
     * 获取参数值
     */
    public String getParameter(String key) {
        return parameters.get(key);
    }
    
    /**
     * 获取参数值，如果不存在则返回默认值
     */
    public String getParameter(String key, String defaultValue) {
        return parameters.getOrDefault(key, defaultValue);
    }
    
    /**
     * 检查参数是否存在
     */
    public boolean hasParameter(String key) {
        return parameters.containsKey(key);
    }
    
    /**
     * 获取所有参数
     */
    public Map<String, String> getAllParameters() {
        return new HashMap<>(parameters);
    }
    
    /**
     * 解析查询字符串
     */
    public static Parameter parseQueryString(String queryString) {
        Parameter param = new Parameter();
        if (queryString == null || queryString.trim().isEmpty()) {
            return param;
        }
        
        String[] pairs = queryString.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                param.addParameter(keyValue[0], keyValue[1]);
            } else if (keyValue.length == 1) {
                param.addParameter(keyValue[0], "");
            }
        }
        
        return param;
    }
    
    /**
     * 解析POST数据
     */
    public static Parameter parsePostData(String postData) {
        return parseQueryString(postData);
    }
}
