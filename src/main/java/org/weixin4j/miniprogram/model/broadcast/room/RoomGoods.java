package org.weixin4j.miniprogram.model.broadcast.room;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 直播间商品
 *
 * @author yangqisheng
 * @since 1.0.3
 */
public class RoomGoods {
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品价格
     */
    private double price;
    /**
     * 商品封面图链接
     */
    @JSONField(name = "cover_img")
    private String coverImg;
    /**
     * 商品小程序路径
     */
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
