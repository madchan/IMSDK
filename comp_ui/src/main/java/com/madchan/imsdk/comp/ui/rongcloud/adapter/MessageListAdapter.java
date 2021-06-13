package com.madchan.imsdk.comp.ui.rongcloud.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;

import com.madchan.imsdk.comp.ui.rongcloud.provider.TextMessageItemProvider;
import com.madchan.imsdk.lib.objects.bean.vo.MessageVo;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter extends BaseAdapter<MessageVo> {

    public MessageListAdapter(IViewProviderListener<MessageVo> listener) {
        super(listener, getMessageListProvider());
    }

    private static ProviderManager<MessageVo> getMessageListProvider() {
        ProviderManager<MessageVo> messageListProvider = new ProviderManager<>();
        messageListProvider.setDefaultProvider(new DefaultProvider());
        messageListProvider.addProvider(new TextMessageItemProvider());
        return  messageListProvider;
    }

    @Override
    public void setDataCollection(List<MessageVo> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        //当有空布局的时候，需要全部刷新
        if ((mDataList.size() == 0 && data.size() > 0) ||
                (mDataList.size() > 0 && data.size() == 0)) {
            super.setDataCollection(data);
            notifyDataSetChanged();
        } else {
            mDiffCallback.setNewList(data);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(mDiffCallback, false);
            super.setDataCollection(data);
            diffResult.dispatchUpdatesTo(new ListUpdateCallback() {
                @Override
                public void onInserted(int position, int count) {
                    notifyItemRangeInserted(getHeadersCount() + position, count);
                }

                @Override
                public void onRemoved(int position, int count) {
                    notifyItemRangeRemoved(getHeadersCount() + position, count);
                }

                @Override
                public void onMoved(int fromPosition, int toPosition) {
                    notifyItemMoved(getHeadersCount() + fromPosition, getHeadersCount() + toPosition);
                }

                @Override
                public void onChanged(int position, int count, @Nullable Object payload) {
                    notifyItemRangeChanged(getHeadersCount() + position, count, null);
                }
            });
        }
    }

    MessageDiffCallBack mDiffCallback = new MessageDiffCallBack();


    private class MessageDiffCallBack extends DiffUtil.Callback {
        private List<MessageVo> newList;

        @Override
        public int getOldListSize() {
            if (mDataList != null) {
                return mDataList.size();
            } else {
                return 0;
            }
        }

        @Override
        public int getNewListSize() {
            if (newList != null) {
                return newList.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return mDataList.get(oldItemPosition).getMessageId() == newList.get(newItemPosition).getMessageId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//            MessageVo newItem = newList.get(newItemPosition);
//            if (newItem.isChange()) {
//                return false;
//            }
            return true;
        }

        public void setNewList(List<MessageVo> newList) {
            this.newList = newList;
        }
    }

    ;
}