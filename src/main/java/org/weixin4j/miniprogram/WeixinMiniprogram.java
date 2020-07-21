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
package org.weixin4j.miniprogram;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.weixin4j.miniprogram.component.*;
import org.weixin4j.miniprogram.http.HttpsClient;
import org.weixin4j.miniprogram.http.Response;
import org.weixin4j.miniprogram.loader.DefaultTokenLoader;
import org.weixin4j.miniprogram.loader.ITokenLoader;
import org.weixin4j.miniprogram.model.auth.Token;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序基础支持对象
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class WeixinMiniprogram extends WeixinSupport implements java.io.Serializable {

    /**
     * 同步锁
     */
    private final static byte[] LOCK = new byte[0];
    /**
     * 小程序唯一凭证，即 AppID
     */
    private final String appId;
    /**
     * 小程序唯一凭证密钥，即 AppSecret
     */
    private final String secret;
    /**
     * AccessToken加载器
     */
    protected ITokenLoader tokenLoader = new DefaultTokenLoader();
    /**
     * 新增组件
     */
    private final Map<String, AbstractComponent> components = new HashMap<String, AbstractComponent>();

    /**
     * 小程序，单一小程序
     */
    public WeixinMiniprogram() {
        this(Configuration.getAppId(), Configuration.getSecret());
    }

    /**
     * 小程序，指定小程序
     *
     * @param appId 小程序开发者AppId
     * @param secret 小程序开发者秘钥
     */
    public WeixinMiniprogram(String appId, String secret) {
        this.appId = appId;
        this.secret = secret;
    }

    public String getAppId() {
        return appId;
    }

    public String getSecret() {
        return secret;
    }

    /**
     * 获取access_token（每次都获取新的，请缓存下来，2小时过期）
     *
     * @return 获取的AccessToken对象
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     */
    private Token token() throws WeixinException {
        if (StringUtils.isEmpty(appId)) {
            throw new IllegalArgumentException("appid can't be null or empty");
        }
        if (StringUtils.isEmpty(secret)) {
            throw new IllegalArgumentException("secret can't be null or empty");
        }
        //拼接参数
        String param = "?grant_type=client_credential&appid=" + appId + "&secret=" + secret;
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
        //update at 1.0.3 因为 fastjson 1.2.68 直接转换JSON不走set方法
        return new Token(jsonObj);
    }

    /**
     * 获取Token对象
     *
     * @return Token对象
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 0.0.1
     */
    public Token getToken() throws WeixinException {
        Token token = tokenLoader.get();
        if (token == null) {
            synchronized (LOCK) {
                token = tokenLoader.get();
                if (token == null) {
                    token = token();
                    tokenLoader.refresh(token);
                }
            }
        }
        return token;
    }

    /**
     * 获取用户信息组件
     *
     * @return 用户信息组件
     * @since 1.0.2
     */
    public AuthComponent auth() {
        String key = AuthComponent.class.getName();
        if (components.containsKey(key)) {
            return (AuthComponent) components.get(key);
        }
        AuthComponent component = new AuthComponent(this);
        components.put(key, component);
        return component;
    }

    /**
     * 获取小程序码组件
     *
     * @return 小程序码组件
     * @since 1.0.0
     */
    public WxacodeComponent wxacode() {
        String key = WxacodeComponent.class.getName();
        if (components.containsKey(key)) {
            return (WxacodeComponent) components.get(key);
        }
        WxacodeComponent component = new WxacodeComponent(this);
        components.put(key, component);
        return component;
    }


    /**
     * 获取模板消息组件
     *
     * @return 模板消息组件
     * @since 1.0.2
     */
    public TemplateMessageComponent templateMessage() {
        String key = TemplateMessageComponent.class.getName();
        if (components.containsKey(key)) {
            return (TemplateMessageComponent) components.get(key);
        }
        TemplateMessageComponent component = new TemplateMessageComponent(this);
        components.put(key, component);
        return component;
    }

    /**
     * 获取直播间组件
     *
     * @return 直播间组件
     * @since 1.0.3
     */
    public LiveRoomComponent liveRoomComponent() {
        String key = LiveRoomComponent.class.getName();
        if (components.containsKey(key)) {
            return (LiveRoomComponent) components.get(key);
        }
        LiveRoomComponent component = new LiveRoomComponent(this);
        components.put(key, component);
        return component;
    }

    /**
     * 获取商品管理组件
     *
     * @return 直播间组件
     * @since 1.0.3
     */
    public LiveGoodsComponent liveGoodsComponent() {
        String key = LiveRoomComponent.class.getName();
        if (components.containsKey(key)) {
            return (LiveGoodsComponent) components.get(key);
        }
        LiveGoodsComponent component = new LiveGoodsComponent(this);
        components.put(key, component);
        return component;
    }
}
