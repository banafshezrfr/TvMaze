package com.sheypoor.application.tvmaze.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sheypoor.application.tvmaze.R;
import com.sheypoor.application.tvmaze.core.ActivityBase;
import com.sheypoor.application.tvmaze.event.Events;
import com.sheypoor.application.tvmaze.fragment.FragmentEpisodeDetail;
import com.sheypoor.application.tvmaze.fragment.FragmentEpisodeList;
import com.sheypoor.application.tvmaze.util.ConstantEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityMovie extends ActivityBase {
    FragmentManager fm;
    FragmentTransaction ft;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.im_episode_pic_det)
    ImageView imFirstPic;
    String url;
    @BindView(R.id.loading_view_background)
    LinearLayout linProgress;
    private FragmentEpisodeList fragmentEpisodeList;
    private FragmentEpisodeDetail fragmentEpisodeDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        showListFragment();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBrowser();
            }
        });
    }

    private void openBrowser() {
        final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
        startActivity(intent);
    }

    private void showListFragment() {
        appBarLayout.setExpanded(false);
        appBarLayout.setEnabled(false);
        fab.setVisibility(View.GONE);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        fragmentEpisodeList = new FragmentEpisodeList();
        ft.replace(R.id.frame_episodes, fragmentEpisodeList);
        ft.commit();
    }

    private void showDetailFragment(Long number, Long season) {
        appBarLayout.setExpanded(true);
        appBarLayout.setEnabled(true);
        fab.setVisibility(View.VISIBLE);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        fragmentEpisodeDetail = new FragmentEpisodeDetail(number, season);
        ft.replace(R.id.frame_episodes, fragmentEpisodeDetail);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appBarLayout.setExpanded(false);
        appBarLayout.setEnabled(false);
        fab.setVisibility(View.GONE);
    }

    @Override
    protected void handlerBus(Object object) {
        super.handlerBus(object);
        if (object instanceof Events.EventEpisodeDetail && ((Events.EventEpisodeDetail) object).getMessage().equalsIgnoreCase(ConstantEvent.EVENT_MESSAGE_EPISODE_DETAIL)) {
            showDetailFragment(((Events.EventEpisodeDetail) object).getNumber(), ((Events.EventEpisodeDetail) object).getSeason());
            Glide.with(ActivityMovie.this)
                    .load(((Events.EventEpisodeDetail) object).getImgUrl())
                    .placeholder(R.mipmap.ic_cloud_download_grey600)
                    .error(R.mipmap.ic_error_red)
                    .animate(android.R.anim.fade_in)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(imFirstPic);
            url = ((Events.EventEpisodeDetail) object).getUrl();
        }
        if (object instanceof Events.EventLoadingView && ((Events.EventLoadingView) object).isShow()) {
            linProgress.setVisibility(View.VISIBLE);
        } else {
            linProgress.setVisibility(View.GONE);
        }
    }
}
