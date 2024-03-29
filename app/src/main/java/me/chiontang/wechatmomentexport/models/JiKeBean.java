package me.chiontang.wechatmomentexport.models;

import java.util.ArrayList;

/**
 * Created by wudi on 16/4/22.
 */
public class JiKeBean {


    /**
     * 文章标题
     */

    String app_name;
    /**
     * 文章简介 可能为空
     */
    String summary;

    /**
     * 发布时间
     */
    long published_date;

    /**
     * 文章详情 html 外联，有可能没有
     */
    String link;

    /**
     * 图片列表
     */
    ArrayList<String> pictureUrl;

    @Override
    public String toString() {
        return "JiKeBean{" +
                "app_name='" + app_name + '\'' +
                ", summary='" + summary + '\'' +
                ", published_date=" + published_date +
                ", link='" + link + '\'' +
                ", pictureUrl=" + pictureUrl +
                '}';
    }

    public ArrayList<String> getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(ArrayList<String> pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public long getPublished_date() {
        return published_date;
    }

    public void setPublished_date(long published_date) {
        this.published_date = published_date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }






}
