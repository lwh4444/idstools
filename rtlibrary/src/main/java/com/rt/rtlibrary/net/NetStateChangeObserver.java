package com.rt.rtlibrary.net;

import com.blankj.utilcode.util.NetworkUtils;

/**
 * 网络监控服务
 * create by lei
 */
public interface NetStateChangeObserver {
    void onNetDisconnected();

    void onNetConnected(NetworkUtils.NetworkType networkType);
}
