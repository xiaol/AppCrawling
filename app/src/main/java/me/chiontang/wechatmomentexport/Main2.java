package me.chiontang.wechatmomentexport;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import me.chiontang.wechatmomentexport.models.Comment;
import me.chiontang.wechatmomentexport.models.Like;
import me.chiontang.wechatmomentexport.models.ModelBean;
import me.chiontang.wechatmomentexport.models.Tweet;


import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findConstructorExact;

public class Main2 implements IXposedHookLoadPackage {

    Tweet currentTweet = new Tweet();
    ArrayList<Tweet> tweetList = new ArrayList<Tweet>();
    String lastTimelineId = "";
    Thread intervalSaveThread = null;
    Context appContext = null;
    String wechatVersion = "";


    /**
     * 需要上传的最终集合
     */
    public LinkedHashMap<Long,ModelBean> detailResultMap = new LinkedHashMap<Long,ModelBean>();

    //com.wandoujia.ripple.fragment.FeedListFragment

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.wandoujia"))
            return;
        final Class modelClass = XposedHelpers.findClass("com.wandoujia.ripple_framework.model.Model", lpparam.classLoader);

        XposedBridge.log("handleLoadPackage ===== " + lpparam.packageName);
        Config.enabled = true;
//        Class classCustomOp = XposedHelpers.findClass("com.wandoujia.ripple_framework.model.Model", lpparam.classLoader);
//        Class classDataloadListener = XposedHelpers.findClass("com.wandoujia.nirvana.framework.network.page.DataLoadListener.ˊ", lpparam.classLoader);
//        if(classCustomOp != null) {
//            XposedBridge.log("内部类classCustomOp=" + classCustomOp.getName());
//
//        }else{
//            XposedBridge.log("内部类classCustomOp=null");
//        }
//
//        if(classDataloadListener != null) {
//            XposedBridge.log("内部类classDataloadListener=" + classDataloadListener.getName());
//        }else{
//            XposedBridge.log("内部类classDataloadListener=null");
//        }

//
//        //＝＝＝＝＝＝＝＝＝＝  获取com.wandoujia.api.proto.Entity$Builder的数据  ＝＝＝＝＝＝＝＝＝＝
//        HashMap<String, String> map = getData(lpparam);
//
//
////        for (int i = 0; i < data.size(); i++){
//            XposedBridge.log("data map ===== " + map.toString());
////        }
//
//        //＝＝＝＝＝＝＝＝＝＝  end  ＝＝＝＝＝＝＝＝＝＝


        /**
         * Xposed提供的Hook方法
         *
         * @param className 待Hook的Class
         * @param classLoader classLoader
         * @param methodName 待Hook的Method
         * @param parameterTypesAndCallback hook回调
         * @return
         */
        findAndHookMethod("com.wandoujia.ripple_framework.ripple.fragment.FeedDetailFragment", lpparam.classLoader, "onCreate",
                Bundle.class, new XC_MethodHook() {

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Config.enabled = true;
                        XposedBridge.log("FeedDetailFragment  param ＝＝＝＝ " + param);
//                Class entity  = XposedHelpers.findClass("com.wandoujia.api.proto.Entity", lpparam.classLoader);
//                Class builder = XposedHelpers.findClass("com.wandoujia.api.proto.Entity$Builder", lpparam.classLoader);
                        Class model = XposedHelpers.findClass("com.wandoujia.ripple_framework.model.Model", lpparam.classLoader);
//                Method parseMethod = builder.getMethod("attach_entity", List.class);
//                        findAndHookMethod("com.wandoujia.ripple_framework.presenter.CommonPresenter", lpparam.classLoader, "bindText",
//                                TextView.class, CharSequence.class, new XC_MethodHook() {
//                                    @Override
//                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                        super.afterHookedMethod(param);
//
//                                        XposedBridge.log("appTitle ＝＝＝＝ " + param.args[1]);
//                                        appTreeSet.add((String) param.args[1]);
//
//                                    }
//                                });
//                        findAndHookMethod("com.wandoujia.ripple_framework.presenter.CommonPresenter", lpparam.classLoader, "bindImage",
//                                ImageView.class, String.class, new XC_MethodHook() {
//                                    @Override
//                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                        super.afterHookedMethod(param);
//
//                                        XposedBridge.log("param bindImage ＝＝＝＝ " + param.args[1]);
//                                        appTreeSet.add((String) param.args[1]);
//
//                                    }
//                                });
//                        findAndHookMethod("com.wandoujia.ripple_framework.presenter.CardPresenterFactory$SummaryPresenter", lpparam.classLoader, "bind",
//                                model, new XC_MethodHook() {
//                                    @Override
//                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                        super.afterHookedMethod(param);
////                                        paramModel.getSummary()
//                                        Object model = param.args[0];
//                                        Method getSummary = modelClass.getMethod("getSummary");
//                                        Object getSummaryResult = (Object) getSummary.invoke(model, new Object[]{});
//
//                                        appTreeSet.add((String) getSummaryResult);
//                                        XposedBridge.log("param getSummaryResult ＝＝＝＝ " + getSummaryResult);
//
//
//                                    }
//                                });


                        findAndHookMethod("com.wandoujia.ripple_framework.presenter.CardPresenterFactory$AuthorPresenter", lpparam.classLoader, "bind",
                                model, new XC_MethodHook() {
                                    @Override
                                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                        super.afterHookedMethod(param);

                                        ModelBean bean = new ModelBean();

//                                        String str1 = paramModel.getArticleDetail().author;
                                        Object model = param.args[0];
//                                        Class Parser = XposedHelpers.findClass("com.wandoujia.api.proto.ArticleDetail", lpparam.classLoader);



                                        Method getProvider = modelClass.getMethod("getProvider");
                                        Object getProviderResult = (Object) getProvider.invoke(model, new Object[]{});

                                        Method getTitle = modelClass.getMethod("getTitle");

                                        String title = (String) getTitle.invoke(model, new Object[]{});
                                        String articlTitle = (String) getTitle.invoke(getProviderResult, new Object[]{});
                                        Method getSummary = modelClass.getMethod("getSummary");
                                        String getSummaryResult = (String) getSummary.invoke(model, new Object[]{});


                                        Method getIcon = modelClass.getMethod("getIcon");
                                        String icon = (String) getIcon.invoke(model, new Object[]{});


                                        Method getArticleDetail = modelClass.getMethod("getArticleDetail");

                                        Object getArticleDetailResult = (Object) getArticleDetail.invoke(model, new Object[]{});

                                        String author = (String) XposedHelpers.getObjectField(getArticleDetailResult, "author");
                                        String content_html = (String) XposedHelpers.getObjectField(getArticleDetailResult, "content_html");
                                        long published_date = (long) XposedHelpers.getObjectField(getArticleDetailResult, "published_date");





                                        if(icon != null){
                                            bean.setIcon(icon);
                                            XposedBridge.log("  icon ＝＝＝＝ " + icon);
                                        }
                                        if (title != null) {
                                            bean.setAppName(title);
                                            XposedBridge.log("  title ＝＝＝＝ " + title);
                                        }
                                        if (articlTitle != null) {
                                            bean.setArticlTitle(articlTitle);
                                            XposedBridge.log("  articlTitle ＝＝＝＝ " + articlTitle);
                                        }
                                        if(getSummaryResult != null){
                                            bean.setSummary(getSummaryResult);
                                            XposedBridge.log("  getSummaryResult ＝＝＝＝ " + getSummaryResult);
                                        }
                                        if (author != null) {
                                            bean.setAuthor(author);
                                            XposedBridge.log("  author ＝＝＝＝ " + author);
                                        }
                                        if (published_date != 0) {
                                            bean.setPublished_date(published_date);
                                            XposedBridge.log("  published_date ＝＝＝＝ " + published_date);
                                        }
                                        if (content_html != null) {
                                            bean.setDetailHtml(content_html);
                                            XposedBridge.log("  content_html ＝＝＝＝ " + content_html);
                                        }


//                                        paramModel.getProvider().getTitle();
                                        detailResultMap.put(published_date, bean);

                                        XposedBridge.log("  detailResultMap ＝＝＝＝ " + detailResultMap.size());

                                    }
                                });

//                        HashMap<String, String> map = getData(lpparam);
//                        XposedBridge.log("data map ===== " + map.toString());


                    }
                });


    }

    public static final String ENTITY_BUILDER = "com.wandoujia.api.proto.Entity$Builder";




    private HashMap<String, String> getData(final LoadPackageParam lpparam) {

        final HashMap<String, String> map = new HashMap<String, String>();


        final Class templateType = XposedHelpers.findClass("com.wandoujia.api.proto.TemplateTypeEnum$TemplateType", lpparam.classLoader);


        final Class ArticleDetail = XposedHelpers.findClass("com.wandoujia.api.proto.ArticleDetail", lpparam.classLoader);
        final Class Detail = XposedHelpers.findClass("com.wandoujia.api.proto.Detail", lpparam.classLoader);
//        findAndHookMethod("com.wandoujia.api.proto.ArticleDetail$Builder", lpparam.classLoader, "build",  new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Config.enabled = true;
//                Object title =  param.getResult();
//
//                long published_date = (long) XposedHelpers.getObjectField(title, "published_date");
//                String content_html = (String) XposedHelpers.getObjectField(title, "content_html");
//
//
//                XposedBridge.log("detail ArticleDetail published_date＝＝＝＝ " + published_date);
//                XposedBridge.log("detail ArticleDetail content_html＝＝＝＝ " + content_html);
//
//            }
//        });





        XposedBridge.log("data map ===== " + map.toString());
//        }
        XposedBridge.log("＝＝＝＝ loop over ＝＝＝＝ ");

        return map;
//        data.add(map);

    }




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


    private void hookMethods(final LoadPackageParam lpparam) {
        findAndHookMethod("com.tencent.mm.storage.c", lpparam.classLoader, "", Cursor.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                //ʿ
//                loadFromSharedPreference();
                Config.enabled = true;

                File jsonFile = new File(Config.outputFile);
                if (!jsonFile.exists()) {
                    try {
                        jsonFile.createNewFile();
                    } catch (IOException e) {
                        XposedBridge.log(e.getMessage());
                    }
                }

                try {
                    FileWriter fw = new FileWriter(jsonFile.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write("create my file");

                    Class c = XposedHelpers.findClass("com.tencent.mm.storage.c", lpparam.classLoader);
                    bw.write(c.getName());
                    bw.close();
                } catch (IOException e) {
                    XposedBridge.log(e.getMessage());
                }
            }
        });

    }

    private void ʿ() {

    }

    private void loadFromSharedPreference() {
        XSharedPreferences pref = new XSharedPreferences(Main2.class.getPackage().getName(), "config");
        pref.makeWorldReadable();
        pref.reload();
        Config.enabled = pref.getBoolean("enabled", false);
        Config.outputFile = pref.getString("outputFile", Environment.getExternalStorageDirectory() + "/moments_output.json");
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