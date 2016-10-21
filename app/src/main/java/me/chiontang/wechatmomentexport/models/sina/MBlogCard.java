package me.chiontang.wechatmomentexport.models.sina;

import java.io.Serializable;

/**
 * Created by Berkeley on 10/9/16.
 * 内容中标签和对应的url
 *
  "short_url": "http:\/\/t.cn\/RVX5v8a",
 "ori_url": "http:\/\/www.ftchinese.com\/story\/001069723?full=y&ccode=2G162001&url_type=39&object_type=webpage&pos=1",
 "url_title": "日本为什么那么干净？",
 "url_type_pic": "http:\/\/h5.sinaimg.cn\/upload\/2015\/09\/25\/3\/timeline_card_small_web.png",
 "position": 2,
 "url_type": 39,
 "result": true,
 "log": "su=RVX5v8a&mark=&mid=4031831525997671",
 "need_save_obj": 1
 */
public class MBlogCard implements Serializable {

    //MBlogCard
    //引用源 原来网页
    private String oriUrl;
    //对应的标题
    private String urlTitle;
    //url type pic
    private String urlTypePic;
    //url type
    private String urlType;

    //点击进入链接源网页 微博短连接
    private String shortUrl;
//    //hide 可以获取
//    private int hide;
////
////    //点击跳转源 可以获取
//    private String actionlog;
//
//    private String log;
//    private int need_save_obj;
//    private int position;
//    private boolean result;





    public String getOriUrl() {
        return oriUrl;
    }

    public void setOriUrl(String oriUrl) {
        this.oriUrl = oriUrl;
    }

    public String getUrlTitle() {
        return urlTitle;
    }

    public void setUrlTitle(String urlTitle) {
        this.urlTitle = urlTitle;
    }

    public String getUrlTypePic() {
        return urlTypePic;
    }

    public void setUrlTypePic(String urlTypePic) {
        this.urlTypePic = urlTypePic;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
