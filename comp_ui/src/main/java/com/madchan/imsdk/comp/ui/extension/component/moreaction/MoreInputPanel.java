package com.madchan.imsdk.comp.ui.extension.component.moreaction;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.madchan.imsdk.comp.ui.R;

public class MoreInputPanel {
    private View mRootView;
    private MoreActionLayout mMoreActionLayout;

    @SuppressLint("ClickableViewAccessibility")
    public MoreInputPanel(Fragment fragment, ViewGroup parent) {
        mRootView = LayoutInflater.from(fragment.getContext()).inflate(R.layout.rc_more_input_panel, parent, false);
        mMoreActionLayout = mRootView.findViewById(R.id.container);
        mMoreActionLayout.setFragment(fragment);
//        mMoreActionLayout.addActions(RongConfigCenter.conversationConfig().getMoreClickActions());
    }

    public View getRootView() {
        return mRootView;
    }

    public void refreshView(boolean activeState) {
        mMoreActionLayout.refreshView(activeState);
    }
}
