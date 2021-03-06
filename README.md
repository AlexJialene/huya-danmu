# huya-danmu
🐯Get the bullet screen of the [http://www.huya.com/](http://www.huya.com/)

---

[Golang version](https://github.com/AlexJialene/go-huya-danmu)| [LICENSE:Apache-2.0](https://github.com/AlexJialene/huya-danmu/blob/master/LICENSE)

## preface
To apply this app you must apply for developer qualification on the open platform ([http://open.huya.com](http://open.huya.com)).

## quick start

```

//openId ， secretId
SocketClient socketClient = new SocketClient("XXXXX" , "XXXXX");

//弹幕消息接收启动
//link ， roomId
socketClient.start(WSLink.MESSAGE_NOTICE , 518512);
//礼物消息接收启动
socketClient.start(WSLink.ITEM_NOTICE , 518512);

```
Because huya different types are different websocket links, so you need to start three times separately.

## callback

```$java

SocketClient socketClient = new SocketClient("appId" , "secretId", new Callback() {
    @Override
    public void message(Message message) {
        System.out.println("弹幕消息====>"+message.toString());
    }

    @Override
    public void itemMessage(ItemMessage message) {
        System.out.println("礼物消息====>"+message.toString());
    }

    @Override
    public void vipMessage(VipMessage message) {
        System.out.println("贵族进场====>"+message.toString());
    }
});
socketClient.start(WSLink.MESSAGE_NOTICE , 522233);
socketClient.start(WSLink.ITEM_NOTICE , 522233);
socketClient.start(WSLink.VIP_NOTICE , 522233);

```

## SSL support
* WSLink.MESSAGE_NOTICE_SSL
* WSLink.ITEM_NOTICE_SSL
* WSLink.VIP_NOTICE_SSL

## Maven

```
<repositories>
    <repository>
        <id>lamkeizyi-repo</id>
        <url>https://raw.githubusercontent.com/AlexJialene/maven-repo/master/repository</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>huya-danmu</groupId>
        <artifactId>huya-danmu</artifactId>
        <version>1.0.2</version>
    </dependency>
</dependencies>
```

## Other 
[callback parameter description](https://github.com/AlexJialene/go-huya-danmu#callback-parameter-description)




