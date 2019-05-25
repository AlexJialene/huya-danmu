# huya-danmu
🐯Get the bullet screen of the [](http://www.huya.com/)
> In development

## preface
To apply this app you must apply for developer qualification on the open platform ([](http://open.huya.com)).

## quick start

```java

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

//TODO




