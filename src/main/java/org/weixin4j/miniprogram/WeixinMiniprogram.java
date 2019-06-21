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

import java.util.HashMap;
import java.util.Map;

import org.weixin4j.miniprogram.component.AbstractComponent;
import org.weixin4j.miniprogram.component.BaseComponent;
import org.weixin4j.miniprogram.component.SnsComponent;
import org.weixin4j.miniprogram.component.WxacodeComponent;
import org.weixin4j.miniprogram.loader.DefaultTokenLoader;
import org.weixin4j.miniprogram.loader.ITokenLoader;
import org.weixin4j.miniprogram.model.base.Token;

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
     * 基础组件
     */
    private final BaseComponent baseComponent = new BaseComponent(this);

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
                    token = base().token();
                    tokenLoader.refresh(token);
                }
            }
        }
        return token;
    }

    /**
     * 获取基础组件
     *
     * @return 基础组件
     * @since 1.0.0
     */
    public BaseComponent base() {
        String key = BaseComponent.class.getName();
        if (components.containsKey(key)) {
            return (BaseComponent) components.get(key);
        }
        BaseComponent component = new BaseComponent(this);
        components.put(key, component);
        return component;
    }

    /**
     * 获取用户信息组件
     *
     * @return 用户信息组件
     * @since 1.0.0
     */
    public SnsComponent sns() {
        String key = SnsComponent.class.getName();
        if (components.containsKey(key)) {
            return (SnsComponent) components.get(key);
        }
        SnsComponent component = new SnsComponent(this);
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
}
