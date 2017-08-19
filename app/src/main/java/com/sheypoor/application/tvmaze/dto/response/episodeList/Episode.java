package com.sheypoor.application.tvmaze.dto.response.episodeList;

/**
 * Created by Banafshe.Zarefar on 15/08/2017.
 */


public class Episode {

    private Long id;
    private String url;
    private String name;
    private Long season;
    private Long number;
    private String airdate;
    private String airtime;
    private String airstamp;
    private Long runtime;
    private Image image;
    private String summary;
    private Link links;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSeason() {
        return season;
    }

    public void setSeason(Long season) {
        this.season = season;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getAirdate() {
        return airdate;
    }

    public void setAirdate(String airdate) {
        this.airdate = airdate;
    }

    public String getAirtime() {
        return airtime;
    }

    public void setAirtime(String airtime) {
        this.airtime = airtime;
    }

    public String getAirstamp() {
        return airstamp;
    }

    public void setAirstamp(String airstamp) {
        this.airstamp = airstamp;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }

}
