package com.datechnologies.androidtest.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.datechnologies.androidtest.R;
import com.datechnologies.androidtest.api.ChatLogMessage;
import com.datechnologies.androidtest.api.ChatLogMessageModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * A recycler view adapter used to display chat log messages in {@link ChatActivity}.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    //==============================================================================================
    // Class Properties
    //==============================================================================================

    private List<ChatLogMessage> chatLogMessageList;


    //==============================================================================================
    // Constructor
    //==============================================================================================

    public ChatAdapter() {
        chatLogMessageList = new ArrayList<>();
    }

    //==============================================================================================
    // Class Instance Methods
    //==============================================================================================

    public void setChatLogMessageList(List<ChatLogMessage> chatLogMessageList) {
        this.chatLogMessageList = chatLogMessageList;
        notifyDataSetChanged();
    }

    //==============================================================================================
    // RecyclerView.Adapter Methods
    //==============================================================================================

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_chat, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder viewHolder, int position) {
        ChatLogMessage chatLogMessage = chatLogMessageList.get(position);

        viewHolder.messageText.setText(chatLogMessage.getMessage());
        viewHolder.personNameText.setText(chatLogMessage.getName());

        Picasso.get().load(chatLogMessage.getAvatarUrl()).transform(new CropCircleTransformation()).into(viewHolder.avatarImage);
    }

    @Override
    public int getItemCount() {
        return chatLogMessageList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImage;
        TextView messageText;
        TextView personNameText;

        public ChatViewHolder(View view) {
            super(view);
            avatarImage = (ImageView) view.findViewById(R.id.avatarImageView);
            messageText = (TextView) view.findViewById(R.id.messageTextView);
            personNameText = (TextView) view.findViewById(R.id.personNameTextView);
        }
    }

}
