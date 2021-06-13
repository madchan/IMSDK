package com.madchan.imsdk.comp.ui.rongcloud.provider;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.madchan.imsdk.comp.ui.R;
import com.madchan.imsdk.comp.ui.rongcloud.adapter.IViewProviderListener;
import com.madchan.imsdk.comp.ui.rongcloud.adapter.ViewHolder;
import com.madchan.imsdk.lib.objects.bean.vo.MessageVo;

import java.util.List;

public abstract class BaseMessageItemProvider<T extends MessageVo> implements IMessageProvider {
    private static final String TAG = "BaseMessageItemProvider";
    protected MessageItemProviderConfig mConfig = new MessageItemProviderConfig();

    /**
     * 创建 ViewHolder
     *
     * @param parent   父 ViewGroup
     * @param viewType 视图类型
     * @return ViewHolder
     */
    protected abstract ViewHolder onCreateMessageContentViewHolder(ViewGroup parent, int viewType);

    /**
     * 设置消息视图里各 view 的值
     *
     * @param holder       ViewHolder
     * @param parentHolder 父布局的 ViewHolder
     * @param t            此展示模板对应的消息
     * @param messageVo    {@link MessageVo}
     * @param position     消息位置
     * @param list         列表
     * @param listener     ViewModel 的点击事件监听器。如果某个子 view 的点击事件需要 ViewModel 处理，可通过此监听器回调。
     */
    protected abstract void bindMessageContentViewHolder(ViewHolder holder, ViewHolder parentHolder, T t, MessageVo messageVo, int position, List<MessageVo> list, IViewProviderListener<MessageVo> listener);

    /**
     * @param holder    ViewHolder
     * @param t         自定义消息
     * @param messageVo {@link MessageVo}
     * @param position  位置
     * @param list      列表数据
     * @param listener  ViewModel 的点击事件监听器。如果某个子 view 的点击事件需要 ViewModel 处理，可通过此监听器回调。
     * @return 点击事件是否被消费
     */
    protected abstract boolean onItemClick(ViewHolder holder, T t, MessageVo messageVo, int position, List<MessageVo> list, IViewProviderListener<MessageVo> listener);

    protected boolean onItemLongClick(ViewHolder holder, T t, MessageVo messageVo, int position, List<MessageVo> list, IViewProviderListener<MessageVo> listener) {
        return false;
    }


    /**
     * 根据消息内容，判断是否为本模板需要展示的消息类型
     *
     * @param messageVo 消息内容
     * @return 本模板是否处理。
     */
    protected abstract boolean isMessageViewType(MessageVo messageVo);

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_message_item, parent, false);
        FrameLayout contentView = rootView.findViewById(R.id.rc_content);
        ViewHolder contentViewHolder = onCreateMessageContentViewHolder(contentView, viewType);
        if (contentViewHolder != null) {
            if (contentView.getChildCount() == 0) {
                contentView.addView(contentViewHolder.itemView);
            }
        }
        return new MessageViewHolder(rootView.getContext(), rootView, contentViewHolder);
    }

    @Override
    public void bindViewHolder(final ViewHolder holder, final MessageVo messageVo, final int position, final List<MessageVo> list, final IViewProviderListener<MessageVo> listener) {
        if (messageVo != null && listener != null) {
//            Message message = messageVo.getMessage();
//            holder.setVisible(R.id.rc_selected, messageVo.isEdit());
//            holder.setVisible(R.id.rc_v_edit, messageVo.isEdit());
//            if (messageVo.isEdit()) {
//                holder.setSelected(R.id.rc_selected, messageVo.isSelected());
//                holder.setOnClickListener(R.id.rc_v_edit, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        listener.onViewClick(MessageClickType.EDIT_CLICK, messageVo);
//                    }
//                });
//            }
//            boolean isSender = messageVo.getMessage().getMessageDirection().equals(Message.MessageDirection.SEND);
            boolean isSender = true;
//            initTime(holder, position, list, message);
//            initUserInfo(holder, messageVo, position, listener, isSender);
            initContent(holder, isSender, messageVo, position, listener, list);
//            initStatus(holder, messageVo, position, listener, message, isSender, list);

            if (holder instanceof MessageViewHolder) {
                bindMessageContentViewHolder(((MessageViewHolder) holder).getMessageContentViewHolder(), holder, (T) messageVo, messageVo, position, list, listener);
            } else {
                Log.e(TAG, "holder is not MessageViewHolder");
            }
//            messageVo.setChange(false);
        } else {
            Log.e(TAG, "messageVo is null");
        }
    }
//
//    private void initStatus(ViewHolder holder, final MessageVo messageVo, final int position, final IViewProviderListener<MessageVo> listener, Message message, boolean isSender, List<MessageVo> list) {
//        if (mConfig.showWarning && !ResendManager.getInstance().needResend(messageVo.getMessage().getMessageId())) {
//            if (isSender && messageVo.getState() == State.ERROR) {
//                holder.setVisible(R.id.rc_warning, true);
//                holder.setOnClickListener(R.id.rc_warning, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        listener.onViewClick(MessageClickType.WARNING_CLICK, messageVo);
//
//                    }
//                });
//            } else {
//                holder.setVisible(R.id.rc_warning, false);
//            }
//        } else {
//            holder.setVisible(R.id.rc_warning, false);
//        }
//        if (mConfig.showProgress) {
//            if (isSender && messageVo.getState() == State.PROGRESS) {
//                holder.setVisible(R.id.rc_progress, true);
//            } else if (isSender && messageVo.getState() == State.ERROR && ResendManager.getInstance().needResend(messageVo.getMessage().getMessageId())) {
//                holder.setVisible(R.id.rc_progress, true);
//            } else {
//                holder.setVisible(R.id.rc_progress, false);
//            }
//        } else {
//            holder.setVisible(R.id.rc_progress, false);
//        }
//
//        initReadStatus(holder, messageVo, position, listener, message, isSender, list);
//    }
//
//    private void initReadStatus(ViewHolder holder, final MessageVo messageVo, int position, final IViewProviderListener<MessageVo> listener, final Message message, boolean isSender, List<MessageVo> list) {
//        //单聊已读状态
//        if (RongConfigCenter.conversationConfig().isShowReadReceipt(message.getConversationType()) &&
//                mConfig.showReadState &&
//                isSender &&
//                message.getSentStatus() == Message.SentStatus.READ) {
//            holder.setVisible(R.id.rc_read_receipt, true);
//        } else {
//            holder.setVisible(R.id.rc_read_receipt, false);
//        }
//        //群聊和讨论组已读状态
//        if (RongConfigCenter.conversationConfig().isShowReadReceiptRequest(message.getConversationType()) &&
//                showReadReceiptRequest(message) &&
//                isSender &&
//                !TextUtils.isEmpty(message.getUId())) {
//
//            boolean isLastSentMessage = true;
//            for (int i = position + 1; i < list.size(); i++) {
//                if (list.get(i).getMessage().getMessageDirection() == Message.MessageDirection.SEND) {
//                    isLastSentMessage = false;
//                    break;
//                }
//            }
//            long serverTime = System.currentTimeMillis() - RongIMClient.getInstance().getDeltaTime();
//            if ((serverTime - message.getSentTime() < RongConfigCenter.conversationConfig().rc_read_receipt_request_interval * 1000)
//                    && isLastSentMessage
//                    && (message.getReadReceiptInfo() == null || !message.getReadReceiptInfo().isReadReceiptMessage())) {
//                holder.setVisible(R.id.rc_read_receipt_request, true);
//                holder.setOnClickListener(R.id.rc_read_receipt_request, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        listener.onViewClick(MessageClickType.READ_RECEIPT_REQUEST_CLICK, messageVo);
//                    }
//                });
//            } else {
//                holder.setVisible(R.id.rc_read_receipt_request, false);
//            }
//
//            if (message.getReadReceiptInfo() != null
//                    && message.getReadReceiptInfo().isReadReceiptMessage()) {
//                if (message.getReadReceiptInfo().getRespondUserIdList() != null) {
//                    holder.setText(R.id.rc_read_receipt_status, message.getReadReceiptInfo().getRespondUserIdList().size() + " " + holder.getContext().getString(R.string.rc_read_receipt_status));
//                } else {
//                    holder.setText(R.id.rc_read_receipt_status, 0 + " " + holder.getContext().getString(R.string.rc_read_receipt_status));
//                }
//                holder.setVisible(R.id.rc_read_receipt_status, true);
//                holder.setOnClickListener(R.id.rc_read_receipt_status, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ConversationClickListener conversationClickListener = RongConfigCenter.conversationConfig().getConversationClickListener();
//                        if (conversationClickListener != null
//                                && conversationClickListener.onReadReceiptStateClick(v.getContext(), messageVo.getMessage())) {
//                            return;
//                        }
//                        listener.onViewClick(MessageClickType.READ_RECEIPT_STATE_CLICK, messageVo);
//
//                    }
//                });
//            } else {
//                holder.setVisible(R.id.rc_read_receipt_status, false);
//            }
//
//        } else {
//            holder.setVisible(R.id.rc_read_receipt_request, false);
//            holder.setVisible(R.id.rc_read_receipt_status, false);
//        }
//
//    }


    private void initContent(final ViewHolder holder, boolean isSender, final MessageVo messageVo, final int position, final IViewProviderListener<MessageVo> listener, final List<MessageVo> list) {
        if (mConfig.showContentBubble) {
            holder.setBackgroundRes(R.id.rc_content, isSender ? R.drawable.rc_ic_bubble_right : R.drawable.rc_ic_bubble_left);
        } else {
            holder.getView(R.id.rc_content).setBackground(null);
        }
        holder.setPadding(R.id.rc_content, 0, 0, 0, 0);

        LinearLayout layout = holder.getView(R.id.rc_layout);
        if (mConfig.centerInHorizontal) {
            layout.setGravity(Gravity.CENTER_HORIZONTAL);
        } else {
            layout.setGravity(isSender ? Gravity.END : Gravity.START);
        }


//        holder.setOnClickListener(R.id.rc_content, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean result = false;
//                /**
//                 * 点击事件分发策略：应用 -> 各消息模板实现类 -> Processor
//                 */
//                if (RongConfigCenter.conversationConfig().getConversationClickListener() != null) {
//                    result = RongConfigCenter.conversationConfig().getConversationClickListener().onMessageClick(holder.getContext(), v, messageVo.getMessage());
//                }
//                if (!result) {
//                    result = onItemClick(((MessageViewHolder) holder).getMessageContentViewHolder(), (T) messageVo.getMessage().getContent(), messageVo, position, list, listener);
//                    if (!result) {
//                        listener.onViewClick(MessageClickType.CONTENT_CLICK, messageVo);
//                    }
//                }
//            }
//        });
//
//        holder.setOnLongClickListener(R.id.rc_content, new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                boolean result = false;
//                if (RongConfigCenter.conversationConfig().getConversationClickListener() != null) {
//                    result = RongConfigCenter.conversationConfig().getConversationClickListener().onMessageLongClick(holder.getContext(), v, messageVo.getMessage());
//                }
//                if (!result) {
//                    result = onItemLongClick(((MessageViewHolder) holder).getMessageContentViewHolder(), (T) messageVo.getMessage().getContent(), messageVo, position, list, listener);
//                    if (!result) {
//                        return listener.onViewLongClick(MessageClickType.CONTENT_LONG_CLICK, messageVo);
//                    }
//                }
//                return false;
//            }
//        });
    }

//    private void initTime(ViewHolder holder, int position, List<MessageVo> data, Message message) {
//        String time = RongDateUtils.getConversationFormatDate(message.getSentTime(), holder.getContext());
//        holder.setText(R.id.rc_time, time);
//        if (position == 0) {
//            holder.setVisible(R.id.rc_time, !(message.getContent() instanceof HistoryDividerMessage));
//        } else {
//            MessageVo pre = data.get(position - 1);
//            if (pre.getMessage() != null && RongDateUtils.isShowChatTime(message.getSentTime(), pre.getMessage().getSentTime(), 180)) {
//                holder.setVisible(R.id.rc_time, true);
//            } else {
//                holder.setVisible(R.id.rc_time, false);
//            }
//        }
//    }
//
//    private void initUserInfo(final ViewHolder holder, final MessageVo messageVo, final int position, final IViewProviderListener<MessageVo> listener, boolean isSender) {
//        if (mConfig.showPortrait) {
//            holder.setVisible(R.id.rc_left_portrait, !isSender);
//            holder.setVisible(R.id.rc_right_portrait, isSender);
//            ImageView view = holder.getView(isSender ? R.id.rc_right_portrait : R.id.rc_left_portrait);
//            if (messageVo.getUserInfo().getPortraitUri() != null) {
//                RongConfigCenter.featureConfig().getKitImageEngine().loadConversationPortrait(holder.getContext(), messageVo.getUserInfo().getPortraitUri().toString(), view);
//            }
//            holder.setOnClickListener(R.id.rc_left_portrait, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (RongConfigCenter.conversationConfig().getConversationClickListener() != null) {
//                        boolean result = RongConfigCenter.conversationConfig().getConversationClickListener().onUserPortraitClick(holder.getContext(), messageVo.getMessage().getConversationType(), messageVo.getUserInfo(), messageVo.getMessage().getTargetId());
//                        if (!result) {
//                            listener.onViewClick(MessageClickType.USER_PORTRAIT_CLICK, messageVo);
//                        }
//                    }
//                }
//            });
//
//            holder.setOnClickListener(R.id.rc_right_portrait, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (RongConfigCenter.conversationConfig().getConversationClickListener() != null) {
//                        boolean result = RongConfigCenter.conversationConfig().getConversationClickListener().onUserPortraitClick(holder.getContext(), messageVo.getMessage().getConversationType(), messageVo.getUserInfo(), messageVo.getMessage().getTargetId());
//                        if (!result) {
//                            listener.onViewClick(MessageClickType.USER_PORTRAIT_CLICK, messageVo);
//                        }
//                    }
//                }
//            });
//
//            holder.setOnLongClickListener(R.id.rc_left_portrait, new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if (RongConfigCenter.conversationConfig().getConversationClickListener() != null) {
//                        boolean result = RongConfigCenter.conversationConfig().getConversationClickListener().onUserPortraitLongClick(holder.getContext(), messageVo.getMessage().getConversationType(), messageVo.getUserInfo(), messageVo.getMessage().getTargetId());
//                        if (!result) {
//                            return listener.onViewLongClick(MessageClickType.USER_PORTRAIT_LONG_CLICK, messageVo);
//                        }
//                    }
//                    return false;
//                }
//            });
//
//            holder.setOnLongClickListener(R.id.rc_right_portrait, new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if (RongConfigCenter.conversationConfig().getConversationClickListener() != null) {
//                        boolean result = RongConfigCenter.conversationConfig().getConversationClickListener().onUserPortraitLongClick(holder.getContext(), messageVo.getMessage().getConversationType(), messageVo.getUserInfo(), messageVo.getMessage().getTargetId());
//                        if (!result) {
//                            return listener.onViewLongClick(MessageClickType.USER_PORTRAIT_LONG_CLICK, messageVo);
//                        }
//                    }
//                    return false;
//                }
//            });
//            if (!RongConfigCenter.conversationConfig().isShowReceiverUserTitle(messageVo.getMessage().getConversationType())) {
//                holder.setVisible(R.id.rc_title, false);
//            } else {
//                if (!isSender) {
//                    holder.setVisible(R.id.rc_title, true);
//                    holder.setText(R.id.rc_title, messageVo.getUserInfo().getName());
//                } else {
//                    holder.setVisible(R.id.rc_title, false);
//                }
//            }
//        } else {
//            holder.setVisible(R.id.rc_left_portrait, false);
//            holder.setVisible(R.id.rc_right_portrait, false);
//            holder.setVisible(R.id.rc_title, false);
//        }
//    }
//
//    /**
//     * @return 群组或讨论组是否展示消息已读回执, 默认只有文本消息展示
//     * 子类可以重写此方法
//     */
//    protected boolean showReadReceiptRequest(Message message) {
//        return message != null && message.getContent() != null &&
//                (message.getContent() instanceof TextMessage);
//    }

    public static class MessageViewHolder extends ViewHolder {
        private ViewHolder mMessageContentViewHolder;

        public MessageViewHolder(Context context, View itemView, ViewHolder messageViewHolder) {
            super(context, itemView);
            mMessageContentViewHolder = messageViewHolder;
        }

        public ViewHolder getMessageContentViewHolder() {
            return mMessageContentViewHolder;
        }

    }


    @Override
    public boolean isItemViewType(MessageVo item) {
        return isMessageViewType(item);
    }

}
