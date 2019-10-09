package org.weixin4j.miniprogram.component;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.weixin4j.miniprogram.Configuration;
import org.weixin4j.miniprogram.WeixinException;
import org.weixin4j.miniprogram.WeixinMiniprogram;
import org.weixin4j.miniprogram.http.HttpsClient;
import org.weixin4j.miniprogram.http.Response;
import org.weixin4j.miniprogram.model.auth.Token;
import org.weixin4j.miniprogram.model.auth.Jscode2Session;
import org.weixin4j.miniprogram.model.auth.PhoneNumber;
import org.weixin4j.miniprogram.model.auth.UserInfo;
import org.weixin4j.miniprogram.util.AES;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;

/**
 * 授权相关接口
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class AuthComponent extends AbstractComponent {

    /**
     * 小程序交互组件
     *
     * @param miniprogram 小程序对象
     */
    public AuthComponent(WeixinMiniprogram miniprogram) {
        super(miniprogram);
    }

    /**
     * 获取小程序全局唯一后台接口调用凭据（access_token）
     *
     * @return 获取的AccessToken对象
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 1.0.2
     */
    public Token getAccessToken() throws WeixinException {
        return miniprogram.getToken();
    }

    /**
     * 登录凭证校验
     *
     * @param js_code 登录时获取的 js_code
     * @return 小程序用户身份
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 1.0.0
     */
    public Jscode2Session code2Session(String js_code) throws WeixinException {
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
     * @since 1.0.0
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

    /**
     * 获取手机号
     *
     * @param sessionKey 用户wx.login获得的sessionKey
     * @param encryptedData 包括敏感数据在内的完整用户信息的加密数据
     * @param iv 加密算法的初始向量
     * @return 微信用户绑定的手机号
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 1.0.2
     */
    public PhoneNumber getPhoneNumber(String sessionKey, String encryptedData, String iv) throws WeixinException {
        PhoneNumber phoneNumber = null;
        try {
            AES aes = new AES();
            //进行数据解密
            byte[] resultByte = aes.decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
            if (null != resultByte && resultByte.length > 0) {
                //解密json格式userInfo
                String json = new String(resultByte, "UTF-8");
                if (Configuration.isDebug()) {
                    System.out.println("getPhoneNumber() :" + json);
                }
                phoneNumber = JSONObject.parseObject(json, PhoneNumber.class);
            }
            return phoneNumber;
        } catch (InvalidAlgorithmParameterException e) {
            throw new WeixinException("数据解密不合法", e);
        } catch (UnsupportedEncodingException e) {
            throw new WeixinException("数据解密不合法", e);
        }
    }

    /**
     * 用户支付完成后，获取该用户的 UnionId，无需用户授权。
     *
     * 注意：调用前需要用户完成支付，且在支付后的五分钟内有效。
     *
     * @param openid 用户小程序openid
     * @return 用户唯一标识
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 1.0.2
     */
    public String getPaidUnionId(String openid) throws WeixinException {
        return getPaidUnionId(openid, null);
    }

    /**
     * 用户支付完成后，获取该用户的 UnionId，无需用户授权。
     *
     * 注意：调用前需要用户完成支付，且在支付后的五分钟内有效。
     *
     * @param openid 用户小程序openid
     * @param transactionId 微信支付订单号
     * @return 用户唯一标识
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 1.0.2
     */
    public String getPaidUnionId(String openid, String transactionId) throws WeixinException {
        return getPaidUnionId(openid, transactionId, null, null);
    }

    /**
     * 用户支付完成后，获取该用户的 UnionId，无需用户授权。
     *
     * 注意：调用前需要用户完成支付，且在支付后的五分钟内有效。
     *
     * @param openid 用户小程序openid
     * @param transactionId 微信支付订单号
     * @param mchId 微信支付分配的商户号
     * @param outTradeNo 微信支付商户订单号
     * @return 用户唯一标识
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 1.0.2
     */
    public String getPaidUnionId(String openid, String transactionId, String mchId, String outTradeNo) throws WeixinException {
        if (openid == null || "".equals(openid)) {
            throw new IllegalStateException("openid can not be null or empty");
        }
        String param = "&openid=" + openid;
        if (transactionId != null && !"".equals(transactionId)) {
            param += "&transaction_id=" + transactionId;
        }
        if (mchId != null && !"".equals(mchId) && outTradeNo != null && !"".equals(outTradeNo)) {
            param += "&mch_id=" + mchId;
            param += "&out_trade_no=" + outTradeNo;
        }
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //调用创建Tick的access_token接口
        Response res = http.get("https://api.weixin.qq.com/wxa/getpaidunionid?access_token" + miniprogram.getToken().getAccess_token() + param);
        //根据请求结果判定，是否验证成功
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return null;
        }
        if (Configuration.isDebug()) {
            System.out.println("getPaidUnionId返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getCause(jsonObj.getIntValue("errcode")));
        }
        return jsonObj.getString("unionid");
    }
}
