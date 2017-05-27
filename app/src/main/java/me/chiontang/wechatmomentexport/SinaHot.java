package me.chiontang.wechatmomentexport;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import me.chiontang.wechatmomentexport.models.sina.BlogUser;
import me.chiontang.wechatmomentexport.models.sina.MBlogCard;
import me.chiontang.wechatmomentexport.models.sina.MblogTag;
import me.chiontang.wechatmomentexport.models.sina.MediaDataObject;
import me.chiontang.wechatmomentexport.models.sina.PicInfo;
import me.chiontang.wechatmomentexport.models.sina.SinaMBlogBean;
import me.chiontang.wechatmomentexport.utils.JSONUtils;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Created by Berkeley on 9/23/16.
 * 解析微博
 * 版本 6.9.0
 */
public class SinaHot implements IXposedHookLoadPackage {
    public static final String PACKAGE_NAME = "com.sina.weibo";
    Context appContext = null;
    RequestQueue mQueue;
    ArrayList<SinaMBlogBean> mBlogs = new ArrayList<>();


    /**
     * 需要上传的最终集合
     */
//    public LinkedHashMap<Long, ModelBean> detailResultMap = new LinkedHashMap<Long, ModelBean>();

    //com.sina.weibo.MainTabActivity
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals(PACKAGE_NAME))
            return;

        XposedBridge.log("handleLoadPackage ===== " + lpparam.packageName);
        Config.enabled = true;

        findAndHookMethod("com.sina.weibo.MainTabActivity", lpparam.classLoader, "onCreate",
                Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if (appContext != null) {
                            return;
                        }
                        XposedBridge.log("SinaHot hooked.");
                        appContext = ((Activity) param.thisObject).getApplicationContext();
                        mQueue = Volley.newRequestQueue(appContext);
                        hookMethods(lpparam);
                    }
                });


    }


    private void hookMethods(final XC_LoadPackage.LoadPackageParam lpparam) {

        findAndHookMethod("com.sina.weibo.page.DiscoverActivity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                log("com.sina.weibo.page.DiscoverActivity::::::::::::DiscoverActivity");

                findAndHookMethod("com.sina.weibo.stream.b.d", lpparam.classLoader, "a", List.class, List.class, boolean.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        log("com.sina.weibo.page.view.a======a");
                        final Class StatusClass = XposedHelpers.findClass("com.sina.weibo.models.Status", lpparam.classLoader);
                        final Class TrendClass = XposedHelpers.findClass("com.sina.weibo.models.Trend", lpparam.classLoader);

                        final Class StatusAnnotationsClass = XposedHelpers.findClass("com.sina.weibo.models.StatusAnnotations", lpparam.classLoader);
                        final Class CommentSummaryClass = XposedHelpers.findClass("com.sina.weibo.models.CommentSummary", lpparam.classLoader);
                        final Class MBlogExtendPageClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogExtendPage", lpparam.classLoader);
                        final Class ForwardSummaryClass = XposedHelpers.findClass("com.sina.weibo.models.ForwardSummary", lpparam.classLoader);
                        final Class LongTextClass = XposedHelpers.findClass("com.sina.weibo.models.LongText", lpparam.classLoader);
                        final Class ContinueTagClass = XposedHelpers.findClass("com.sina.weibo.models.ContinueTag", lpparam.classLoader);
                        final Class PicInfoClass = XposedHelpers.findClass("com.sina.weibo.models.PicInfo", lpparam.classLoader);
                        final Class StatusCommentClass = XposedHelpers.findClass("com.sina.weibo.models.StatusComment", lpparam.classLoader);
                        final Class StatusComplaintClass = XposedHelpers.findClass("com.sina.weibo.models.StatusComplaint", lpparam.classLoader);
                        final Class MBlogKeywordClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogKeyword", lpparam.classLoader);
                        final Class JsonButtonClass = XposedHelpers.findClass("com.sina.weibo.card.model.JsonButton", lpparam.classLoader);
                        final Class MblogCardInfoClass = XposedHelpers.findClass("com.sina.weibo.card.model.MblogCardInfo", lpparam.classLoader);
                        final Class MBlogTagClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogTag", lpparam.classLoader);
                        final Class MBlogTitleClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogTitle", lpparam.classLoader);
                        final Class MblogCardClass = XposedHelpers.findClass("com.sina.weibo.models.MblogCard", lpparam.classLoader);
                        final Class JsonUserInfoClass = XposedHelpers.findClass("com.sina.weibo.models.JsonUserInfo", lpparam.classLoader);
                        final Class MblogTopicClass = XposedHelpers.findClass("com.sina.weibo.models.MblogTopic", lpparam.classLoader);
                        final Class MBlogMultiMediaClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogMultiMedia", lpparam.classLoader);
//                        final Class MBlogTitleInfoClass = XposedHelpers.findClass("com.sina.weibo.models.MBlogTitleInfo", lpparam.classLoader);
                        final Class MediaDataObjectClass = XposedHelpers.findClass("com.sina.weibo.card.model.MediaDataObject", lpparam.classLoader);

                        final List listMessage = (List) param.args[0];
                        log("com.sina.weibo.stream.b.d======b" + listMessage.size() + "Object:::::::::" + listMessage.toString());

                        for (int i = 0; i < listMessage.size(); i++) {
                            SinaMBlogBean sinaMBlog = new SinaMBlogBean();
                            Method getMblogTitle = StatusClass.getMethod("getMblogTitle");
                            Object mblogTitle = (Object) getMblogTitle.invoke(listMessage.get(i), new Object[]{});
                            log("getMblogTitle:::::::::" + mblogTitle);

                            if (null != mblogTitle) {

                                Method getTitle = MBlogTitleClass.getMethod("getTitle");
                                String title = (String) getTitle.invoke(mblogTitle, new Object[]{});
                                log("getTitle:::::::::" + title);
                                if (title != null)
                                    sinaMBlog.setTitle(title);

                            }
                            if (sinaMBlog.getTitle() != null && sinaMBlog.getTitle().equals("广告")) {
                                continue;
                            }

                            /*======================用户信息=================================*/

                            Method getUser = StatusClass.getMethod("getUser");
                            Object user = (Object) getUser.invoke(listMessage.get(i), new Object[]{});
                            log("getUser:::::::::" + user);
                            if (null != user) {
                                BlogUser blogUser = new BlogUser();
                                //高清图片
                                Method getAvatarHd = JsonUserInfoClass.getMethod("getAvatarHd");
                                String avatarHd = (String) getAvatarHd.invoke(user, new Object[]{});
                                log("getAvatarHd:::::::::" + avatarHd);
                                blogUser.setAvatarHd(avatarHd);
                                sinaMBlog.setAvatarHd(avatarHd);

                                //微博名
                                Method getName = JsonUserInfoClass.getMethod("getName");
                                String name = (String) getName.invoke(user, new Object[]{});
                                log("getName:::::::::" + name);
                                blogUser.setName(name);


//                                sinaMBlog.setmBlogUser(blogUser);


                            }
                            /*==========================SinaMBlog实体类==============================*/

                            Method getId = StatusClass.getMethod("getId");
                            String id = (String) getId.invoke(listMessage.get(i), new Object[]{});
                            log("getId:::::::::" + id);
                            sinaMBlog.setId(id);
//                            sinaMBlog.setCommentsUrl(id);
                            log("commentsList:" + sinaMBlog.getCommentsUrl());

//                            Method getCreatedDate = StatusClass.getMethod("getCreatedDate");
//                            Date createdDate = (Date) getCreatedDate.invoke(listMessage.get(i), new Object[]{});
//                            log("getCreatedDate:::::::::" + createdDate);
//                            sinaMBlog.setDate(createdDate);

                            //微博距离发布时间

                            Method getCreatedDateStr = StatusClass.getMethod("getCreatedDateStr");
                            String createdDateStr = (String) getCreatedDateStr.invoke(listMessage.get(i), new Object[]{});
                            log("getCreatedDateStr:::::::::" + createdDateStr);
                            sinaMBlog.setCreateDate(createdDateStr);

                            //微博距离发布时间
                            Method getTimestampText = StatusClass.getMethod("getTimestampText");
                            String timestampText = (String) getTimestampText.invoke(listMessage.get(i), new Object[]{});
                            log("getTimestampText:::::::::" + timestampText);
                            sinaMBlog.setCreate(timestampText);


                            Method getSource = StatusClass.getMethod("getSource");
                            String source = (String) getSource.invoke(listMessage.get(i), new Object[]{});
                            log("getSource:::::::::" + source);
                            sinaMBlog.setSource(source);

                            Method getSource_type = StatusClass.getMethod("getSource_type");
                            int source_type = (int) getSource_type.invoke(listMessage.get(i), new Object[]{});
                            log("getSource_type:::::::::" + source_type);

                            Method getFormatSourceUrl = StatusClass.getMethod("getFormatSourceUrl");
                            String formatSourceUrl = (String) getFormatSourceUrl.invoke(listMessage.get(i), new Object[]{});
                            log("getFormatSourceUrl:::::::::" + formatSourceUrl);
                            sinaMBlog.setFormatSourceUrl(formatSourceUrl);

                            //内容
                            Method getMblogContent = StatusClass.getMethod("getMblogContent");
                            SpannableStringBuilder mblogContent = (SpannableStringBuilder) getMblogContent.invoke(listMessage.get(i), new Object[]{});
                            log("getMblogContent:::::::::" + mblogContent.toString());
                            sinaMBlog.setBlogContent(mblogContent.toString());


                            //点赞数量
                            Method getAttitudes_status = StatusClass.getMethod("getAttitudes_status");
                            int attitudes_status = (int) getAttitudes_status.invoke(listMessage.get(i), new Object[]{});
                            log("getAttitudes_status:::::::::" + attitudes_status);
                            sinaMBlog.setAttitudesCount(attitudes_status);

                            //转发数量
                            Method getReposts_count = StatusClass.getMethod("getReposts_count");
                            int reposts_count = (int) getReposts_count.invoke(listMessage.get(i), new Object[]{});
                            log("getReposts_count:::::::::" + reposts_count);
                            sinaMBlog.setRepostsCount(reposts_count);

                            //评论数量
                            Method getComments_count = StatusClass.getMethod("getComments_count");
                            int ommencts_count = (int) getComments_count.invoke(listMessage.get(i), new Object[]{});
                            log("getComments_count:::::::::" + ommencts_count);
                            sinaMBlog.setRepostsCount(ommencts_count);


                            Method getTopicList = StatusClass.getMethod("getTopicList");
                            List topicList = (List) getTopicList.invoke(listMessage.get(i), new Object[]{});
                            log("getTopicList:::::::::" + topicList);
                            if (null != topicList) {
                                ArrayList<String> topicLists = new ArrayList<String>();
                                for (int j = 0; j < topicList.size(); j++) {

                                    Method getTopic_title = MblogTopicClass.getMethod("getTopic_title");
                                    String topic_title = (String) getTopic_title.invoke(topicList.get(j), new Object[]{});
                                    log("getTopic_title:::::::::" + topic_title);
                                    topicLists.add(topic_title);

                                }
                                sinaMBlog.setTopicTitles(topicLists);
                            }


                            Method getUserId = StatusClass.getMethod("getUserId");
                            String userId = (String) getUserId.invoke(listMessage.get(i), new Object[]{});
                            log("getUserId:::::::::" + userId);
                            sinaMBlog.setUserId(userId);


                            Method getUserLevel = StatusClass.getMethod("getUserLevel");
                            int userLevel = (int) getUserLevel.invoke(listMessage.get(i), new Object[]{});
                            log("getUserLevel:::::::::" + userLevel);
                            sinaMBlog.setUserLevel(userLevel);

                            Method getUserScreenName = StatusClass.getMethod("getUserScreenName");
                            String userScreenName = (String) getUserScreenName.invoke(listMessage.get(i), new Object[]{});
                            log("getUserScreenName:::::::::" + userScreenName);
                            sinaMBlog.setUserName(userScreenName);



                            /*============================PicInfo==============================*/

                            Method getPicInfos = StatusClass.getMethod("getPicInfos");
                            List picInfos = (List) getPicInfos.invoke(listMessage.get(i), new Object[]{});
                            log("getPicInfos:::::::::" + picInfos);

                            if (null != picInfos) {
                                ArrayList<PicInfo> picLists = new ArrayList<PicInfo>();
                                for (int j = 0; j < picInfos.size(); j++) {
                                    PicInfo picInfo = new PicInfo();
                                    Method getLargeUrl = PicInfoClass.getMethod("getLargeUrl");
                                    String largeUrl = (String) getLargeUrl.invoke(picInfos.get(j), new Object[]{});
                                    log("getLargeUrl:::::::::" + largeUrl);
                                    picInfo.setPicUrl(largeUrl);

                                    Method getVideo = PicInfoClass.getMethod("getVideo");
                                    String video = (String) getVideo.invoke(picInfos.get(j), new Object[]{});
                                    log("getVideo:::::::::" + video);
                                    picInfo.setVideoUrl(video);

                                    picLists.add(picInfo);


                                }
                                sinaMBlog.setPicInfos(picLists);
                            }
                            /*================================MblogTag====================================*/

                            Method getMBlogTag = StatusClass.getMethod("getMBlogTag");
                            List mBlogTag = (List) getMBlogTag.invoke(listMessage.get(i), new Object[]{});
                            log("getMBlogTag:::::::::" + mBlogTag);
                            if (null != mBlogTag) {
                                ArrayList<MblogTag> mblogTags = new ArrayList<MblogTag>();
                                for (int j = 0; j < mBlogTag.size(); j++) {
                                    MblogTag mblogTag = new MblogTag();


                                    Method getName = MBlogTagClass.getMethod("getName");
                                    String name = (String) getName.invoke(mBlogTag.get(j), new Object[]{});
                                    log("getName:::::::::" + name);
                                    mblogTag.setTagName(name);


                                    Method getUrl_type_pic = MBlogTagClass.getMethod("getUrl_type_pic");
                                    String url_type_pic = (String) getUrl_type_pic.invoke(mBlogTag.get(j), new Object[]{});
                                    log("getUrl_type_pic:::::::::" + url_type_pic);
                                    mblogTag.setTagTypePic(url_type_pic);


                                    Method getType = MBlogTagClass.getMethod("getType");
                                    int type = (int) getType.invoke(mBlogTag.get(j), new Object[]{});
                                    log("getType:::::::::" + type);
                                    mblogTag.setTagType(type);
                                    mblogTags.add(mblogTag);

                                }
                                sinaMBlog.setMblogTags(mblogTags);

                            }


                            Method getMultiMedia = StatusClass.getMethod("getMultiMedia");
                            List multiMedia = (List) getMultiMedia.invoke(listMessage.get(i), new Object[]{});
                            log("getMultiMedia:::::::::" + multiMedia);


                            /*===========================BlogCard==================================*/
                            Method getUrlList = StatusClass.getMethod("getUrlList");
                            List urlList = (List) getUrlList.invoke(listMessage.get(i), new Object[]{});
                            log("getUrlList:::::::::" + urlList);

                            if (null != urlList) {
                                ArrayList<MBlogCard> mBlogCards = new ArrayList<MBlogCard>();

                                for (int j = 0; j < urlList.size(); j++) {
                                    MBlogCard mblogCard = new MBlogCard();
                                    Method getOri_url = MblogCardClass.getMethod("getOri_url");
                                    String ori_url = (String) getOri_url.invoke(urlList.get(j), new Object[]{});
                                    log("getOri_url:::::::::" + ori_url);
                                    mblogCard.setOriUrl(ori_url);
//
                                    Method getUrl_title = MblogCardClass.getMethod("getUrl_title");
                                    String url_title = (String) getUrl_title.invoke(urlList.get(j), new Object[]{});
                                    log("getUrl_title:::::::::" + url_title);
                                    mblogCard.setUrlTitle(url_title);

                                    Method getUrl_type_pic = MblogCardClass.getMethod("getUrl_type_pic");
                                    String url_type_pic = (String) getUrl_type_pic.invoke(urlList.get(j), new Object[]{});
                                    log("getUrl_type_pic:::::::::" + url_type_pic);
                                    mblogCard.setUrlTypePic(url_type_pic);

                                    Method getShort_url = MblogCardClass.getMethod("getShort_url");
                                    String short_url = (String) getShort_url.invoke(urlList.get(j), new Object[]{});
                                    log("getShort_url:::::::::" + short_url);
                                    mblogCard.setShortUrl(short_url);

                                    Method getUrl_type = MblogCardClass.getMethod("getUrl_type");
                                    String url_type = (String) getUrl_type.invoke(urlList.get(j), new Object[]{});
                                    log("getUrl_type:::::::::" + url_type);
                                    mblogCard.setUrlType(url_type);

//                                    Method getHide = MblogCardClass.getMethod("getHide");
//                                    String hide = (String) getHide.invoke(urlList.get(j), new Object[]{});
//                                    log("getHide:::::::::" + hide);
                                    mBlogCards.add(mblogCard);
                                }
                                sinaMBlog.setmBlogCards(mBlogCards);

                            }

                            //================================================================
                            Method getCardInfo = StatusClass.getMethod("getCardInfo");
                            Object cardInfo = (Object) getCardInfo.invoke(listMessage.get(i), new Object[]{});
                            log("getCardInfo:::::::::" + cardInfo);
                            if (null != cardInfo) {

//                                JSONUtils.reflectValue(cardInfo);

                                Method getPeople_desc = MblogCardInfoClass.getMethod("getPeople_desc");
                                String people_desc = (String) getPeople_desc.invoke(cardInfo, new Object[]{});
                                log(" getPeople_desc:::::::::" + people_desc);

//
//                                log("content1="+ JSONUtils.reflectValue(cardInfo,"content1"));
//                                log("content2="+ JSONUtils.reflectValue(cardInfo,"content2"));
//                                log("content3="+ JSONUtils.reflectValue(cardInfo,"content3"));
//                                log("content4="+ JSONUtils.reflectValue(cardInfo,"content4"));
//                                log("contentPic="+ JSONUtils.reflectValue(cardInfo,"contentPic"));
//                                log("content_data="+ JSONUtils.reflectValue(cardInfo,"content_data"));
//                                log("content1_icon="+ JSONUtils.reflectValue(cardInfo,"content1_icon"));
//                                log("monitorUrl="+ JSONUtils.reflectValue(cardInfo,"monitorUrl"));
//                                log("page_url="+ JSONUtils.reflectValue(cardInfo,"page_url"));
                                log("page_pic=" + JSONUtils.reflectValue(cardInfo, "page_pic"));
                                log("object_type=" + JSONUtils.reflectValue(cardInfo, "object_type"));
                                String objectType = JSONUtils.reflectValue(cardInfo, "object_type");
                                String pagePic = JSONUtils.reflectValue(cardInfo, "page_pic");
                                Method getMedia = MblogCardInfoClass.getMethod("getMedia");
                                Object media = (Object) getMedia.invoke(cardInfo, new Object[]{});
                                log("getMedia:::::::::" + media);


//
                                if (media != null) {
                                    MediaDataObject mediaDataObject = new MediaDataObject();
//
//                                    Method getAppIcon = MediaDataObjectClass.getMethod("getAppIcon");
//                                    String appIcon = (String) getAppIcon.invoke(cardInfo, new Object[]{});
//                                    log("getAppIcon:::::::::" + appIcon);

                                    Method getVideoName = MediaDataObjectClass.getMethod("getVideoName");
                                    String videoName = (String) getVideoName.invoke(media, new Object[]{});
                                    log(" getVideoName:::::::::" + videoName);
                                    mediaDataObject.setName(videoName);
//
                                    Method getVideoTime = MediaDataObjectClass.getMethod("getVideoTime");
                                    String videoTime = (String) getVideoTime.invoke(media, new Object[]{});
                                    log(" getVideoTime:::::::::" + videoTime);
                                    mediaDataObject.setDuration(videoTime);
//
                                    Method getStreamUrlSD = MediaDataObjectClass.getMethod("getStreamUrlSD");
                                    String streamUrlSD = (String) getStreamUrlSD.invoke(media, new Object[]{});
                                    log(" getStreamUrlSD:::::::::" + streamUrlSD);
                                    mediaDataObject.setStreamUrl(streamUrlSD);


                                    log("streamUrlHD=" + JSONUtils.reflectValue(media, "stream_url_hd"));
                                    mediaDataObject.setStreamUrlHd(JSONUtils.reflectValue(media, "stream_url_hd"));
                                    log("h5_url=" + JSONUtils.reflectValue(media, "h5_url"));
                                    mediaDataObject.setH5Url(JSONUtils.reflectValue(media, "h5_url"));
                                    log("online_users=" + JSONUtils.reflectValue(media, "online_users"));
                                    mediaDataObject.setOnlineUsers(JSONUtils.reflectValue(media, "online_users"));
                                    log("online_users_number=" + JSONUtils.reflectValue(media, "online_users_number"));
                                    log("h265_mp4_hd=" + JSONUtils.reflectValue(media, "h265_mp4_hd"));
                                    log("inch_4_mp4_hd=" + JSONUtils.reflectValue(media, "inch_4_mp4_hd"));
                                    log("inch_5_5_mp4_hd=" + JSONUtils.reflectValue(media, "inch_5_5_mp4_hd"));
                                    log("inch_5_mp4_hd=" + JSONUtils.reflectValue(media, "inch_5_mp4_hd"));
//                                    log("live_source_icon="+ JSONUtils.reflectValue(media,"live_source_icon"));
//                                    log("video_feed_title="+ JSONUtils.reflectValue(media,"video_feed_title"));
//                                    log("video_local_path="+ JSONUtils.reflectValue(media,"video_local_path"));
                                    if (objectType.equals("video")) {
                                        mediaDataObject.setPageIcon(pagePic);
                                    }

//
                                    mediaDataObject.setOnlineUsersNumber(Integer.parseInt(JSONUtils.reflectValue(media, "online_users_number")));
//
//
                                    sinaMBlog.setMediaDataObject(mediaDataObject);
//////
//                                    Method getStreamUrlHD = MediaDataObjectClass.getMethod("getStreamUrlHD");
//                                    String streamUrlHD = (String) getStreamUrlHD.invoke(media, new Object[]{});
//                                    log("getStreamUrlHD:::::::::" + streamUrlHD);
//
//
//                                    Method getVideoH5Source = MediaDataObjectClass.getMethod("getVideoH5Source");
//                                    String videoH5Source = (String) getVideoH5Source.invoke(media, new Object[]{});
//                                    log(" getVideoH5Source:::::::::" + videoH5Source);
//
//                                    Method getOnline_users = MediaDataObjectClass.getMethod("getOnline_users");
//                                    String online_users = (String) getOnline_users.invoke(media, new Object[]{});
//                                    log(" getOnline_users:::::::::" + online_users);

//
//                                    Method getOnline_users_number = MediaDataObjectClass.getMethod("getOnline_users_number");
//                                    int online_users_number = (int) getOnline_users_number.invoke(media, new Object[]{});
//                                    log(" getOnline_users_number:::::::::" + online_users_number);
//
                                }
                            }








                            /*=====================================================================*/
                            mBlogs.add(sinaMBlog);
                            postDetailTest(sinaMBlog);

                        }
//                        postDetail(mBlogs);
                        mBlogs.clear();


                    }
                });


            }
        });

    }


    private void postDetail(final ArrayList<SinaMBlogBean> appendList) throws JSONException {
        if (appendList == null || appendList.size() == 0) {
            return;
        }

        XposedBridge.log("postDetail ＝＝＝＝");
        final JSONArray ab = getJsonArray(appendList);

        XposedBridge.log("JSONArray ＝＝＝" + ab);
//        http://114.55.110.143:9000/weibo_news
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://114.55.110.143:9000/weibo_news", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response);
                XposedBridge.log("onResponse ＝＝＝" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                XposedBridge.log("onErrorResponse ＝＝＝" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();


                map.put("news_list", ab.toString());

                XposedBridge.log("ab.toString() ＝＝＝" + ab.toString());
                return map;
            }
        };
        mQueue.add(stringRequest);


    }

    private void postDetailTest(final SinaMBlogBean bean) throws JSONException {
        if (bean == null) {
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://114.55.110.143:9001/api/weibo", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response);
                XposedBridge.log("onResponse ＝＝＝" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                XposedBridge.log("onErrorResponse ＝＝＝" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                try {

                    JSONObject jsonObject = new JSONObject();
                    if (bean.getPicInfos() != null) {
                        JSONArray jo = new JSONArray();
                        for (int j = 0; j < bean.getPicInfos().size(); j++) {
                            jo.put(reflect(bean.getPicInfos().get(j)));
                        }
                        jsonObject.put("picinfos", jo);
                    }

                    if (bean.getmBlogCards() != null) {
                        JSONArray jo = new JSONArray();
                        for (int j = 0; j < bean.getmBlogCards().size(); j++) {
                            jo.put(reflect(bean.getmBlogCards().get(j)));
                        }

                        jsonObject.put("mblogcards", jo);
                    }


                    if (bean.getMblogTags() != null) {
                        JSONArray jo = new JSONArray();
                        for (int j = 0; j < bean.getMblogTags().size(); j++) {
                            jo.put(reflect(bean.getMblogTags().get(j)));
                        }

                        jsonObject.put("mblogtags", jo);
                    }

                    if (bean.getTopicTitles() != null && bean.getTopicTitles().size() != 0) {
                        JSONArray ja = new JSONArray();
                        for (int j = 0; j < bean.getTopicTitles().size(); j++) {
                            ja.put(bean.getTopicTitles().get(j));
                        }
                        jsonObject.put("toptitle", ja);
                    }

                    if (bean.getMediaDataObject() != null) {

                        jsonObject.put("video", reflect(bean.getMediaDataObject()));
                    }

                    jsonObject.put("status", reflect(bean));
                    map.put("news",jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                XposedBridge.log("SinaHot:::" + bean.toString());
                return map;
            }
        };
        mQueue.add(stringRequest);
    }


    public static JSONArray getJsonArray(List<SinaMBlogBean> list) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            SinaMBlogBean sinaBean = list.get(i);
            JSONObject jsonObject = new JSONObject();
            if (sinaBean.getPicInfos() != null) {
                JSONArray jo = new JSONArray();
                for (int j = 0; j < sinaBean.getPicInfos().size(); j++) {
                    jo.put(reflect(sinaBean.getPicInfos().get(j)));
                }
                jsonObject.put("picinfos", jo);
            }

            if (sinaBean.getmBlogCards() != null) {
                JSONArray jo = new JSONArray();
                for (int j = 0; j < sinaBean.getmBlogCards().size(); j++) {
                    jo.put(reflect(sinaBean.getmBlogCards().get(j)));
                }

                jsonObject.put("mblogcards", jo);
            }


            if (sinaBean.getMblogTags() != null) {
                JSONArray jo = new JSONArray();
                for (int j = 0; j < sinaBean.getMblogTags().size(); j++) {
                    jo.put(reflect(sinaBean.getMblogTags().get(j)));
                }

                jsonObject.put("mblogtags", jo);
            }
            if (sinaBean.getTopicTitles() != null && sinaBean.getTopicTitles().size() != 0) {
                JSONArray ja = new JSONArray();
                for (int j = 0; j < sinaBean.getTopicTitles().size(); j++) {
                    ja.put(sinaBean.getTopicTitles().get(j));
                }
                jsonObject.put("toptitle", ja);
            }

            if (sinaBean.getMediaDataObject() != null) {

                jsonObject.put("video", reflect(sinaBean.getMediaDataObject()));
            }

            jsonObject.put("status", reflect(sinaBean));


            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }


    public static <T> JSONObject reflect(T obj) {
        JSONObject jo = new JSONObject();
        if (null == obj)
            return jo;

        try {
            Field[] fields = obj.getClass().getDeclaredFields();

            String[] types = {"int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"};
            String[] value = {"Integer", "java.lang.String", "java.lang.Boolean", "java.lang.Character", "java.lang.Float", "java.lang.Double", "java.lang.Long", "java.lang.Short", "java.lang.Byte", "java.util.Date"};
            for (int j = 0; j < fields.length; j++) {
                fields[j].setAccessible(true);
                // 字段名
//                System.out.print(fields[j].getName() + ":");

                // 字段值
                for (int i = 0; i < types.length; i++) {
                    if (fields[j].getType().getName()
                            .equalsIgnoreCase(types[i]) || fields[j].getType().getName().equalsIgnoreCase(value[i])) {
                        try {
//                            System.out.print(fields[j].get(obj) + "");
                            jo.put(fields[j].getName() + "", fields[j].get(obj) + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {

        }
        return jo;
    }


}
