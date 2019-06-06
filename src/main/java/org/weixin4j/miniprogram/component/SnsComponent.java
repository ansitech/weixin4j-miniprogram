package org.weixin4j.miniprogram.component;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.weixin4j.miniprogram.Configuration;
import org.weixin4j.miniprogram.WeixinMiniprogram;
import org.weixin4j.miniprogram.WeixinException;
import org.weixin4j.miniprogram.http.HttpsClient;
import org.weixin4j.miniprogram.http.Response;
import org.weixin4j.miniprogram.model.sns.Jscode2Session;
import org.weixin4j.miniprogram.model.sns.UserInfo;
import org.weixin4j.miniprogram.util.AES;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

/**
 * 小程序交互组件
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class SnsComponent extends AbstractComponent {

    /**
     * 小程序交互组件
     *
     * @param miniprogram 小程序对象
     */
    public SnsComponent(WeixinMiniprogram miniprogram) {
        super(miniprogram);
    }

    /**
     * 获取小程序用户Session
     *
     * @param js_code 登录时获取的 js_code
     * @return 小程序用户身份
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 1.0.0
     */
    public Jscode2Session getJscode2Session(String js_code) throws WeixinException {
        if (StringUtils.isEmpty(js_code)) {
            throw new IllegalArgumentException("js_code can't be null or empty");
        }
        //拼接参数
        String param = "?appid=" + miniprogram.getAppId() + "&secret=" + miniprogram.getSecret() + "&js_code=" + js_code + "&grant_type=authorization_code";
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //调用获取access_token接口
        Response res = http.get("https://api.weixin.qq.com/sns/jscode2session" + param);
        //根据请求结果判定，是否验证成功
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return null;
        }
        if (Configuration.isDebug()) {
            System.out.println("getJscode2Session返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getCause(jsonObj.getIntValue("errcode")));
        }
        return new Jscode2Session(jsonObj);
    }

    /**
     * 获取用户信息
     *
     * @param sessionKey 用户wx.login获得的sessionKey
     * @param encryptedData 完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @return 小程序用户信息
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     */
    public UserInfo getUserInfo(String sessionKey, String encryptedData, String iv) throws WeixinException {
        UserInfo userInfo = null;
        try {
            AES aes = new AES();
            //进行数据解密
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if (null != resultByte && resultByte.length > 0) {
                //解密json格式userInfo
                String userInfoJson = new String(resultByte, "UTF-8");
                if (Configuration.isDebug()) {
                    System.out.println("getUserInfo() :" + userInfoJson);
                }
                userInfo = JSONObject.parseObject(userInfoJson, UserInfo.class);
            }
            return userInfo;
        } catch (InvalidAlgorithmParameterException e) {
            throw new WeixinException("数据解密不合法", e);
        } catch (UnsupportedEncodingException e) {
            throw new WeixinException("数据解密不合法", e);
        }
    }
}
