package org.weixin4j.miniprogram.model.broadcast.room;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 回放信息
 *
 * @author yangqisheng
 * @since 1.0.3
 */
public class RoomReplay {
    /**
     * 回放视频链接
     */
    @JSONField(name = "media_url")
    private String mediaUrl;
    /**
     * 回放视频创建时间
     */
    @JSONField(name = "create_time")
    private String createTime;
    /**
     * 回放视频url过期时间
     */
    @JSONField(name = "expire_time")
    private String expireTime;

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

}
