/*
 * 微信小程序API(JAVA) SDK
 *
 * Copyright (c) 2014, Ansitech Network Technology Co.,Ltd All rights reserved.
 * 
 * http://www.weixin4j.org/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.weixin4j.miniprogram.component;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.weixin4j.miniprogram.Configuration;
import org.weixin4j.miniprogram.WeixinMiniprogram;
import org.weixin4j.miniprogram.WeixinException;
import org.weixin4j.miniprogram.WeixinSupport;
import org.weixin4j.miniprogram.http.HttpsClient;
import org.weixin4j.miniprogram.http.Response;
import org.weixin4j.miniprogram.model.base.Token;

/**
 * 基础组件
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class BaseComponent extends WeixinSupport {

    protected WeixinMiniprogram miniprogram;

    public BaseComponent(WeixinMiniprogram weixin) {
        if (miniprogram == null) {
            throw new IllegalArgumentException("miniprogram can not be null");
        }
        this.miniprogram = weixin;
    }

    /**
     * 获取access_token（每次都获取新的，请缓存下来，2小时过期）
     *
     * @return 获取的AccessToken对象
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     */
    public Token token() throws WeixinException {
        if (StringUtils.isEmpty(miniprogram.getAppId())) {
            throw new IllegalArgumentException("appid can't be null or empty");
        }
        if (StringUtils.isEmpty(miniprogram.getSecret())) {
            throw new IllegalArgumentException("secret can't be null or empty");
        }
        //拼接参数
        String param = "?grant_type=client_credential&appid=" + miniprogram.getAppId() + "&secret=" + miniprogram.getSecret();
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //调用获取access_token接口
        Response res = http.get("https://api.weixin.qq.com/cgi-bin/token" + param);
        //根据请求结果判定，是否验证成功
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            throw new WeixinException(getCause(-1));
        }
        if (Configuration.isDebug()) {
            System.out.println("getAccessToken返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null) {
            //返回异常信息
            throw new WeixinException(getCause(jsonObj.getIntValue("errcode")));
        }
        //设置凭证，设置accessToken和过期时间
        return (Token) JSONObject.toJavaObject(jsonObj, Token.class);
    }
}
