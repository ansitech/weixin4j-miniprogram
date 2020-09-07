package org.weixin4j.miniprogram.component;

import com.alibaba.fastjson.JSONObject;
import org.weixin4j.miniprogram.Configuration;
import org.weixin4j.miniprogram.WeixinException;
import org.weixin4j.miniprogram.WeixinMiniprogram;
import org.weixin4j.miniprogram.http.HttpsClient;
import org.weixin4j.miniprogram.http.Response;
import org.weixin4j.miniprogram.model.template.message.TemplateMessageData;

import java.util.List;

/**
 * 模板消息
 *
 * @author yangqisheng
 * @since 1.0.2
 */
@Deprecated
public class TemplateMessageComponent extends AbstractComponent {

    public TemplateMessageComponent(WeixinMiniprogram miniprogram) {
        super(miniprogram);
    }

    /**
     * 发送模板消息
     *
     * @param openid 接收者（用户）的 openid
     * @param templateId 所需下发的模板消息的id
     * @param formId 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
     * @param datas 模板内容，不填则下发空模板
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 1.0.2
     */
    public void send(String openid, String templateId, String formId,
                     List<TemplateMessageData> datas) throws WeixinException {
        send(openid, templateId, formId, datas, null, null);
    }

    /**
     * 发送模板消息
     *
     * @param openid 接收者（用户）的 openid
     * @param templateId 所需下发的模板消息的id
     * @param formId 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
     * @param datas 模板内容，不填则下发空模板
     * @param page 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 1.0.2
     */
    public void send(String openid, String templateId, String formId,
                     List<TemplateMessageData> datas, String page) throws WeixinException {
        send(openid, templateId, formId, datas, page, null);
    }

    /**
     * 发送模板消息
     *
     * @param openid 接收者（用户）的 openid
     * @param templateId 所需下发的模板消息的id
     * @param formId 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
     * @param datas 模板内容，不填则下发空模板
     * @param page 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
     * @param emphasisKeyword 模板需要放大的关键词，不填则默认无放大
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @since 1.0.2
     */
    public void send(String openid, String templateId, String formId,
                     List<TemplateMessageData> datas, String page, String emphasisKeyword) throws WeixinException {
        //内部业务验证
        if (openid == null || openid.equals("")) {
            throw new IllegalStateException("openid can not be null or empty");
        }
        if (templateId == null || templateId.equals("")) {
            throw new IllegalStateException("templateId can not be null or empty");
        }
        if (formId == null || formId.equals("")) {
            throw new IllegalStateException("formId can not be null or empty");
        }
        JSONObject json = new JSONObject();
        json.put("touser", openid);
        json.put("templateId", templateId);
        json.put("formId", formId);
        //点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
        if (page != null && !page.equals("")) {
            json.put("page", page);
        }
        //模板内容，不填则下发空模板。具体格式请参考示例。
        JSONObject data = new JSONObject();
        for (TemplateMessageData templateData : datas) {
            JSONObject dataContent = new JSONObject();
            dataContent.put("value", templateData.getValue());
            data.put(templateData.getKey(), dataContent);
        }
        json.put("data", data);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        Response res = http.post("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //根据请求结果判定，是否验证成功
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj != null) {
            if (Configuration.isDebug()) {
                System.out.println("sendTemplateMessage返回json：" + jsonObj.toString());
            }
            Object errcode = jsonObj.get("errcode");
            if (errcode != null && !errcode.toString().equals("0")) {
                //返回异常信息
                throw new WeixinException(getCause(jsonObj.getIntValue("errcode")));
            }
        }
    }
}
