package org.weixin4j.miniprogram.model.broadcast.room;

import java.util.List;

/**
 * 直播回放
 *
 * @author yangqisheng
 * @since 1.0.3
 */
public class LiveReplayInfo {
    private int total;
    private List<RoomReplay> replays;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RoomReplay> getReplays() {
        return replays;
    }

    public void setReplays(List<RoomReplay> replays) {
        this.replays = replays;
    }
}
