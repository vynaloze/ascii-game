package com.pps.asciigame.common;

import com.pps.asciigame.common.protocol.Message;

public interface Dispatcher {
    void dispatch(final Message message);
}
