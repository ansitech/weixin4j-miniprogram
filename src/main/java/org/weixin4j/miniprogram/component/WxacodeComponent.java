package org.weixin4j.miniprogram.component;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedInputStream;
import org.weixin4j.miniprogram.WeixinException;
import org.weixin4j.miniprogram.WeixinMiniprogram;
import org.weixin4j.miniprogram.http.HttpsClient;
import org.weixin4j.miniprogram.http.Response;
import org.weixin4j.miniprogram.model.wxacode.Wxacode;

/**
 * 小程序码组件
 *
 * @author yangqisheng
 * @since 1.0.0
 */
public class WxacodeComponent extends AbstractComponent {

    public WxacodeComponent(WeixinMiniprogram miniprogram) {
        super(miniprogram);
    }

    /**
     * 获取小程序码
     *
     * <p>
     * 适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制。</p>
     *
     * @param scene 场景值，最大32个可见字符，只支持数字，大小写英文以及部分特殊字符
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     * @return 小程序码
     */
    public Wxacode getUnlimited(String scene) throws WeixinException {
        return getUnlimited(scene, null);
    }

    /**
     * 获取小程序码
     *
     * <p>
     * 适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制。</p>
     *
     * @param scene 场景值，最大32个可见字符，只支持数字，大小写英文以及部分特殊字符
     * @param page 小程序页面路径，必须是已经发布的小程序存在的页面
     * @return 小程序码
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     */
    public Wxacode getUnlimited(String scene, String page) throws WeixinException {
        return getUnlimited(scene, page, 430, false, new int[]{0, 0, 0}, false);
    }

    /**
     * 获取小程序码
     *
     * <p>
     * 适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制。</p>
     *
     * @param scene 场景值，最大32个可见字符，只支持数字，大小写英文以及部分特殊字符
     * @param page 小程序页面路径，必须是已经发布的小程序存在的页面
     * @param width 二维码的宽度，单位 px，最小 280px，最大 1280px
     * @param auto_color 是否自动配置线条颜色
     * @param rgb RGB颜色值数组，auto_color 为 false 时生效，使用 rgb 设置颜色
     * @param is_hyaline 是否需要透明底色，为 true 时，生成透明底色的小程序
     * @return 小程序码
     * @throws org.weixin4j.miniprogram.WeixinException 微信操作异常
     */
    public Wxacode getUnlimited(String scene, String page, int width, boolean auto_color, int[] rgb, boolean is_hyaline) throws WeixinException {
        if (scene == null || "".equals(scene)) {
            throw new IllegalStateException("scene can  not be null or empty");
        }
        if (width < 280 || width > 1280) {
            throw new IllegalStateException("width超出280-1280范围");
        }
        JSONObject postData = new JSONObject();
        postData.put("scene", scene);
        if (page != null && !"".equals(page)) {
            postData.put("page", page);
        }
        postData.put("width", width);
        postData.put("auto_color", auto_color);
        if (rgb != null && rgb.length == 3
                && rgb[0] >= 0 && rgb[0] <= 255
                && rgb[1] >= 0 && rgb[1] <= 255
                && rgb[2] >= 0 && rgb[2] <= 255) {
            JSONObject rgbJson = new JSONObject();
            rgbJson.put("r", rgb[0]);
            rgbJson.put("g", rgb[1]);
            rgbJson.put("b", rgb[2]);
            postData.put("rgb", rgbJson);
        }
        postData.put("is_hyaline", is_hyaline);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //调用创建Tick的access_token接口
        Response res = http.post("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + miniprogram.getToken().getAccess_token(), postData);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(res.asStream());
        Wxacode wxacode = new Wxacode();
        wxacode.setBufferedInputStream(bufferedInputStream);
        return wxacode;
    }
}
