package com.miniAPP.pojo.WXModel;


public class WXSessionModel {
    private String session_key;
    private String openid;

    public void setSession_key(String session_key){
        this.session_key = session_key;
    }

    public void setOpenid(String openid){
        this.openid = openid;
    }

    public String getSession_key(){
        return session_key;
    }

    public String getOpenid(){
        return openid;
    }
}
