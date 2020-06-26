package org.weixin4j.miniprogram.component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.weixin4j.miniprogram.Configuration;
import org.weixin4j.miniprogram.WeixinException;
import org.weixin4j.miniprogram.WeixinMiniprogram;
import org.weixin4j.miniprogram.http.HttpsClient;
import org.weixin4j.miniprogram.http.Response;
import org.weixin4j.miniprogram.model.broadcast.room.LiveReplayInfo;
import org.weixin4j.miniprogram.model.broadcast.room.LiveRoom;
import org.weixin4j.miniprogram.model.broadcast.room.RoomInfo;
import org.weixin4j.miniprogram.model.broadcast.room.RoomReplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 直播间组件
 *
 * @author yangqisheng
 * @since 1.0.3
 */
public class LiveRoomComponent extends AbstractComponent {

    public LiveRoomComponent(WeixinMiniprogram miniprogram) {
        super(miniprogram);
    }

    /**
     * 创建直播间
     *
     * @param liveRoom 直播间信息
     * @return 房间ID
     * @throws WeixinException 微信操作异常
     * @since 1.0.3
     */
    public int createLiveRoom(LiveRoom liveRoom) throws WeixinException {
        JSONObject json = (JSONObject) JSONObject.toJSON(liveRoom);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //创建直播间
        Response res = http.post("https://api.weixin.qq.com/wxaapi/broadcast/room/create?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //返回结果处理
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return 0;
        }
        if (Configuration.isDebug()) {
            System.out.println("createLiveRoom返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getSelfCause(jsonObj.getIntValue("errcode")));
        }
        //返回房间号
        return jsonObj.getIntValue("roomId");
    }

    /**
     * 获取直播间列表
     *
     * @param start 起始房间，0表示从第1个房间开始拉取
     * @param limit 每次拉取的房间数量，建议100以内
     * @return 直播间列表
     * @throws WeixinException 微信操作异常
     * @since 1.0.3
     */
    public List<RoomInfo> getLiveInfo(int start, int limit) throws WeixinException {
        JSONObject json = new JSONObject();
        json.put("start", start);
        json.put("limit", limit);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //创建直播间
        Response res = http.post("http://api.weixin.qq.com/wxa/business/getliveinfo?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //返回结果处理
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return null;
        }
        if (Configuration.isDebug()) {
            System.out.println("getLiveInfo返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getSelfCause(jsonObj.getIntValue("errcode")));
        }
        //获取直播间列表
        JSONArray roomInfo = jsonObj.getJSONArray("room_info");
        List<RoomInfo> rooms = new ArrayList<RoomInfo>();
        for (Object room : roomInfo) {
            rooms.add(JSONObject.toJavaObject((JSONObject) room, RoomInfo.class));
        }
        return rooms;
    }

    /**
     * 获取直播间回放
     *
     * @param roomId 直播间ID
     * @param start 起始拉取视频，0表示从第一个视频片段开始拉取
     * @param limit 每次拉取的房间数量，建议100以内
     * @return 回放视频信息
     * @throws WeixinException 微信操作异常
     * @since 1.0.3
     */
    public LiveReplayInfo getLiveReplayInfo(int roomId, int start, int limit) throws WeixinException {
        JSONObject json = new JSONObject();
        json.put("action", "get_replay");
        json.put("room_id", roomId);
        json.put("start", start);
        json.put("limit", limit);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //创建直播间
        Response res = http.post("http://api.weixin.qq.com/wxa/business/getliveinfo?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //返回结果处理
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return null;
        }
        if (Configuration.isDebug()) {
            System.out.println("getLiveReplayInfo返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getSelfCause(jsonObj.getIntValue("errcode")));
        }
        LiveReplayInfo liveReplayInfo = new LiveReplayInfo();
        liveReplayInfo.setTotal(jsonObj.getIntValue("total"));
        //获取直播间列表
        JSONArray liveReplay = jsonObj.getJSONArray("live_replay");
        List<RoomReplay> roomReplays = new ArrayList<RoomReplay>();
        for (Object replay : liveReplay) {
            roomReplays.add(JSONObject.toJavaObject((JSONObject) replay, RoomReplay.class));
        }
        liveReplayInfo.setReplays(roomReplays);
        return liveReplayInfo;
    }

    /**
     * 直播间添加商品
     *
     * @param roomId 房间号
     * @param goodsIds 数组列表，可传入多个，里面填写 商品 ID
     * @return 直播间列表
     * @throws WeixinException 微信操作异常
     * @since 1.0.3
     */
    public boolean addRoomGoods(int roomId, int[] goodsIds) throws WeixinException {
        JSONObject json = new JSONObject();
        json.put("roomId", roomId);
        json.put("ids", goodsIds);
        //创建请求对象
        HttpsClient http = new HttpsClient();
        //创建直播间
        Response res = http.post("https://api.weixin.qq.com/wxaapi/broadcast/room/addgoods?access_token=" + miniprogram.getToken().getAccess_token(), json);
        //返回结果处理
        JSONObject jsonObj = res.asJSONObject();
        if (jsonObj == null) {
            return false;
        }
        if (Configuration.isDebug()) {
            System.out.println("addRoomGoods返回json：" + jsonObj.toString());
        }
        Object errcode = jsonObj.get("errcode");
        if (errcode != null && jsonObj.getIntValue("errcode") != 0) {
            //返回异常信息
            throw new WeixinException(getSelfCause(jsonObj.getIntValue("errcode")));
        }
        return true;
    }

    private final static Map<Integer, String> RETURN_CODE_MAP = new HashMap<Integer, String>();

    static {
        RETURN_CODE_MAP.put(-1, "系统繁忙，此时请开发者稍候再试");
        RETURN_CODE_MAP.put(0, "请求成功");
        RETURN_CODE_MAP.put(1, "未创建直播间");
        RETURN_CODE_MAP.put(1003, "商品id不存在");
        RETURN_CODE_MAP.put(47001, "入参格式不符合规范");
        RETURN_CODE_MAP.put(200002, "入参错误");
        RETURN_CODE_MAP.put(300001, "禁止创建/更新商品 或 禁止编辑&更新房间");
        RETURN_CODE_MAP.put(300002, "名称长度不符合规则");
        RETURN_CODE_MAP.put(300006, "图片上传失败（如：mediaID过期)");
        RETURN_CODE_MAP.put(300022, "此房间号不存在");
        RETURN_CODE_MAP.put(300023, "房间状态 拦截（当前房间状态不允许此操作）");
        RETURN_CODE_MAP.put(300024, "商品不存在");
        RETURN_CODE_MAP.put(300025, "商品审核未通过");
        RETURN_CODE_MAP.put(300026, "房间商品数量已经满额");
        RETURN_CODE_MAP.put(300027, "导入商品失败");
        RETURN_CODE_MAP.put(300028, "房间名称违规");
        RETURN_CODE_MAP.put(300029, "主播昵称违规");
        RETURN_CODE_MAP.put(300030, "主播微信号不合法");
        RETURN_CODE_MAP.put(300031, "直播间封面图不合规");
        RETURN_CODE_MAP.put(300032, "直播间分享图违规");
        RETURN_CODE_MAP.put(300033, "添加商品超过直播间上限");
        RETURN_CODE_MAP.put(300034, "主播微信昵称长度不符合要求");
        RETURN_CODE_MAP.put(300035, "主播微信号不存在");
        RETURN_CODE_MAP.put(300036, "主播微信号未实名认证");
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
