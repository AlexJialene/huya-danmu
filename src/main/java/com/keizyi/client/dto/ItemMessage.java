package com.keizyi.client.dto;

public class ItemMessage {

    private String itemName;
    private String presenterNick;
    private Integer roomId;
    private Integer sendItemComboHits;
    private Integer sendItemCount;
    private String sendNick;
    private String senderAvatarurl;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPresenterNick() {
        return presenterNick;
    }

    public void setPresenterNick(String presenterNick) {
        this.presenterNick = presenterNick;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getSendItemComboHits() {
        return sendItemComboHits;
    }

    public void setSendItemComboHits(Integer sendItemComboHits) {
        this.sendItemComboHits = sendItemComboHits;
    }

    public Integer getSendItemCount() {
        return sendItemCount;
    }

    public void setSendItemCount(Integer sendItemCount) {
        this.sendItemCount = sendItemCount;
    }

    public String getSendNick() {
        return sendNick;
    }

    public void setSendNick(String sendNick) {
        this.sendNick = sendNick;
    }

    public String getSenderAvatarurl() {
        return senderAvatarurl;
    }

    public void setSenderAvatarurl(String senderAvatarurl) {
        this.senderAvatarurl = senderAvatarurl;
    }

    @Override
    public String toString() {
        return "ItemMessage{" +
                "itemName='" + itemName + '\'' +
                ", presenterNick='" + presenterNick + '\'' +
                ", roomId=" + roomId +
                ", sendItemComboHits=" + sendItemComboHits +
                ", sendItemCount=" + sendItemCount +
                ", sendNick='" + sendNick + '\'' +
                ", senderAvatarurl='" + senderAvatarurl + '\'' +
                '}';
    }
}
