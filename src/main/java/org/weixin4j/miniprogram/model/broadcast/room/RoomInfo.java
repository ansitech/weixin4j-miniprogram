package org.weixin4j.miniprogram.model.broadcast.room;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 直播间信息
 *
 * @author yangqisheng
 * @since 1.0.3
 */
public class RoomInfo {
    /**
     * 房间号
     */
    @JSONField(name = "roomid")
    private int roomId;
    /**
     * 直播间名称
     */
    private String name;
    /**
     * 直播间背景图链接
     */
    @JSONField(name = "cover_img")
    private String coverImg;
    /**
     * 直播间分享图链接
     */
    @JSONField(name = "share_img")
    private String shareImg;
    /**
     * 直播间状态。
     *
     * 101：直播中，102：未开始，103已结束，104禁播，105：暂停，106：异常，107：已过期
     */
    @JSONField(name = "live_status")
    private int liveStatus;
    /**
     * 直播间开始时间，列表按照start_time降序排列
     */
    @JSONField(name = "start_time")
    private long startTime;
    /**
     * 直播计划结束时间
     */
    @JSONField(name = "end_time")
    private long endTime;
    /**
     * 主播昵称，最短2个汉字，最长15个汉字，1个汉字相当于2个字符
     */
    @JSONField(name = "anchor_name")
    private String anchorName;
    /**
     * 商品列表
     */
    private List<RoomGoods> goods;
    /**
     * 拉取房间总数
     */
    private int total;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public int getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(int liveStatus) {
        this.liveStatus = liveStatus;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public List<RoomGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<RoomGoods> goods) {
        this.goods = goods;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
