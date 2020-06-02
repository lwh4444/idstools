package com.rt.rtlibrary.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;


import com.blankj.utilcode.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络监控服务
 * create by lei
 */
public class NetStateChangeReceiver extends BroadcastReceiver {
    private NetworkUtils.NetworkType mType = NetworkUtils.getNetworkType();

    private static class InstanceHolder {
        private static final NetStateChangeReceiver INSTANCE = new NetStateChangeReceiver();
    }

    private List<NetStateChangeObserver> mObservers = new ArrayList<NetStateChangeObserver>();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType();
            notifyObservers(networkType);
        }
    }

    public static void registerReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(InstanceHolder.INSTANCE, intentFilter);
    }

    public static void unRegisterReceiver(Context context) {
        context.unregisterReceiver(InstanceHolder.INSTANCE);
    }

    public static void registerObserver(NetStateChangeObserver observer) {
        if (observer == null) {
            return;
        }
        if (!InstanceHolder.INSTANCE.mObservers.contains(observer)) {
            InstanceHolder.INSTANCE.mObservers.add(observer);
        }
    }

    public static void unRegisterObserver(NetStateChangeObserver observer) {
        if (observer == null) {
            return;
        }
        if (InstanceHolder.INSTANCE.mObservers == null) {
            return;
        }
        InstanceHolder.INSTANCE.mObservers.remove(observer);
    }

    private void notifyObservers(NetworkUtils.NetworkType networkType) {
        if (mType == networkType) {
            return;
        }
        mType = networkType;
        if (networkType == NetworkUtils.NetworkType.NETWORK_NO) {
            for (NetStateChangeObserver observer : mObservers) {
                observer.onNetDisconnected();
            }
        } else {
            for (NetStateChangeObserver observer : mObservers) {
                observer.onNetConnected(networkType);
            }
        }
    }

}
