package com.madchan.imsdk.comp.ui.rongcloud.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.madchan.imsdk.comp.ui.R;

import java.util.List;

public class DefaultProvider implements IViewProvider {
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setText(R.string.rc_default_message);
        return new ViewHolder(parent.getContext(), textView);
    }

    @Override
    public boolean isItemViewType(Object item) {
        return true;
    }

    @Override
    public void bindViewHolder(ViewHolder holder, Object o, int position, List list, IViewProviderListener listener) {

    }

}
