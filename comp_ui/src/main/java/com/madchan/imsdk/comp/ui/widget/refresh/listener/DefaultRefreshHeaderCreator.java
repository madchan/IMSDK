package com.madchan.imsdk.comp.ui.widget.refresh.listener;

import android.content.Context;

import androidx.annotation.NonNull;

import com.madchan.imsdk.comp.ui.widget.refresh.api.RefreshHeader;
import com.madchan.imsdk.comp.ui.widget.refresh.api.RefreshLayout;

/**
 * 默认Header创建器
 * Created by scwang on 2018/1/26.
 */
public interface DefaultRefreshHeaderCreator {
    @NonNull
    RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout);
}
