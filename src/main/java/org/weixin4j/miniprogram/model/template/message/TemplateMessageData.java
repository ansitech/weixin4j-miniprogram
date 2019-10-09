package org.weixin4j.miniprogram.model.template.message;

/**
 * 模板消息内容
 *
 * @author yangqisheng
 * @since 1.0.2
 */
public class TemplateMessageData {

    /**
     * 字段Key
     */
    private String key;

    /**
     * 值
     */
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
