package org.weixin4j.miniprogram.model.broadcast.room;

/**
 * 直播间
 *
 * @author yangqisheng
 * @since 1.0.3
 */
public class LiveRoom {
    /**
     * 直播间名字，最短3个汉字，最长17个汉字，1个汉字相当于2个字符
     */
    private String name;
    /**
     * 背景图，填入mediaID（mediaID获取后，三天内有效）；
     *
     * 图片mediaID的获取，请参考以下文档： https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/New_temporary_materials.html；
     *
     * 直播间背景图，图片规则：建议像素1080*1920，大小不超过2M
     */
    private String coverImg;
    /**
     * 直播计划开始时间
     *
     * （开播时间需要在当前时间的10分钟后 并且 开始时间不能在 6 个月后）
     */
    private long startTime;
    /**
     * 直播计划结束时间
     *
     * （开播时间和结束时间间隔不得短于30分钟，不得超过24小时）
     */
    private long endTime;
    /**
     * 主播昵称，最短2个汉字，最长15个汉字，1个汉字相当于2个字符
     */
    private String anchorName;
    /**
     * 主播微信号，如果未实名认证，需要先前往“小程序直播”小程序进行实名验证
     */
    private String anchorWechat;
    /**
     * 分享图，填入mediaID（mediaID获取后，三天内有效）；
     *
     * 图片mediaID的获取，请参考以下文档： https://developers.weixin.qq.com/doc/offiaccount/Asset_Management/New_temporary_materials.html；
     *
     * 直播间分享图，图片规则：建议像素800*640，大小不超过1M；
     */
    private String shareImg;
    /**
     * 直播间类型 【1: 推流，0：手机直播】
     */
    private int type;
    /**
     * 横屏、竖屏 【1：横屏，0：竖屏】
     *
     * （横屏：视频宽高比为16:9、4:3、1.85:1 ；竖屏：视频宽高比为9:16、2:3）
     */
    private int screenType;
    /**
     * 是否关闭点赞 【0：开启，1：关闭】（若关闭，直播开始后不允许开启）
     */
    private int closeLike;
    /**
     * 是否关闭货架 【0：开启，1：关闭】（若关闭，直播开始后不允许开启）
     */
    private int closeGoods;
    /**
     * 是否关闭评论 【0：开启，1：关闭】（若关闭，直播开始后不允许开启）
     */
    private int closeComment;

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

    public String getAnchorWechat() {
        return anchorWechat;
    }

    public void setAnchorWechat(String anchorWechat) {
        this.anchorWechat = anchorWechat;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getScreenType() {
        return screenType;
    }

    public void setScreenType(int screenType) {
        this.screenType = screenType;
    }

    public int getCloseLike() {
        return closeLike;
    }

    public void setCloseLike(int closeLike) {
        this.closeLike = closeLike;
    }

    public int getCloseGoods() {
        return closeGoods;
    }

    public void setCloseGoods(int closeGoods) {
        this.closeGoods = closeGoods;
    }

    public int getCloseComment() {
        return closeComment;
    }

    public void setCloseComment(int closeComment) {
        this.closeComment = closeComment;
    }
}
