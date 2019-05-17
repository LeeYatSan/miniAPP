package com.miniAPP.pojo.VO;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TemplateVO {

    String keyword1 = "您今天还有卡片需要记忆哟～";
    String keyword2;
    int keyword3;

    public TemplateVO(String keyword2, int keyword3) {
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public int getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(int keyword3) {
        this.keyword3 = keyword3;
    }
}
