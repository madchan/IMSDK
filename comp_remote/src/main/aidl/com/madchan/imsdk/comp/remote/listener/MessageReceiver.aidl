// MessageReceiver.aidl
package com.madchan.imsdk.comp.remote.listener;
import com.madchan.imsdk.comp.remote.bean.Envelope;

interface MessageReceiver {
    void onMessageReceived(in Envelope envelope);
}