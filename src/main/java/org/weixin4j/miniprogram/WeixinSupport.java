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

/**
 * 微信平台支持
 *
 * <p>
 * 通过<tt>Weixin</tt>产生一个请求对象，对应生成一个<tt>HttpClient</tt>，
 * 每次登陆产生一个<tt>OAuth</tt>用户连接,使用<tt>OAuthToken</tt>
 * 可以不用重复向微信平台发送登陆请求，在没有过期时间内，可继续请求。</p>
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class WeixinSupport {

    /**
     * 全局返回码说明
     */
    private final static Map<Integer, String> RETURN_CODE_MAP = new HashMap<Integer, String>();

    static {
        RETURN_CODE_MAP.put(-1, "系统繁忙，此时请开发者稍候再试");
        RETURN_CODE_MAP.put(0, "请求成功");
        RETURN_CODE_MAP.put(40029, "code 无效");
        RETURN_CODE_MAP.put(45011, "频率限制，每个用户每分钟100次");
        RETURN_CODE_MAP.put(40037, "template_id不正确");
        RETURN_CODE_MAP.put(41028, "form_id不正确，或者过期");
        RETURN_CODE_MAP.put(41029, "form_id已被使用");
        RETURN_CODE_MAP.put(41030, "page不正确");
        RETURN_CODE_MAP.put(45009, "接口调用超过限额（目前默认每个帐号日调用限额为100万）");
        RETURN_CODE_MAP.put(40003, "openid 错误");
        RETURN_CODE_MAP.put(89002, "没有绑定开放平台帐号");
        RETURN_CODE_MAP.put(89300, "订单无效");
        RETURN_CODE_MAP.put(40001, "AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性");
        RETURN_CODE_MAP.put(40002, "请确保 grant_type 字段值为 client_credential");
        RETURN_CODE_MAP.put(40013, "不合法的 AppID，请开发者检查 AppID 的正确性，避免异常字符，注意大小写");
    }

    /**
     * 异常代码识别
     *
     * @param statusCode 异常代码
     * @return 错误信息
     */
    public String getCause(int statusCode) {
        if (RETURN_CODE_MAP.containsKey(statusCode)) {
            //根据错误码返回错误代码
            return statusCode + ":" + RETURN_CODE_MAP.get(statusCode);
        }
        return statusCode + ":操作异常";
    }
}
