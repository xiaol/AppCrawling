package me.chiontang.wechatmomentexport.models.sina;

import java.io.Serializable;

/**
 * Created by Berkeley on 10/9/16.
 *
 */
public class MBlogTopic implements Serializable{

    private String topicTitle;
    private String topicUrl;

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getTopicUrl() {
        return topicUrl;
    }

    public void setTopicUrl(String topicUrl) {
        this.topicUrl = topicUrl;
    }
}
