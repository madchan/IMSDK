package com.madchan.imsdk.comp.ui.rongcloud.provider;

import android.util.LayoutDirection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.core.text.TextUtilsCompat;

import com.madchan.imsdk.comp.ui.R;
import com.madchan.imsdk.comp.ui.rongcloud.adapter.IViewProviderListener;
import com.madchan.imsdk.comp.ui.rongcloud.adapter.ViewHolder;
import com.madchan.imsdk.lib.objects.bean.dto.MessageDTO;
import com.madchan.imsdk.lib.objects.bean.vo.MessageVO;

import java.util.List;
import java.util.Locale;

public class TextMessageItemProvider extends BaseMessageItemProvider<MessageVO> {

    public TextMessageItemProvider() {
        mConfig.showReadState = true;
    }

    @Override
    protected ViewHolder onCreateMessageContentViewHolder(ViewGroup parent, int viewType) {
        View textView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_text_message_item, parent, false);
        return new ViewHolder(parent.getContext(), textView);
    }

    @Override
    protected void bindMessageContentViewHolder(final ViewHolder holder, ViewHolder parentHolder, MessageVO message, final MessageVO messageVo, int position, List<MessageVO> list, IViewProviderListener<MessageVO> listener) {
        final TextView view = holder.getView(R.id.rc_text);

        if (TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault()) == LayoutDirection.RTL) {
            view.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        }
        view.setTag(messageVo.getMessageId());
//        if (messageVo.getContentSpannable() == null) {
//            SpannableStringBuilder spannable = TextViewUtils.getSpannable(message.getContent(), new TextViewUtils.RegularCallBack() {
//                @Override
//                public void finish(SpannableStringBuilder spannable) {
//                    messageVo.setContentSpannable(spannable);
//                    if (view.getTag().equals(messageVo.getMessageId())) {
//                        view.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                view.setText(messageVo.getContentSpannable());
//                            }
//                        });
//                    }
//                }
//            });
//            messageVo.setContentSpannable(spannable);
//        }
        view.setText(messageVo.getContent());
//        view.setMovementMethod(new LinkTextViewMovementMethod(new ILinkClickListener() {
//            @Override
//            public boolean onLinkClick(String link) {
//                boolean result = false;
//                if (RongConfigCenter.conversationConfig().getConversationClickListener() != null) {
//                    result = RongConfigCenter.conversationConfig().getConversationClickListener().onMessageLinkClick(holder.getContext(), link, messageVo.getMessage());
//                }
//                if (result)
//                    return true;
//                String str = link.toLowerCase();
//                if (str.startsWith("http") || str.startsWith("https")) {
//                    RouteUtils.routeToWebActivity(view.getContext(), link);
//                    result = true;
//                }
//
//                return result;
//            }
//        }));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewParent parent = view.getParent();
                if (parent instanceof View) {
                    ((View) parent).performClick();
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ViewParent parent = view.getParent();
                if (parent instanceof View) {
                    return ((View) parent).performLongClick();
                }
                return false;
            }
        });
    }

    @Override
    protected boolean onItemClick(ViewHolder holder, MessageVO message, MessageVO messageVo, int position, List<MessageVO> list, IViewProviderListener<MessageVO> listener) {
        return false;
    }


    @Override
    protected boolean isMessageViewType(MessageVO messageVo) {
//        return messageContent instanceof TextMessage && !messageContent.isDestruct();
        return MessageDTO.Message.MessageType.MESSAGE_TYPE_TEXT_VALUE == messageVo.getMessageType();
    }

//    @Override
//    public Spannable getSummarySpannable(Context context, TextMessage message) {
//        if (message != null && !TextUtils.isEmpty(message.getContent())) {
//            String content = message.getContent();
//            content = content.replace("\n", " ");
//            if (content.length() > 100) {
//                content = content.substring(0, 100);
//            }
//            return new SpannableString(AndroidEmoji.ensure(content));
//        } else {
//            return new SpannableString("");
//        }
//    }
}
