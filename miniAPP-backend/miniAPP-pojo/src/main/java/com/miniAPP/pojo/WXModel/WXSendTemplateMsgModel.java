package com.miniAPP.pojo.WXModel;

public class WXSendTemplateMsgModel {

    private int errcode;
    private String errmsg;

//    public WXSendTemplateMsgModel(int errcode, String errmsg) {
//        this.errcode = errcode;
//        this.errmsg = errmsg;
//    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
