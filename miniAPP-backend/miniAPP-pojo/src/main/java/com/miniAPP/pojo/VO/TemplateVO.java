//package com.miniAPP.pojo.VO;
//
//import com.miniAPP.utils.JsonUtils;
//
//public class TemplateVO {
//
//    TemplateDataVO keyword1 = new TemplateDataVO("您今天还有卡片需要记忆哟～");
//    TemplateDataVO keyword2;
//    TemplateDataVO keyword3;
//
//    public TemplateVO(TemplateDataVO keyword2, TemplateDataVO keyword3) {
//        this.keyword2 = keyword2;
//        this.keyword3 = keyword3;
//    }
//
//    public TemplateVO(String keyword2, int keyword3) {
//        this.keyword2 = new TemplateDataVO(keyword2);
//        this.keyword3 = new TemplateDataVO(keyword3);
//    }
//
//    public TemplateDataVO getKeyword1() {
//        return keyword1;
//    }
//
//    public TemplateDataVO getKeyword2() {
//        return keyword2;
//    }
//
//    public void setKeyword2(TemplateDataVO keyword2) {
//        this.keyword2 = keyword2;
//    }
//
//    public TemplateDataVO getKeyword3() {
//        return keyword3;
//    }
//
//    public void setKeyword3(TemplateDataVO keyword3) {
//        this.keyword3 = keyword3;
//    }
//}

package com.miniAPP.pojo.VO;

import java.util.Map;

public class TemplateVO {

    private String touser; //接收者（用户）的 openid

    private String template_id; //所需下发的模板消息的id

    private String page; //点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。

    private String form_id; //表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id

    private Map<String,TemplateDataVO> data; //模板内容，不填则下发空模板

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }

    public Map<String, TemplateDataVO> getData() {
        return data;
    }

    public void setData(Map<String, TemplateDataVO> data) {
        this.data = data;
    }
}
