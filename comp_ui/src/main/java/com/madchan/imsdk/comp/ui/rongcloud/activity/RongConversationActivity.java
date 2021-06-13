package com.madchan.imsdk.comp.ui.rongcloud.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.madchan.imsdk.comp.ui.R;
import com.madchan.imsdk.comp.ui.rongcloud.fragment.ConversationFragment;
import com.madchan.imsdk.comp.ui.rongcloud.util.RouteUtils;

import java.util.Locale;

public class RongConversationActivity extends RongBaseActivity {
    protected String mTargetId;
    protected ConversationFragment mConversationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            mTargetId = getIntent().getStringExtra(RouteUtils.TARGET_ID);
        }
        setContentView(R.layout.rc_conversation_activity);
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
