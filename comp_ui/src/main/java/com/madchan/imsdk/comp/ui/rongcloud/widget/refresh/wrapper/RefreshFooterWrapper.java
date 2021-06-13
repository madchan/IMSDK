package com.madchan.imsdk.comp.ui.rongcloud.widget.refresh.wrapper;

import android.annotation.SuppressLint;
import android.view.View;

import com.madchan.imsdk.comp.ui.rongcloud.widget.refresh.api.RefreshFooter;
import com.madchan.imsdk.comp.ui.rongcloud.widget.refresh.simple.SimpleComponent;

/**
 * 刷新底部包装
 * Created by scwang on 2017/5/26.
 */
@SuppressLint("ViewConstructor")
public class RefreshFooterWrapper extends SimpleComponent implements RefreshFooter {

    public RefreshFooterWrapper(View wrapper) {
        super(wrapper);
    }

}
