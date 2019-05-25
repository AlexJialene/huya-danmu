package com.keizyi.client.code;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.keizyi.client.callback.Callback;
import com.keizyi.client.dto.ItemMessage;
import com.keizyi.client.dto.Message;
import com.keizyi.client.dto.ResponseData;
import com.keizyi.client.dto.VipMessage;
import com.keizyi.client.kit.WSLink;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    private final WebSocketClientHandshaker webSocketClientHandshaker;
    private ChannelPromise handshakeFuture;
    private final Callback callback;
    private WSLink wsLink;

    public WebSocketClientHandler(WebSocketClientHandshaker webSocketClientHandshaker) {
        this(webSocketClientHandshaker, null);
    }

    public WebSocketClientHandler(WebSocketClientHandshaker webSocketClientHandshaker, Callback callback) {
        this.webSocketClientHandshaker = webSocketClientHandshaker;
        this.callback = callback;
        this.wsLink = WSLink.MESSAGE_NOTICE;
    }

    public WebSocketClientHandler(WebSocketClientHandshaker webSocketClientHandshaker, Callback callback, WSLink wsLink) {
        this.webSocketClientHandshaker = webSocketClientHandshaker;
        this.callback = callback;
        this.wsLink = wsLink;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    public void setHandshaker(Channel channel) {
        webSocketClientHandshaker.handshake(channel);
    }

    public void setWsLink(WSLink wsLink) {
        this.wsLink = wsLink;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!webSocketClientHandshaker.isHandshakeComplete()) {
            FullHttpResponse f = (FullHttpResponse) msg;
            System.out.println(f.toString());
            webSocketClientHandshaker.finishHandshake(ch, (FullHttpResponse) msg);
            System.out.println("WebSocket Client connected!");
            handshakeFuture.setSuccess();
            return;
        }

        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.getStatus() + ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            String text = textFrame.text();
            //System.out.println("WebSocket Client received message: " + text);
            //
            //ResponseData<Message> responseData = JSON.parseObject(text , new TypeReference<ResponseData<Message>>(){});
            //System.out.println(responseData.getData().getBadgeName());
            toCallback(text);
        } else if (frame instanceof PongWebSocketFrame) {
            System.out.println("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            System.out.println("WebSocket Client received closing");
            ch.close();
        }

    }

    private void toCallback(String text) {
        if (null == callback)
            return;
        switch (wsLink) {
            case MESSAGE_NOTICE:
            case MESSAGE_NOTICE_SSL:
                ResponseData<Message> responseData1 = JSON.parseObject(text, new TypeReference<ResponseData<Message>>() {
                });
                this.callback.message(responseData1.getData());
                break;
            case ITEM_NOTICE:
            case ITEM_NOTICE_SSL:
                ResponseData<ItemMessage> responseData2 = JSON.parseObject(text, new TypeReference<ResponseData<ItemMessage>>() {
                });
                this.callback.itemMessage(responseData2.getData());
                break;
            case VIP_NOTICE:
            case VIP_NOTICE_SSL:
                ResponseData<VipMessage> responseData3 = JSON.parseObject(text, new TypeReference<ResponseData<VipMessage>>() {
                });
                this.callback.vipMessage(responseData3.getData());
                break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

}
