package me.chiontang.wechatmomentexport.models.sina;

/**
 * Created by Berkeley on 10/9/16.
 */
public class PicInfo {
    //PicInfo
    //图片地址
    private String picUrl;
    //gif 对应视频
    private String videoUrl;


    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
