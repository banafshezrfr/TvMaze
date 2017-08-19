package com.sheypoor.application.tvmaze.event;


/**
 * Created by banafshe.zarefar on 2016-09-05.
 */
public class Events {
    private Events() {
    }

    public static class EventEpisodeDetail {
        public final Long number;
        public final Long season;
        public final String message;
        public final String imgUrl;
        public final String url;

        public EventEpisodeDetail(Long number, Long season, String message, String imgUrl, String url) {
            this.number = number;
            this.season = season;
            this.message = message;
            this.imgUrl = imgUrl;
            this.url = url;
        }

        public Long getNumber() {
            return number;
        }

        public Long getSeason() {
            return season;
        }

        public String getMessage() {
            return message;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public String getUrl() {
            return url;
        }
    }

    public static class EventLoadingView {
        public final boolean show;

        public EventLoadingView(boolean show) {
            this.show = show;
        }

        public boolean isShow() {
            return show;
        }
    }
}
