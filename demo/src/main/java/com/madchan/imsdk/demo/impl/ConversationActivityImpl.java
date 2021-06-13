package com.madchan.imsdk.demo.impl;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.madchan.imsdk.comp.ui.rongcloud.activity.RongBaseActivity;
import com.madchan.imsdk.comp.ui.rongcloud.fragment.ConversationFragment;
import com.madchan.imsdk.demo.R;

public class ConversationActivityImpl extends RongBaseActivity {
    protected String mTargetId;
    protected ConversationFragment mConversationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_impl);
        setTitle();
        mConversationFragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        mTitleBar.getRightView().setVisibility(View.GONE);
        initViewModel();
    }

    private void setTitle() {
        mTitleBar.setTitle("张三");
    }

    private void initViewModel() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            if (mConversationFragment != null && !mConversationFragment.onBackPressed()) {
                finish();
            }
        }
        return false;
    }
}
