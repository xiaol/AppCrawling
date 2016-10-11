package me.chiontang.wechatmomentexport.models.sina;

import android.text.SpannableStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Berkeley on 10/9/16.
 */
public class SinaHotBean implements Serializable {
    //微博Id
    private String id;
    //用户Id
    private String userId;
    //用户级别 大V和普通 1，普通，2，大V
    private int userLevel;
    //userName
    private String userName;

    //用户信息
    private BlogUser mBlogUser;
    //创建时间
    private Date date;
    //发布时间
    private String create;
    //微博发布平台，来自于
    private String source;
    //来源类型
    private String sourceType;
    //格式化源
    private String formatSourceUrl;
    //微博内容
    private SpannableStringBuilder blogContent;
    //图片地址
    private List<PicInfo> picInfos;

    //内容标签
    private MBlogCard mBlogCard;

    //微博标签
    private ArrayList<MblogTag> mblogTags;
    //点赞
    private int attitudesCount;
    //评论
    private int commentsCount;
    //转发
    private int repostsCount;
    //title 标记是否是热门，或者广告
    private String title;
    //topicTitle
    private ArrayList<String> topicTitles;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBlogContent(SpannableStringBuilder blogContent) {
        this.blogContent = blogContent;
    }

    public String getFormatSourceUrl() {
        return formatSourceUrl;
    }

    public void setFormatSourceUrl(String formatSourceUrl) {
        this.formatSourceUrl = formatSourceUrl;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getTopicTitles() {
        return topicTitles;
    }

    public void setTopicTitles(ArrayList<String> topicTitles) {
        this.topicTitles = topicTitles;
    }

    public BlogUser getmBlogUser() {
        return mBlogUser;
    }

    public void setmBlogUser(BlogUser mBlogUser) {
        this.mBlogUser = mBlogUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<PicInfo> getPicInfos() {
        return picInfos;
    }

    public void setPicInfos(List<PicInfo> picInfos) {
        this.picInfos = picInfos;
    }

    public MBlogCard getmBlogCard() {
        return mBlogCard;
    }

    public void setmBlogCard(MBlogCard mBlogCard) {
        this.mBlogCard = mBlogCard;
    }

    public ArrayList<MblogTag> getMblogTags() {
        return mblogTags;
    }

    public void setMblogTags(ArrayList<MblogTag> mblogTags) {
        this.mblogTags = mblogTags;
    }

    public int getAttitudesCount() {
        return attitudesCount;
    }

    public void setAttitudesCount(int attitudesCount) {
        this.attitudesCount = attitudesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getRepostsCount() {
        return repostsCount;
    }

    public void setRepostsCount(int repostsCount) {
        this.repostsCount = repostsCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
