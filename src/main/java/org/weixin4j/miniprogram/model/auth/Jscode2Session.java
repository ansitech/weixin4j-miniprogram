package org.weixin4j.miniprogram.model.auth;

import com.alibaba.fastjson.JSONObject;
import org.weixin4j.miniprogram.WeixinException;

/**
 * 小程序会话信息
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class Jscode2Session {

    private String openid;
    private String session_key;
    private String unionid;

    /**
     * 通过微信小程序平台返回JSON对象创建凭证对象
     *
     * @param jsonObj JSON数据包
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     */
    public Jscode2Session(JSONObject jsonObj) throws WeixinException {
        this.openid = jsonObj.getString("openid");
        this.session_key = jsonObj.getString("session_key");
        this.unionid = jsonObj.getString("unionid");
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
