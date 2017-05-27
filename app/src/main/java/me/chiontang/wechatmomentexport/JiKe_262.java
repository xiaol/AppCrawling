package me.chiontang.wechatmomentexport;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import me.chiontang.wechatmomentexport.models.Comment;
import me.chiontang.wechatmomentexport.models.JiKeBean;
import me.chiontang.wechatmomentexport.models.Like;
import me.chiontang.wechatmomentexport.models.Tweet;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class JiKe_262 implements IXposedHookLoadPackage {

    Tweet currentTweet = new Tweet();
    ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
    String lastTimelineId = "";
    Thread intervalSaveThread = null;
    Context appContext = null;
    String wechatVersion = "";
    RequestQueue mQueue;

    private static String cur_content_html = "";

    /**
     * 需要上传的最终集合
     */
//    public LinkedHashMap<Long, ModelBean> detailResultMap = new LinkedHashMap<Long, ModelBean>();

    //com.wandoujia.ripple.fragment.FeedListFragment
    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {

        if (!lpparam.packageName.equals("com.ruguoapp.jike"))
            return;

        XposedBridge.log("handleLoadPackage ===== " + lpparam.packageName);
        Config.enabled = true;

        findAndHookMethod("com.ruguoapp.jike.ui.activity.MainActivity", lpparam.classLoader, "onCreate",
                Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        if (appContext != null) {
                            return;
                        }
                        XposedBridge.log("LauncherUI hooked.");
                        appContext = ((Activity) param.thisObject).getApplicationContext();
                        mQueue = Volley.newRequestQueue(appContext);
                        hookMethods(lpparam);
                    }
                });


    }

    public static JSONArray getJsonArray(List<JiKeBean> list) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JiKeBean jiKeBean = list.get(i);
            JSONObject jo = new JSONObject();
            if (jiKeBean.getApp_name() != null) {
                jo.put("app_name", jiKeBean.getApp_name());
            }
            if (jiKeBean.getSummary() != null) {
                jo.put("summary", jiKeBean.getSummary());
            }
            jo.put("published_date", jiKeBean.getPublished_date());
            if (jiKeBean.getLink() != null) {
                jo.put("link", jiKeBean.getLink());
            }
            if (jiKeBean.getPictureUrl() != null) {
                JSONArray ja = new JSONArray();
                for (int j = 0; j < jiKeBean.getPictureUrl().size(); j++) {
                    ja.put(jiKeBean.getPictureUrl().get(j));
                }
                jo.put("pictureUrl", ja);
            }

            jsonArray.put(jo);
        }

        return jsonArray;
    }

    private void postDetail(final ArrayList<JiKeBean> appendList) throws JSONException {
        if (appendList == null || appendList.size() == 0) {
            return;
        }

        XposedBridge.log("postDetail ＝＝＝＝");
//        String aa = net.sf.json.JSONArray.fromObject(appendList).toString();

//        XposedBridge.log("onResponse ＝＝＝" + aa);
        final JSONArray ab = getJsonArray(appendList);

        XposedBridge.log("JSONArray ＝＝＝" + ab);
        //莱特提供地址

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://114.55.110.143:9000/jike_news", new Response.Listener<String>() {
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


    public static final String ENTITY_BUILDER = "com.wandoujia.api.proto.Entity$Builder";


    private void getAllTextViews(final View v) {
        if (v instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) v;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                getAllTextViews(child);
            }
        } else if (v instanceof TextView) {
            String name = ((TextView) v).getText().toString();
            writeTxtToFile(name);
        }
    }

    /**
     * 记录上次list的size
     */
    int listSize = 0;


    private void hookMethods(final LoadPackageParam lpparam) {

//        final Class modelClass = XposedHelpers.findClass("com.wandoujia.ripple_framework.model.Model", lpparam.classLoader);

        /**
         * Xposed提供的Hook方法
         *
         * @param className 待Hook的Class
         * @param classLoader classLoader
         * @param methodName 待Hook的Method
         * @param parameterTypesAndCallback hook回调
         * @return
         */
        findAndHookMethod("com.ruguoapp.jike.ui.fragment.FeedFragment", lpparam.classLoader, "onCreate",
                Bundle.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Config.enabled = true;
                        XposedBridge.log("com.ruguoapp.jike.ui.adapter.MessageViewHolder AAAAAAAAAAAAAA");
                        findAndHookMethod("com.ruguoapp.jike.view.a", lpparam.classLoader, "d", List.class,
                                new XC_MethodHook() {


                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        super.afterHookedMethod(param);


                                        Config.enabled = true;

                                        XposedBridge.log("com.ruguoapp.jike.ASDFASFA " + param.args[0].toString());


                                        List listMessage = (List) param.args[0];

                                        int listMessageSize = listMessage.size();
                                        XposedBridge.log("listMessage.size() ＝＝＝＝ " + listMessageSize);
                                        XposedBridge.log("listSize ＝＝＝＝ " + listSize);

                                        if (listMessageSize == 50) {  // 50说明刷新数据，初始化listsize
                                            listSize = 0;
                                        }
//
//                                        if (listMessageSize == listSize) {       //当前数据等于刚刚获取的数据，为重复数据。不进行hook
//                                            return;
//                                        }

                                        Class messageObj = XposedHelpers.findClass("com.ruguoapp.jike.model.bean.MessageBean", lpparam.classLoader);
                                        /** 本次新增的list*/
                                        ArrayList<JiKeBean> appendList = new ArrayList<JiKeBean>(20);


                                        //只获取更新之后的数据。之前的数据不加入list
                                        for (int i = 0; i < listMessageSize; i++) {

                                            Class feedMessageBean = XposedHelpers.findClass("com.ruguoapp.jike.model.bean.feed.FeedMessageBean", lpparam.classLoader);

                                            if (!listMessage.get(i).getClass().equals(feedMessageBean)) {
                                                continue;
                                            }
                                            Method feedEntity = feedMessageBean.getMethod("feedEntity");
                                            Object messageBean = (Object) feedEntity.invoke(listMessage.get(i), new Object[]{});

//                                            Method getCollectCount = messageObj.getMethod("getCollectCount");
//                                            int collectCount = (int) getCollectCount.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("collectCount  param ＝＝＝＝ " + collectCount);

//                                            Method getCollectedTime = messageObj.getMethod("getCollectedTime");
//                                            long collectedTime = (long) getCollectedTime.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("collectedTime  param ＝＝＝＝ " + collectedTime);

                                            JiKeBean bean = new JiKeBean();

                                            Method getContent = messageObj.getMethod("getContent");
                                            String collected = (String) getContent.invoke(messageBean, new Object[]{});
                                            XposedBridge.log("collected  param ＝＝＝＝ " + collected);
                                            bean.setSummary(collected);

                                            Method getCreatedAt = messageObj.getMethod("getCreatedAt");
                                            long createdAt = (long) getCreatedAt.invoke(messageBean, new Object[]{});
                                            XposedBridge.log("createdAt  param ＝＝＝＝ " + createdAt);
                                            bean.setPublished_date(createdAt);
//                                            Method getId = messageObj.getMethod("getId");
//                                            String id = (String) getId.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("id  param ＝＝＝＝ " + id);

                                            Method getLinkUrl = messageObj.getMethod("getLinkUrl");
                                            String linkUrl = (String) getLinkUrl.invoke(messageBean, new Object[]{});
                                            XposedBridge.log("linkUrl  param ＝＝＝＝ " + linkUrl);
                                            bean.setLink(linkUrl);

//                                            Method getMessageId = messageObj.getMethod("getMessageId");
//                                            int messageId = (int) getMessageId.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("messageId  param ＝＝＝＝ " + messageId);

//                                            Method getObjectId = messageObj.getMethod("getObjectId");
//                                            String objectId = (String) getObjectId.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("objectId  param ＝＝＝＝ " + objectId);

//                                            Method getOriginUrl = messageObj.getMethod("getOriginUrl");
//                                            String originUrl = (String) getOriginUrl.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("originUrl  param ＝＝＝＝ " + originUrl);

//                                            Method getSourceRawValue = messageObj.getMethod("getSourceRawValue");
//                                            String sourceRawValue = (String) getSourceRawValue.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("sourceRawValue  param ＝＝＝＝ " + sourceRawValue);


                                            Method getTitle = messageObj.getMethod("getTitle");
                                            String title = (String) getTitle.invoke(messageBean, new Object[]{});
                                            XposedBridge.log("title  param ＝＝＝＝ " + title);
                                            bean.setApp_name(title);

//                                            Method getTopic = messageObj.getMethod("getTopic");
//                                            String topic = (String) getTopic.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("topic  param ＝＝＝＝ " + topic);


//                                            Method getTopicObjectId = messageObj.getMethod("getTopicObjectId");
//                                            String topicObjectId = (String) getTopicObjectId.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("topicObjectId  param ＝＝＝＝ " + topicObjectId);

//                                            Method getUpdatedAt = messageObj.getMethod("getUpdatedAt");
//                                            long updatedAt = (long) getUpdatedAt.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("updatedAt  param ＝＝＝＝ " + updatedAt);


//                                            Method getPictureUrls = messageObj.getMethod("getPictureUrls");
//                                            List pictureUrls = (List) getPictureUrls.invoke(messageBean, new Object[]{});
//                                            XposedBridge.log("pictureUrls  param ＝＝＝＝ " + pictureUrls.toString());


//                                            XposedBridge.log("pictureUrls  param ＝＝＝＝ " + pictureUrls.toString());

//                                            final Field field = findField(XposedHelpers.findClass("com.ruguoapp.jike.ui.adapter.v",lpparam.classLoader),"pictureUrls");
//                                            field.get(new Object());
//                                           String imagelist = (String) messageObj.forName("pictureUrls");
                                            Field f = XposedHelpers.findField(messageObj, "pictureUrls");
                                            ArrayList pictureUrlslist = (ArrayList) f.get(messageBean);


                                            XposedBridge.log("pictureUrls  asdfasdfasf ＝＝＝＝ " + pictureUrlslist.toString());


                                            ArrayList<String> imageList = new ArrayList<String>();
                                            Class pictureUrlsClass = XposedHelpers.findClass("com.ruguoapp.jike.model.bean.PictureUrlsBean", lpparam.classLoader);
                                            for (int j = 0; j < pictureUrlslist.size(); j++) {
//                                                Method getPicUrl = pictureUrlsClass.getMethod("getPicUrl");
//                                                String picUrl = (String) getPicUrl.invoke(imageList.get(j), new Object[]{});
                                                //获取类变量值
                                                Field f1 = XposedHelpers.findField(pictureUrlsClass, "picUrl");
                                                String picUrl = (String) f1.get(pictureUrlslist.get(j));
                                                XposedBridge.log("pictureUrls.get" + j + " picUrl  param ＝＝＝＝ " + picUrl);
                                                if (picUrl != null)
                                                    imageList.add(picUrl);

//                                                Method getMiddlePicUrl = pictureUrlsClass.getMethod("getMiddlePicUrl");
//                                                String middlePicUrl = (String) getMiddlePicUrl.invoke(pictureUrls.get(j), new Object[]{});
//                                                XposedBridge.log("pictureUrls.get" + j + " middlePicUrl  param ＝＝＝＝ " + middlePicUrl);
                                            }
                                            bean.setPictureUrl(imageList);
                                            appendList.add(bean);
                                        }
//                                        listSize = listMessageSize;

                                        XposedBridge.log("开始发送数据");

                                        //发送获取数据
                                        postDetail(appendList);
                                        appendList.clear();

                                        XposedBridge.log("发送数据");


                                    }


                                });

                    }
                });


    }


    private String getTimelineId(String xmlResult) {
        Pattern idPattern = Pattern.compile("<id><!\\[CDATA\\[(.+?)\\]\\]></id>");
        Matcher idMatcher = idPattern.matcher(xmlResult);
        if (idMatcher.find()) {
            return idMatcher.group(1);
        } else {
            return "";
        }
    }

    private void parseTimelineXML(String xmlResult) throws Throwable {
        Pattern userIdPattern = Pattern.compile("<username><!\\[CDATA\\[(.+?)\\]\\]></username>");
        Pattern contentPattern = Pattern.compile("<contentDesc><!\\[CDATA\\[(.+?)\\]\\]></contentDesc>", Pattern.DOTALL);
        Pattern mediaPattern = Pattern.compile("<media>.*?<url.*?><!\\[CDATA\\[(.+?)\\]\\]></url>.*?</media>");
        Pattern timestampPattern = Pattern.compile("<createTime><!\\[CDATA\\[(.+?)\\]\\]></createTime>");

        Matcher userIdMatcher = userIdPattern.matcher(xmlResult);
        Matcher contentMatcher = contentPattern.matcher(xmlResult);
        Matcher mediaMatcher = mediaPattern.matcher(xmlResult);
        Matcher timestampMatcher = timestampPattern.matcher(xmlResult);

        currentTweet.id = getTimelineId(xmlResult);

        currentTweet.rawXML = xmlResult;

        if (timestampMatcher.find()) {
            currentTweet.timestamp = Integer.parseInt(timestampMatcher.group(1));
        }

        if (userIdMatcher.find()) {
            currentTweet.authorId = userIdMatcher.group(1);
        }

        if (contentMatcher.find()) {
            currentTweet.content = contentMatcher.group(1);
        }

        while (mediaMatcher.find()) {
            boolean flag = true;
            for (int i = 0; i < currentTweet.mediaList.size(); i++) {
                if (currentTweet.mediaList.get(i).equals(mediaMatcher.group(1))) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                currentTweet.mediaList.add(mediaMatcher.group(1));
        }

    }

    private void parseSnsObject(Object aqiObject) throws Throwable {
        Tweet matchTweet = null;
        Field field = null;
        Object userId = null, nickname = null;

        field = aqiObject.getClass().getField(Config.PROTOCAL_SNS_OBJECT_USERID_FIELD);
        userId = field.get(aqiObject);

        field = aqiObject.getClass().getField(Config.PROTOCAL_SNS_OBJECT_NICKNAME_FIELD);
        nickname = field.get(aqiObject);

        field = aqiObject.getClass().getField(Config.PROTOCAL_SNS_OBJECT_TIMESTAMP_FIELD);
        long snsTimestamp = ((Integer) field.get(aqiObject)).longValue();

        if (userId == null || nickname == null) {
            return;
        }

        for (int i = 0; i < tweetList.size(); i++) {
            Tweet tweet = tweetList.get(i);
            if (tweet.timestamp == snsTimestamp && tweet.authorId.equals((String) userId)) {
                matchTweet = tweet;
                break;
            }
        }

        if (matchTweet == null) {
            return;
        }

        matchTweet.ready = true;
        matchTweet.author = (String) nickname;
        field = aqiObject.getClass().getField(Config.PROTOCAL_SNS_OBJECT_COMMENTS_FIELD);
        LinkedList list = (LinkedList) field.get(aqiObject);
        for (int i = 0; i < list.size(); i++) {
            Object childObject = list.get(i);
            parseSnsObjectExt(childObject, true, matchTweet);
        }

        field = aqiObject.getClass().getField(Config.PROTOCAL_SNS_OBJECT_LIKES_FIELD);
        LinkedList likeList = (LinkedList) field.get(aqiObject);
        for (int i = 0; i < likeList.size(); i++) {
            Object likeObject = likeList.get(i);
            parseSnsObjectExt(likeObject, false, matchTweet);
        }
        matchTweet.print();

    }

    private void parseSnsObjectExt(Object apzObject, boolean isComment, Tweet matchTweet) throws Throwable {
        if (isComment) {
            Field field = apzObject.getClass().getField(Config.SNS_OBJECT_EXT_AUTHOR_NAME_FIELD);
            Object authorName = field.get(apzObject);

            field = apzObject.getClass().getField(Config.SNS_OBJECT_EXT_REPLY_TO_FIELD);
            Object replyToUserId = field.get(apzObject);

            field = apzObject.getClass().getField(Config.SNS_OBJECT_EXT_COMMENT_FIELD);
            Object commentContent = field.get(apzObject);

            field = apzObject.getClass().getField(Config.SNS_OBJECT_EXT_AUTHOR_ID_FIELD);
            Object authorId = field.get(apzObject);

            if (authorId == null || commentContent == null || authorName == null) {
                return;
            }

            for (int i = 0; i < matchTweet.comments.size(); i++) {
                Comment loadedComment = matchTweet.comments.get(i);
                if (loadedComment.authorId.equals((String) authorId) && loadedComment.content.equals((String) commentContent)) {
                    return;
                }
            }

            Comment newComment = new Comment();
            newComment.authorName = (String) authorName;
            newComment.content = (String) commentContent;
            newComment.authorId = (String) authorId;
            newComment.toUserId = (String) replyToUserId;

            for (int i = 0; i < matchTweet.comments.size(); i++) {
                Comment loadedComment = matchTweet.comments.get(i);
                if (replyToUserId != null && loadedComment.authorId.equals((String) replyToUserId)) {
                    newComment.toUser = loadedComment.authorName;
                    break;
                }
            }

            matchTweet.comments.add(newComment);
        } else {
            Field field = apzObject.getClass().getField(Config.SNS_OBJECT_EXT_AUTHOR_NAME_FIELD);
            Object nickname = field.get(apzObject);
            field = apzObject.getClass().getField(Config.SNS_OBJECT_EXT_AUTHOR_ID_FIELD);
            Object userId = field.get(apzObject);
            if (nickname == null || userId == null) {
                return;
            }

            if (((String) userId).equals("")) {
                return;
            }

            for (int i = 0; i < matchTweet.likes.size(); i++) {
                if (matchTweet.likes.get(i).userId.equals((String) userId)) {
                    return;
                }
            }
            Like newLike = new Like();
            newLike.userId = (String) userId;
            newLike.userName = (String) nickname;
            matchTweet.likes.add(newLike);
        }
    }

    private void addTweetToListNoRepeat() {
        if (currentTweet.id.equals("")) {
            return;
        }
        int replaceIndex = -1;
        for (int i = 0; i < tweetList.size(); i++) {
            Tweet loadedTweet = tweetList.get(i);
            if (loadedTweet.id.equals(currentTweet.id)) {
                replaceIndex = i;
                break;
            }
        }

        Tweet tweetToAdd = currentTweet.clone();
        if (replaceIndex == -1) {
            tweetList.add(tweetToAdd);
        } else {
            tweetList.remove(replaceIndex);
            tweetList.add(replaceIndex, tweetToAdd);
        }

    }


    // 将字符串写入到文本文件中
    public void writeTxtToFile(String name) {

        XposedBridge.log("---------------------");
        XposedBridge.log(name);
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }

    }
}