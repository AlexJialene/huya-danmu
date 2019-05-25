package com.keizyi.client;


import com.keizyi.client.code.SocketClient;
import com.keizyi.client.kit.WSLink;

import java.net.URISyntaxException;

public class Test {

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        SocketClient socketClient = new SocketClient("XXXXX" , "XXXXX");
        socketClient.start(WSLink.MESSAGE_NOTICE , 518512);
        socketClient.start(WSLink.ITEM_NOTICE , 518512);

        //System.out.println(socketClient.getSocketRequestConfig(channel.id()).getHost());


    }
}
