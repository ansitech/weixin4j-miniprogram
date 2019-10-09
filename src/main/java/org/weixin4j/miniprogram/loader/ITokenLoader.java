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
package org.weixin4j.miniprogram.loader;

import org.weixin4j.miniprogram.model.auth.Token;

/**
 * AccessToken加载接口
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public interface ITokenLoader {

    /**
     * 获取access_token
     * 
     * @return 包含access_token数据的对象
     */
    public Token get();

    /**
     * 刷新access_token
     *
     * @param accessToken 包含access_token数据的对象
     */
    public void refresh(Token accessToken);
}
