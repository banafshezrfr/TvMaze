package com.sheypoor.application.tvmaze.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sheypoor.application.tvmaze.R;
import com.sheypoor.application.tvmaze.dto.response.episodeList.Episode;
import com.sheypoor.application.tvmaze.event.Events;
import com.sheypoor.application.tvmaze.util.ConstantEvent;
import com.sheypoor.application.tvmaze.util.RxBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterEpisodeList extends RecyclerView.Adapter<AdapterEpisodeList.MyViewHolder> {
    private List<Episode> episodeList;
    private int selectedItem = -1;
    private Context context;

    public AdapterEpisodeList(List<Episode> list, Context context) {
        this.episodeList = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_episode, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Episode episode = episodeList.get(position);
        holder.tvTitle.setText(String.valueOf(episode.getName()));
        if (null != episode.getImage() && !episode.getImage().getMedium().isEmpty()) {
            String url = episode.getImage().getOriginal();
            Glide.with(context)
                    .load(url)
                    .placeholder(R.mipmap.ic_cloud_download_grey600)
                    .error(R.mipmap.ic_error_red)
                    .override(800, 800)
                    .animate(android.R.anim.fade_in)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(holder.imLogo);
        }

        holder.rpEpisode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RxBus.getInstance().send(new Events.EventEpisodeDetail(episode.getNumber(), episode.getSeason(), ConstantEvent.EVENT_MESSAGE_EPISODE_DETAIL, episode.getImage().getOriginal(), episode.getUrl()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    public int getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(int selectedItem) {
        if (getSelectedItem() == selectedItem) {
            return;
        }
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.im_logo)
        ImageView imLogo;
        @BindView(R.id.cv_episode)
        CardView cvEpisode;
        @BindView(R.id.rp_episode)
        MaterialRippleLayout rpEpisode;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
