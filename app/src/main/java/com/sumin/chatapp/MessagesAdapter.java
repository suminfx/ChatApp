package com.sumin.chatapp;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {

    private static final int TYPE_MY_MESSAGE = 0;
    private static final int TYPE_OTHER_MESSAGE = 1;

    private List<Message> messages;
    private Context context;

    public MessagesAdapter(Context context) {
        messages = new ArrayList<>();
        this.context = context;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == TYPE_MY_MESSAGE) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_my_message, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_other_message, viewGroup, false);
        }
        return new MessagesViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        String author = message.getAuthor();
        if (author != null && author.equals(PreferenceManager.getDefaultSharedPreferences(context).getString("author", "Anonim"))) {
            return TYPE_MY_MESSAGE;
        } else {
            return TYPE_OTHER_MESSAGE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder messagesViewHolder, int i) {
        Message message = messages.get(i);
        String author = message.getAuthor();
        String textOfMessage = message.getTextOfMessage();
        String urlToMessage = message.getImageUrl();
        if (urlToMessage == null || urlToMessage.isEmpty()) {
            messagesViewHolder.imageViewImage.setVisibility(View.GONE);
        } else {
            messagesViewHolder.imageViewImage.setVisibility(View.VISIBLE);
        }
        messagesViewHolder.textViewAuthor.setText(author);
        if (textOfMessage != null && !textOfMessage.isEmpty()) {
            messagesViewHolder.textViewTextOfMessage.setVisibility(View.VISIBLE);
            messagesViewHolder.textViewTextOfMessage.setText(textOfMessage);
        } else {
            messagesViewHolder.textViewTextOfMessage.setVisibility(View.GONE);
        }
        if (urlToMessage != null && !urlToMessage.isEmpty()) {
            Picasso.get().load(urlToMessage).into(messagesViewHolder.imageViewImage);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessagesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor;
        private TextView textViewTextOfMessage;
        private ImageView imageViewImage;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewTextOfMessage = itemView.findViewById(R.id.textViewTextOfMessage);
            imageViewImage = itemView.findViewById(R.id.imageViewImage);
        }
    }
}
