package com.keizyi.client.code;


import com.keizyi.client.kit.WSLink;
import com.keizyi.client.kit.Encrypt;
import com.keizyi.client.kit.KeepAlive;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class SocketClient {

    private String appId;
    private String key;

    private Map<ChannelId, SocketRequestConfig> channels;

    public SocketClient(String appId, String key) {
        this.appId = appId;
        this.key = key;
        channels = new ConcurrentHashMap<>();
    }

    public Channel start(WSLink link, int roomId) throws URISyntaxException, InterruptedException {
        String requestUrl = getRequestUrl(link, roomId);
        URI uri = new URI(requestUrl);
        SocketRequestConfig socketRequestConfig = initRequest(uri);
        socketRequestConfig.setRequestUrl(requestUrl);

        EventLoopGroup group = new NioEventLoopGroup();

        final WebSocketClientHandler handler =
                new WebSocketClientHandler(
                        WebSocketClientHandshakerFactory.newHandshaker(
                                uri,
                                WebSocketVersion.V13,
                                null,
                                false,
                                new DefaultHttpHeaders()));

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws SSLException {
                        ChannelPipeline p = ch.pipeline();
                        if (socketRequestConfig.isSsl()) {
                            SslContext sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                            p.addLast(sslCtx.newHandler(ch.alloc(), socketRequestConfig.getHost(), socketRequestConfig.getPort()));
                        }
                        p.addLast(new HttpClientCodec(), new HttpObjectAggregator(8192), handler);
                    }
                });

        Channel ch = b.connect(uri.getHost(), socketRequestConfig.getPort()).sync().channel();
        handler.setHandshaker(ch);
        handler.handshakeFuture().sync();

        //keepalive
        new KeepAlive(ch).start();

        setSocketRequestConfig(ch.id(), socketRequestConfig);

        return ch;
    }

    protected SocketRequestConfig initRequest(URI uri) {
        SocketRequestConfig socketRequestConfig = new SocketRequestConfig();
        String scheme = uri.getScheme() == null ? "ws" : uri.getScheme();
        socketRequestConfig.setHost(uri.getHost() == null ? "127.0.0.1" : uri.getHost());

        if (uri.getPort() == -1) {
            if ("ws".equalsIgnoreCase(scheme)) {
                socketRequestConfig.setPort(80);
            } else if ("wss".equalsIgnoreCase(scheme)) {
                socketRequestConfig.setPort(443);
            } else {
                socketRequestConfig.setPort(-1);
            }
        } else {
            socketRequestConfig.setPort(uri.getPort());
        }

        if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
            System.err.println("Only WS(S) is supported.");
            return null;
        }

        socketRequestConfig.setSsl("wss".equalsIgnoreCase(scheme));

        return socketRequestConfig;
    }

    protected String getRequestUrl(WSLink link, int roomId) {
        //generate param
        String data = "{\"roomId\":" + roomId + "}";
        int timestamp = getSecondTimestamp(new Date());
        String sign = genSign(data, this.key, timestamp);
        try {
            return urlAssembly(
                    link.getLink(),
                    URLEncoder.encode(data, "utf-8"),
                    this.appId,
                    timestamp,
                    sign
            );
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private String genSign(String data, String key, int timestamp) {
        return Encrypt.md5("data=" + data + "&key=" + key + "&timestamp=" + timestamp);
    }

    private String urlAssembly(String url, Object... param) {
        StringBuilder sb = new StringBuilder(url);
        Stream.of(param).forEach(k -> {
            sb.replace(sb.indexOf("{"), sb.indexOf("}") + 1, String.valueOf(k));

        });
        return sb.toString();
    }

    private int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    public SocketRequestConfig getSocketRequestConfig(ChannelId id) {
        return channels.get(id);
    }

    private void setSocketRequestConfig(ChannelId id, SocketRequestConfig socketRequestConfig) {
        channels.put(id, socketRequestConfig);
    }
}
