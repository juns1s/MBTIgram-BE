package com.Zizon.MBTInstagram.global.embedded;

public enum SnsType {
    INSTAGRAM("instagram"),
    FACEBOOK("facebook"),
    TWITTER("twitter"),
    THREAD("thread"),
    NAVER_BLOG("naver_blog"),
    ETC("etc");

    private String snsType;

    SnsType(String snsType) {
        this.snsType = snsType;
    }

    public String getSnsType() {
        return snsType;
    }
}
