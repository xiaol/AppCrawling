package me.chiontang.wechatmomentexport.models.sina;

import java.io.Serializable;

/**
 * Created by Berkeley on 10/19/16.
 * 视频播放类
 *   "media_info": {
 "name": "秒拍视频",
 "duration": 65,
 "stream_url": "http://us.sinaimg.cn/001OONtNjx073fCJeDfi010401003S2n0k01.mp4?KID=unistore,video&Expires=1476939104&ssig=6AaniJam2%2B",
 "stream_url_hd": "http://us.sinaimg.cn/001b6CK2jx073fCJfBBd010401004kJx0k01.mp4?KID=unistore,video&Expires=1476939104&ssig=YlgtU4Vs%2Fo",
 "h5_url": "http://video.weibo.com/show?fid=1034:c5566520573fd84e38f613183bbbd155",
 "mp4_sd_url": "http://us.sinaimg.cn/001OONtNjx073fCJeDfi010401003S2n0k01.mp4?KID=unistore,video&Expires=1476939104&ssig=6AaniJam2%2B",
 "mp4_hd_url": "http://us.sinaimg.cn/001b6CK2jx073fCJfBBd010401004kJx0k01.mp4?KID=unistore,video&Expires=1476939104&ssig=YlgtU4Vs%2Fo",
 "h265_mp4_hd": "http://us.sinaimg.cn/000kT9PGjx073fCJjaSc0104010000200k01.m3u8?KID=unistore,video&Expires=1476939104&ssig=1Izg3oXrVC",
 "h265_mp4_ld": "http://us.sinaimg.cn/00427Xovjx073fCJjalV0104010000200k01.m3u8?KID=unistore,video&Expires=1476939104&ssig=ZuaWdz5xzU",
 "inch_4_mp4_hd": "http://us.sinaimg.cn/003I7SHejx073fCJfqpF010401003ft60k01.mp4?KID=unistore,video&Expires=1476939104&ssig=Ca6sSM%2BK7n",
 "inch_5_mp4_hd": "http://us.sinaimg.cn/003g4Vryjx073fCJfqpF010401003Lkq0k01.mp4?KID=unistore,video&Expires=1476939104&ssig=wtzQ4mchwM",
 "inch_5_5_mp4_hd": "http://us.sinaimg.cn/0014vcfvjx073fCJfISQ0104010046Jm0k01.mp4?KID=unistore,video&Expires=1476939104&ssig=0BWio9QZzo",
 "video_feed_show_custom_bg": 0,
 "play_completion_actions": [
 {
 "type": 1,
 "icon": "http://img.t.sinajs.cn/t6/style/images/face/feed_c_r.png",
 "text": "重播",
 "link": "",
 "act_code": 1221,
 "btn_code": 1000,
 "show_position": 1,
 "actionlog": {
 "oid": "230444c5566520573fd84e38f613183bbbd155",
 "code": "80000002",
 "act_type": 1,
 "source": "ad",
 "mark": "1_B4E5695D7C79C5D3BF1EEE157AA9CE19A4DD40E04D76C6DA8C000C1B3429EED091A3049237AC0DC17A8ECF1FD3A843A53190B4E06D9B4FC60F28ABAFF3165B3A807737DEFB456578D0421B66A7B3DE36826BC8B007E958D0A8BEBDD245209C400B90538F80E763081A66393A2E8DFFC1"
 }
 },
 {
 "type": 4,
 "icon": "http://img.t.sinajs.cn/t6/style/images/face/feed_c_s.png",
 "text": "打赏",
 "act_code": 1222,
 "btn_code": 1001,
 "show_position": 5,
 "link": "sinaweibo://reward?enter_type=1&enter_id=1000281261&seller=5717406501&bid=1000281261&oid=1034:c5566520573fd84e38f613183bbbd155&dis_title=&object_url=&sign=a6fe7e1824e167637773d69c867ce835&miduid=2142168143",
 "actionlog": {
 "oid": "230444c5566520573fd84e38f613183bbbd155",
 "code": "",
 "act_type": 1,
 "source": "ad",
 "mark": "1_B4E5695D7C79C5D3BF1EEE157AA9CE19A4DD40E04D76C6DA8C000C1B3429EED091A3049237AC0DC17A8ECF1FD3A843A53190B4E06D9B4FC60F28ABAFF3165B3A807737DEFB456578D0421B66A7B3DE36826BC8B007E958D0A8BEBDD245209C400B90538F80E763081A66393A2E8DFFC1"
 }
 }
 ],
 "encode_mode": "crf",
 "prefetch_type": 1,
 "prefetch_size": 524288,
 "online_users_number": 36109461,
 "online_users": "3610万次播放",
 "ttl": 3600,
 "storage_type": "unistore",
 "is_keep_current_mblog": 1,
 "has_recommend_video": 1
 }

 评论拼接网址 http://api.weibo.cn/2/comments/build_comments?networktype=wifi&max_id=0&is_show_bulletin=2&uicode=10000002&moduleID=700&trim_user=0&is_reload=1&is_encoded=0&c=android&i=b6c200b&s=419334ea&id=4031689095328114&ua=Meizu-M355__weibo__6.9.0__android__android4.4.4&wm=9848_0009&aid=01Ar354cap2DzMazi4cfDVZdyXGvhrbdVARjcDpn5c2RVXn8M.&v_f=2&v_p=34&from=1069095010&gsid=_2A251AAYtDeTxGeRP6lIR9inEzD-IHXVXlB7lrDV6PUJbkdANLXnwkWqTSjG5xLENZHY6xuUpOdJoPmo9xQ..&lang=zh_CN&lfid=231091&skin=default&count=20&oldwm=9848_0009&sflag=1&luicode=10000010&fetch_level=0
 */
public class MediaDataObject implements Serializable {

    //视频类 MediaDataObject
    //拍摄内容标题
    private String name;
    //持续时间
    private String duration;
    //视频流 一般和高清
    private String streamUrl;
    private String streamUrlHd;
    //源h5url
    private String h5Url;
    //播放次数
    private int onlineUsersNumber;
    private String onlineUsers;
    //图片背景
    private String pagePic;


    public String getPageIcon() {
        return pagePic;
    }

    public void setPageIcon(String pageIcon) {
        this.pagePic = pageIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getStreamUrlHd() {
        return streamUrlHd;
    }

    public void setStreamUrlHd(String streamUrlHd) {
        this.streamUrlHd = streamUrlHd;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public int getOnlineUsersNumber() {
        return onlineUsersNumber;
    }

    public void setOnlineUsersNumber(int onlineUsersNumber) {
        this.onlineUsersNumber = onlineUsersNumber;
    }

    public String getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(String onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
}
