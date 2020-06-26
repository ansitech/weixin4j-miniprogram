package org.weixin4j.miniprogram.model.payment;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author yangqisheng
 * @since 1.0.3
 */
public class RequestPayment {
    /**
     * 时间戳从1970年1月1日00:00:00至今的秒数,即当前的时间
     */
    private String timeStamp;
    /**
     * 随机字符串，长度为32个字符以下。
     */
    private String nonceStr;
    /**
     * 统一下单接口返回的 prepay_id 参数值，提交格式如：prepay_id=*
     */
    @JSONField(name = "package")
    private String packages;
    /**
     * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。注意此处需与统一下单的签名类型一致
     */
    private String signType;
    /**
     * 支付签名
     */
    private String paySign;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }
}
