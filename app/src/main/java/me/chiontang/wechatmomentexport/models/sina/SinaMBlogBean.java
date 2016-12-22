package me.chiontang.wechatmomentexport.models.sina;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Berkeley on 10/9/16.
 */
public class SinaMBlogBean implements Serializable {
    //微博Id
    private String id;
    //用户Id
    private String userId;
    //用户级别 大V和普通 1，普通，2，大V
    private int userLevel;
    //userName
    private String userName;
    //高清头像
    private String avatarHd;

    //用户信息
//    private BlogUser mBlogUser;
    //创建时间
//    private Date date;
    private String createDate;
    //发布时间
    private String create;
    //微博发布平台，来自于
    private String source;
    //来源类型
    private String sourceType;
    //格式化源
    private String formatSourceUrl;
    //微博内容
    private String blogContent;
    //图片地址
    private List<PicInfo> picInfos;

    //内容标签
    private ArrayList<MBlogCard> mBlogCards;

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


    //评论
    private String commentsUrl;
    private MediaDataObject mediaDataObject;


    public MediaDataObject getMediaDataObject() {
        return mediaDataObject;
    }

    public void setMediaDataObject(MediaDataObject mediaDataObject) {
        this.mediaDataObject = mediaDataObject;
    }

    public String getCommentsUrl() {
        int count = 50;
        int filter_by_author = 0;
        int page = 1;
        if (mediaDataObject != null) {
            this.commentsUrl = "http://api.weibo.cn/2/comments/build_comments?networktype=wifi&max_id=0&is_show_bulletin=2&uicode=10000002&moduleID=700&trim_user=0&is_reload=1&is_encoded=0&c=android&i=b6c200b&s=419334ea&id="+this.id+"&ua=Meizu-M355__weibo__6.9.0__android__android4.4.4&wm=9848_0009&aid=01Ar354cap2DzMazi4cfDVZdyXGvhrbdVARjcDpn5c2RVXn8M.&v_f=2&v_p=34&from=1069095010&gsid=_2A251AAYtDeTxGeRP6lIR9inEzD-IHXVXlB7lrDV6PUJbkdANLXnwkWqTSjG5xLENZHY6xuUpOdJoPmo9xQ..&lang=zh_CN&lfid=231091&skin=default&count=20&oldwm=9848_0009&sflag=1&luicode=10000010&fetch_level=0";

        } else {
            this.commentsUrl = "http://api.weibo.cn/2/comments/show?trim_level=1&networktype=wifi&with_common_cmt_new=1&uicode=10000002&moduleID=700&c=android&i=b6c200b&s=419334ea&id=" + this.id + "&ua=Meizu-M355__weibo__6.9.0__android__android4.4.4&wm=9848_0009&aid=01Ar354cap2DzMazi4cfDVZdyXGvhrbdVARjcDpn5c2RVXn8M.&v_f=2&v_p=34&from=1069095010&gsid=_2A251AAYtDeTxGeRP6lIR9inEzD-IHXVXlB7lrDV6PUJbkdANLXnwkWqTSjG5xLENZHY6xuUpOdJoPmo9xQ..&lang=zh_CN&lfid=231091&page=" + page + "&skin=default&trim=1&count=" + count + "&oldwm=9848_0009&sflag=1&related_user=0&luicode=10000010&filter_by_author=" + filter_by_author;
        }

        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        int count = 50;
        int filter_by_author = 0;
        int page = 1;
        if (mediaDataObject != null) {
            this.commentsUrl = "http://api.weibo.cn/2/comments/build_comments?networktype=wifi&max_id=0&is_show_bulletin=2&uicode=10000002&moduleID=700&trim_user=0&is_reload=1&is_encoded=0&c=android&i=b6c200b&s=419334ea&id="+id+"&ua=Meizu-M355__weibo__6.9.0__android__android4.4.4&wm=9848_0009&aid=01Ar354cap2DzMazi4cfDVZdyXGvhrbdVARjcDpn5c2RVXn8M.&v_f=2&v_p=34&from=1069095010&gsid=_2A251AAYtDeTxGeRP6lIR9inEzD-IHXVXlB7lrDV6PUJbkdANLXnwkWqTSjG5xLENZHY6xuUpOdJoPmo9xQ..&lang=zh_CN&lfid=231091&skin=default&count=20&oldwm=9848_0009&sflag=1&luicode=10000010&fetch_level=0";

        } else {
            this.commentsUrl = "http://api.weibo.cn/2/comments/show?trim_level=1&networktype=wifi&with_common_cmt_new=1&uicode=10000002&moduleID=700&c=android&i=b6c200b&s=419334ea&id=" + id + "&ua=Meizu-M355__weibo__6.9.0__android__android4.4.4&wm=9848_0009&aid=01Ar354cap2DzMazi4cfDVZdyXGvhrbdVARjcDpn5c2RVXn8M.&v_f=2&v_p=34&from=1069095010&gsid=_2A251AAYtDeTxGeRP6lIR9inEzD-IHXVXlB7lrDV6PUJbkdANLXnwkWqTSjG5xLENZHY6xuUpOdJoPmo9xQ..&lang=zh_CN&lfid=231091&page=" + page + "&skin=default&trim=1&count=" + count + "&oldwm=9848_0009&sflag=1&related_user=0&luicode=10000010&filter_by_author=" + filter_by_author;
        }
    }

    //topicTitle
    private ArrayList<String> topicTitles;

    public String getAvatarHd() {
        return avatarHd;
    }

    public void setAvatarHd(String avatarHd) {
        this.avatarHd = avatarHd;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public ArrayList<MBlogCard> getmBlogCards() {
        return mBlogCards;
    }

    public void setmBlogCards(ArrayList<MBlogCard> mBlogCards) {
        this.mBlogCards = mBlogCards;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBlogContent(String blogContent) {
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
//
//    public BlogUser getmBlogUser() {
//        return mBlogUser;
//    }
//
//    public void setmBlogUser(BlogUser mBlogUser) {
//        this.mBlogUser = mBlogUser;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

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
