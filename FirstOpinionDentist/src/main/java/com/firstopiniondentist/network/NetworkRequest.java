package com.firstopiniondentist.network;

/**
 * Created by jasmeetsingh on 6/28/16.
 */

public interface NetworkRequest {

    public Object success( Object data);
    public Object failed(Object data);
}
