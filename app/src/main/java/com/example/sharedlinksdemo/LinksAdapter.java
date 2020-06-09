package com.example.sharedlinksdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.LinkViewHolder> {
    private List<Link> mLinks;
    private LayoutInflater mInflater;
    private Context mContext;

    public LinksAdapter(Context context, List<Link> links) {
        this.mInflater = LayoutInflater.from(context);
        this.mLinks = links;
        this.mContext = context;
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.links_item, parent, false);
        return new LinkViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        Link link = mLinks.get(position);
        if (link.thumbnailUrl != null){
            Glide.with(mContext).load(link.thumbnailUrl).into(holder.mThumbnail);
        }
        holder.mTitle.setText(link.title);
        holder.mUrl.setText(link.linkUrl);
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    class LinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mThumbnail;
        public final TextView mTitle;
        public final TextView mUrl;
        final LinksAdapter adapter;

        public LinkViewHolder(@NonNull View itemView, LinksAdapter adapter) {
            super(itemView);
            mThumbnail = itemView.findViewById(R.id.link_thumbnail);
            mTitle = itemView.findViewById(R.id.link_title);
            mUrl = itemView.findViewById(R.id.link_url);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            String url = mLinks.get(position).linkUrl;
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            // Find an activity to hand the intent and start that activity.
            if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                v.getContext().startActivity(intent);
            } else {
                Log.d("ImplicitIntents", "Can't handle this intent!");
            }
        }
    }
}
