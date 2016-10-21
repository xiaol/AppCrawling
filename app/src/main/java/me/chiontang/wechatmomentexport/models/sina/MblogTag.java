package me.chiontang.wechatmomentexport.models.sina;

import java.io.Serializable;

/**
 * Created by Berkeley on 10/9/16.
 * 微博底部标签和图片
 *
 "tag_name": "陈伟霆",
 "oid": "1022:1008084a94544ee846845600cf5d23845dc2f3",
 "tag_type": 2,
 "tag_hidden": 0,
 "tag_scheme": "sinaweibo:\/\/pageinfo?containerid=1008084a94544ee846845600cf5d23845dc2f3&pageid=1008084a94544ee846845600cf5d23845dc2f3&extparam=%E9%99%88%E4%BC%9F%E9%9C%86",
 "url_type_pic": null

 都能解析到
 */
public class MblogTag implements Serializable {

//    private String oid;
    //url MblogTag微博底部标签和图片
    private String tagName;
    //url tag type
    private int tagType;
    //tag_type_pic
    private String tagTypePic;

//    private int tag_hidden;
//    private String tag_scheme;
//    private String url_type_pic;




    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTagType() {
        return tagType;
    }

    public void setTagType(int tagType) {
        this.tagType = tagType;
    }

    public String getTagTypePic() {
        return tagTypePic;
    }

    public void setTagTypePic(String tagTypePic) {
        this.tagTypePic = tagTypePic;
    }
}
