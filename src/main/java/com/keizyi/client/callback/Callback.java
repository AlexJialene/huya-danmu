package com.keizyi.client.callback;

import com.keizyi.client.dto.ItemMessage;
import com.keizyi.client.dto.Message;
import com.keizyi.client.dto.VipMessage;

public interface Callback {

    void  message(Message message);

    void  itemMessage(ItemMessage itemMessage);

    void  vipMessage(VipMessage vipMessage);

}
