package com.keizyi.client.kit;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class KeepAlive extends Thread {

    private Channel channel;

    public KeepAlive(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {

        while (null != channel && channel.isActive()) {
            TextWebSocketFrame frame = new TextWebSocketFrame("ping");
            channel.writeAndFlush(frame).addListener(channelFuture -> {
                if (channelFuture.isSuccess()) {
                    System.out.println("Keep-Alive send success ====>");
                }
            });

            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
