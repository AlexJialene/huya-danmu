package com.keizyi.client.dto;

public class VipMessage {
    private String badgeName;
    private Integer fansLevel;
    private Integer nobleLevel;
    private String nobleName;
    private Integer roomId;
    private String userAvatarUrl;
    private String userNick;
    private Integer weekRank;
    private Integer guardLevel;

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public Integer getFansLevel() {
        return fansLevel;
    }

    public void setFansLevel(Integer fansLevel) {
        this.fansLevel = fansLevel;
    }

    public Integer getNobleLevel() {
        return nobleLevel;
    }

    public void setNobleLevel(Integer nobleLevel) {
        this.nobleLevel = nobleLevel;
    }

    public String getNobleName() {
        return nobleName;
    }

    public void setNobleName(String nobleName) {
        this.nobleName = nobleName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public Integer getWeekRank() {
        return weekRank;
    }

    public void setWeekRank(Integer weekRank) {
        this.weekRank = weekRank;
    }

    public Integer getGuardLevel() {
        return guardLevel;
    }

    public void setGuardLevel(Integer guardLevel) {
        this.guardLevel = guardLevel;
    }

    @Override
    public String toString() {
        return "VipMessage{" +
                "badgeName='" + badgeName + '\'' +
                ", fansLevel=" + fansLevel +
                ", nobleLevel=" + nobleLevel +
                ", nobleName='" + nobleName + '\'' +
                ", roomId=" + roomId +
                ", userAvatarUrl='" + userAvatarUrl + '\'' +
                ", userNick='" + userNick + '\'' +
                ", weekRank=" + weekRank +
                ", guardLevel=" + guardLevel +
                '}';
    }
}
