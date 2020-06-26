/*
 * 微信公众平台(JAVA) SDK
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
package org.weixin4j.miniprogram.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.weixin4j.miniprogram.model.payment.RequestPayment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 签名算法
 *
 * @author yangqisheng
 * @since 1.0.3
 */
public class SignUtil {

    /**
     * 生成小程序支付签名
     *
     * 签名生成规则如下：
     *
     * paySign = MD5(appId=wxd678efh567hg6787&nonceStr=5K8264ILTKCH16CQ2502SI8ZNMTM67VS&package=prepay_id=wx2017033010242291fcfe0db70013231072&signType=MD5&timeStamp=1490840662&key=qazwsxedcrfvtgbyhnujmikolp111111)
     *
     * 22D9B4E54AB1950F51E0649E8810ACD6
     *
     * @param appId 小程序IF
     * @return 成功返回授权签名对象，否则返回null
     * @since 1.0.3
     */
    public static RequestPayment getRequestPayment(String appId, String prepayId, String mchKey) {
        Map<String, String> params = new HashMap(16);
        params.put("appId", appId);
        params.put("nonceStr", UUID.randomUUID().toString().replaceAll("-", ""));
        params.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
        params.put("package", "prepay_id=" + prepayId);
        params.put("signType", "MD5");

        //1.1 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）
        Map<String, String> sortParams = MapUtil.sortAsc(params);
        //1.2 使用URL键值对的格式拼接成字符串
        String string1 = MapUtil.mapJoin(sortParams, false);
        //拼接签名字符串
        String stringSignTemp = string1 + "&key=" + mchKey;
        //2 对string1进行MD5签名
        String paySign = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
        //返回签名对象
        RequestPayment requestPayment = new RequestPayment();
        requestPayment.setNonceStr(params.get("nonceStr"));
        requestPayment.setTimeStamp(params.get("timeStamp"));
        requestPayment.setPackages(params.get("package"));
        requestPayment.setSignType(params.get("signType"));
        requestPayment.setPaySign(paySign);
        return requestPayment;
    }
}
