package me.chiontang.wechatmomentexport.models.sina;

/**
 * Created by Berkeley on 10/9/16.
 *
 *
 *  "id": 2142168143,
 "idstr": "2142168143",
 "class": 1,
 "screen_name": "全球健身中心",
 "name": "全球健身中心",
 "province": "44",
 "city": "1",
 "location": "广东 广州",
 "description": "最大 最全的健身类微博，来自中日韩欧美等最热门的健身教程，加上专业的教练指导，总有一款适合你，还等什么了？赶快健身起来吧！",
 博客地址
 "url": "http://shop60224810.taobao.com",
 头像
 "profile_image_url": "http://tva3.sinaimg.cn/crop.0.2.1242.1242.50/7faee44fjw8f82cyhlfs0j20yi0ym40e.jpg",
 背景图片
 "cover_image": "http://ww2.sinaimg.cn/crop.0.0.920.300/7faee44fgw1f68a9rxhcej20pk08ctda.jpg",
 "cover_image_phone": "http://ww3.sinaimg.cn/crop.0.0.640.640.640/7faee44fgw1f82ck0dmrmj20yi0yh0vb.jpg;http://ww4.sinaimg.cn/crop.0.0.640.640.640/7faee44fjw1f0n51rmz4fj20v90v940o.jpg;http://ww2.sinaimg.cn/crop.0.0.640.640.640/7faee44fjw1emnqrzlxtnj20j60j7779.jpg",
 "profile_url": "shijiyinyue",
 "domain": "shijiyinyue",
 "weihao": "",
 性别
 "gender": "f",
 粉丝
 "followers_count": 10867616,
 关注
 "friends_count": 433,

 "pagefriends_count": 30,
 全部微博
 "statuses_count": 32652,
 "favourites_count": 1548,
 创建时间
 "created_at": "Wed May 25 11:03:51 +0800 2011",
 "following": true,
 "allow_all_act_msg": true,
 "geo_enabled": true,
 是否认证
 "verified": true,
 "verified_type": 0,
 "remark": "",
 "ptype": 0,
 允许所有评论
 "allow_all_comment": true,
 高清头像
 "avatar_large": "http://tva3.sinaimg.cn/crop.0.2.1242.1242.180/7faee44fjw8f82cyhlfs0j20yi0ym40e.jpg",
 "avatar_hd": "http://tva3.sinaimg.cn/crop.0.2.1242.1242.1024/7faee44fjw8f82cyhlfs0j20yi0ym40e.jpg",
 微博认证
 "verified_reason": "优秀健身资讯博主 ",
 "verified_trade": "1288",
 "verified_reason_url": "",
 "verified_source": "",
 "verified_source_url": "",
 "verified_state": 0,
 "verified_level": 3,
 "verified_type_ext": 0,
 "verified_reason_modified": "",
 "verified_contact_name": "",
 "verified_contact_email": "",
 "verified_contact_mobile": "",
 "follow_me": false,
 "online_status": 0,
 "bi_followers_count": 233,
 "lang": "zh-cn",
 "star": 0,
 "mbtype": 12,
 级别 vip
 "mbrank": 6,
 "block_word": 0,
 "block_app": 1,
 "level": 2,
 "type": 1,
 "ulevel": 36028797018963970,
 "badge": {
 "uc_domain": 0,
 "enterprise": 0, 个人0 企业 1
 "anniversary": 0,
 "taobao": 0,
 "travel2013": 0,
 "gongyi": 0,
 "gongyi_level": 0,
 "bind_taobao": 1,
 "hongbao_2014": 0,
 "suishoupai_2014": 0,
 "dailv": 0,
 "zongyiji": 1,
 "vip_activity1": 0,
 "unread_pool": 0,
 "daiyan": 0,
 "ali_1688": 0,
 "vip_activity2": 0,
 "suishoupai_2016": 1,
 "fools_day_2016": 0,
 "uefa_euro_2016": 0,
 "super_star_2016": 1,
 "unread_pool_ext": 0,
 "self_media": 1,
 "olympic_2016": 1,
 "dzwbqlx_2016": 1,
 "discount_2016": 0,
 "wedding_2016": 1,
 "shuang11_2016": 0
 },
 "badge_top": "",
 "has_ability_tag": 1,
 "extend": {
 "privacy": {
 "mobile": 0
 },
 "mbprivilege": "0000000000000000000000000000000000000000000000000000000005c00208"
 },
 "credit_score": 80,
 "user_ability": 524,
 信用等级
 "urank": 39,
 "icons": [
 {
 "url": "https://h5.sinaimg.cn/upload/2016/09/27/494/common_icon_membership_level6.png"
 }
 ]



 */
public class BlogUser {

    //BlogUser
    //用户Id
    private String userId;
    //高清头像
    private String avatarHd;
    //微博名称
    private String name;
    //性别
    private String gender;
    //是否认证 及认证类型
    private  boolean verified;
    private int verifiedType;
    //会员等级
    private int mbrank;




    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarHd() {
        return avatarHd;
    }

    public void setAvatarHd(String avatarHd) {
        this.avatarHd = avatarHd;
    }
}
