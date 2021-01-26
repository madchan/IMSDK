package com.madchan.imsdk.comp.remote;
import com.madchan.imsdk.comp.remote.bean.Envelope;
import com.madchan.imsdk.comp.remote.listener.MessageReceiver;

interface MessageCarrier {
    void sendMessage(in Envelope envelope);
    void registerReceiveListener(MessageReceiver messageReceiver);
    void unregisterReceiveListener(MessageReceiver messageReceiver);
}