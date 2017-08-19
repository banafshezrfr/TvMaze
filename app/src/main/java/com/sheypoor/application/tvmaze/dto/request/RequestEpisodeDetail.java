package com.sheypoor.application.tvmaze.dto.request;

/**
 * Created by Banafshe.Zarefar on 15/08/2017.
 */

public class RequestEpisodeDetail {
    private long episodebynumber;
    private long season;
    private long number;

    public long getEpisodebynumber() {
        return episodebynumber;
    }

    public void setEpisodebynumber(long episodebynumber) {
        this.episodebynumber = episodebynumber;
    }

    public long getSeason() {
        return season;
    }

    public void setSeason(long season) {
        this.season = season;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
