package com.keizyi.client.kit;

public enum  WSLink {

    MESSAGE_NOTICE(
            "ws://openapi.huya.com/index.html?do=getMessageNotice&data={}&appId={}&timestamp={}&sign={}"
    ),
    ITEM_NOTICE(
            "ws://openapi.huya.com/index.html?do=getSendItemNotice&data={}&appId={}&timestamp={}&sign={}"
    );


    private String link;

    WSLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }
}
