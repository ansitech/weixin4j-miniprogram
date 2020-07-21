package org.weixin4j.miniprogram.component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.weixin4j.miniprogram.Configuration;
import org.weixin4j.miniprogram.WeixinException;
import org.weixin4j.miniprogram.WeixinMiniprogram;
import org.weixin4j.miniprogram.http.HttpsClient;
import org.weixin4j.miniprogram.http.Response;
import org.weixin4j.miniprogram.model.broadcast.goods.AddGoodsResult;
import org.weixin4j.miniprogram.model.broadcast.goods.GoodsStat;
import org.weixin4j.miniprogram.model.broadcast.goods.GoodsWareHouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理组件
 *
 * @author yangqisheng
 * @since 1.0.3
 */
public class LiveGoodsComponent extends AbstractComponent {

    public LiveGoodsComponent(WeixinMiniprogram miniprogram) {
        super(miniprogram);
    }

    /**
     * 商品添加并提审
     *
     * @param name 商品名称，最长14个汉字，1个汉字相当于2个字符
     * @param priceType 价格类型，1：一口价 2：价格区间 3：显示折扣价
     * @param price 价格1，数字，最多保留两位小数，单位元
     * @param price2 价格2，数字，最多保留两位小数，单位元
     * @param coverImgUrl 填入mediaID
     * @param url 商品详情页的小程序路径，路径参数存在 url 的，该参数的值需要进行 encode 处理再填入
     * @return 操作结果
     * @throws WeixinException 微信操作异常
     * @since 1.0.3
     */
    public AddGoodsResult addGoods(
            String name, int priceType, double price, double price2,
            String coverImgUrl, String url) throws WeixinException {
        JSONObject json = new JSONObject();
        JSONObject goodsInfo = new JSONObject();
        goodsInfo.put("name", name);
        goodsInfo.put("priceType", priceType);
        goodsInfo.put("price", price);
        if (priceType > 1) {
            goodsInfo.put("price2", price2);
        }
        goodsInfo.put("coverImgUrl", coverImgUrl);
        goodsInfo.put("url", url);
        json.put("goodsInfo", goodsInfo);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //创建直播间
        Response res = http.post("https://api.weixin.qq.com/wxaapi/broadcast/goods/add?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //返回结果处理
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return null;
        }
        if (Configuration.isDebug()) {
            System.out.println("addGoods返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getSelfCause(jsonObj.getIntValue("errcode")));
        }
        //创建返回结果
        AddGoodsResult result = new AddGoodsResult();
        result.setGoodsId(jsonObj.getLongValue("goodsId"));
        result.setAuditId(jsonObj.getLongValue("auditId"));
        return result;
    }


    /**
     * 撤回审核
     *
     * @param auditId 审核单ID
     * @param goodsId 商品ID
     * @return 操作结果
     * @throws WeixinException 微信操作异常
     * @since 1.0.3
     */
    public boolean resetAuditGoods(long auditId, long goodsId) throws WeixinException {
        JSONObject json = new JSONObject();
        json.put("auditId", auditId);
        json.put("goodsId", goodsId);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //创建直播间
        Response res = http.post("https://api.weixin.qq.com/wxaapi/broadcast/goods/resetaudit?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //返回结果处理
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return false;
        }
        if (Configuration.isDebug()) {
            System.out.println("resetAuditGoods返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getSelfCause(jsonObj.getIntValue("errcode")));
        }
        return true;
    }

    /**
     * 重新提交审核
     *
     * @param goodsId 商品ID
     * @return 操作结果
     * @throws WeixinException 微信操作异常
     * @since 1.0.3
     */
    public AddGoodsResult auditGoods(long goodsId) throws WeixinException {
        JSONObject json = new JSONObject();
        json.put("goodsId", goodsId);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //创建直播间
        Response res = http.post("https://api.weixin.qq.com/wxaapi/broadcast/goods/audit?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //返回结果处理
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return null;
        }
        if (Configuration.isDebug()) {
            System.out.println("auditGoods返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getSelfCause(jsonObj.getIntValue("errcode")));
        }
        //创建返回结果
        AddGoodsResult result = new AddGoodsResult();
        result.setGoodsId(goodsId);
        result.setAuditId(jsonObj.getLongValue("auditId"));
        return result;
    }


    /**
     * 删除商品
     *
     * @param goodsId 商品ID
     * @return 操作结果
     * @throws WeixinException 微信操作异常
     * @since 1.0.3
     */
    public boolean deleteGoods(long goodsId) throws WeixinException {
        JSONObject json = new JSONObject();
        json.put("goodsId", goodsId);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //创建直播间
        Response res = http.post("https://api.weixin.qq.com/wxaapi/broadcast/goods/delete?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //返回结果处理
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return false;
        }
        if (Configuration.isDebug()) {
            System.out.println("deleteGoods返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getSelfCause(jsonObj.getIntValue("errcode")));
        }
        return true;
    }

    /**
     * 更新商品
     *
     * @param goodsId 商品ID
     * @param name 商品名称，最长14个汉字，1个汉字相当于2个字符
     * @param priceType 价格类型，1：一口价 2：价格区间 3：显示折扣价
     * @param price 价格1，数字，最多保留两位小数，单位元
     * @param price2 价格2，数字，最多保留两位小数，单位元
     * @param coverImgUrl 填入mediaID
     * @param url 商品详情页的小程序路径，路径参数存在 url 的，该参数的值需要进行 encode 处理再填入
     * @return 操作结果
     * @throws WeixinException 微信操作异常
     * @since 1.0.3
     */
    public boolean updateGoods(
            long goodsId, String name, int priceType, double price, double price2,
            String coverImgUrl, String url) throws WeixinException {
        JSONObject json = new JSONObject();
        JSONObject goodsInfo = new JSONObject();
        goodsInfo.put("goodsId", goodsId);
        goodsInfo.put("name", name);
        goodsInfo.put("priceType", priceType);
        goodsInfo.put("price", price);
        if (priceType > 1) {
            goodsInfo.put("price2", price2);
        }
        goodsInfo.put("coverImgUrl", coverImgUrl);
        goodsInfo.put("url", url);
        json.put("goodsInfo", goodsInfo);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //创建直播间
        Response res = http.post("https://api.weixin.qq.com/wxaapi/broadcast/goods/update?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //返回结果处理
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return false;
        }
        if (Configuration.isDebug()) {
            System.out.println("updateGoods返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getSelfCause(jsonObj.getIntValue("errcode")));
        }
        return true;
    }

    /**
     * 获取商品状态
     *
     * @param goodsIds 商品ID素组
     * @return 商品状态
     * @throws WeixinException 微信操作异常
     * @since 1.0.3
     */
    public GoodsWareHouse getGoodsWareHouse(long[] goodsIds) throws WeixinException {
        JSONObject json = new JSONObject();
        json.put("goods_ids", goodsIds);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //创建直播间
        Response res = http.post("https://api.weixin.qq.com/wxa/business/getgoodswarehouse?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //返回结果处理
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return null;
        }
        if (Configuration.isDebug()) {
            System.out.println("getGoodsWareHouse返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getSelfCause(jsonObj.getIntValue("errcode")));
        }
        GoodsWareHouse goodsWareHouse = new GoodsWareHouse();
        goodsWareHouse.setTotal(jsonObj.getIntValue("total"));
        //获取直播间列表
        JSONArray goodsArray = jsonObj.getJSONArray("goods");
        List<GoodsStat> goodsList = new ArrayList<GoodsStat>();
        for (Object goods : goodsArray) {
            goodsList.add(JSONObject.toJavaObject((JSONObject) goods, GoodsStat.class));
        }
        goodsWareHouse.setGoodsList(goodsList);
        return goodsWareHouse;
    }

    private final static Map<Integer, String> RETURN_CODE_MAP = new HashMap<Integer, String>();

    static {
        RETURN_CODE_MAP.put(-1, "系统错误");
        RETURN_CODE_MAP.put(1003, "商品id不存在");
        RETURN_CODE_MAP.put(47001, "入参格式不符合规范");
        RETURN_CODE_MAP.put(200002, "入参错误");
        RETURN_CODE_MAP.put(300001, "禁止创建/更新商品（如：商品创建功能被封禁）");
        RETURN_CODE_MAP.put(300002, "名称长度不符合规则");
        RETURN_CODE_MAP.put(300003, "价格输入不合规（如：现价比原价大、传入价格非数字等）");
        RETURN_CODE_MAP.put(300004, "商品名称存在违规违法内容");
        RETURN_CODE_MAP.put(300005, "商品图片存在违规违法内容");
        RETURN_CODE_MAP.put(300006, "图片上传失败（如:mediaID过期）");
        RETURN_CODE_MAP.put(300007, "线上小程序版本不存在该链接");
        RETURN_CODE_MAP.put(300008, "添加商品失败");
        RETURN_CODE_MAP.put(300009, "商品审核撤回失败");
        RETURN_CODE_MAP.put(300010, "商品审核状态不对（如:商品审核中）");
        RETURN_CODE_MAP.put(300011, "操作非法（API不允许操作非API创建的商品）");
        RETURN_CODE_MAP.put(300012, "没有提审额度（每天500次提审额度）");
        RETURN_CODE_MAP.put(300013, "提审失败");
        RETURN_CODE_MAP.put(300014, "审核中，无法删除（非零代表失败）");
        RETURN_CODE_MAP.put(300017, "商品未提审");
        RETURN_CODE_MAP.put(300021, "商品添加成功，审核失败");
    }

    /**
     * 异常代码识别
     *
     * @param statusCode 异常代码
     * @return 错误信息
     */
    public String getSelfCause(int statusCode) {
        if (RETURN_CODE_MAP.containsKey(statusCode)) {
            //根据错误码返回错误代码
            return statusCode + ":" + RETURN_CODE_MAP.get(statusCode);
        }
        return statusCode + ":操作异常";
    }
}
