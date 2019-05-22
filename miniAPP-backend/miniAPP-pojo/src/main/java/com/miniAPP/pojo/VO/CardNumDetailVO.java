package com.miniAPP.pojo.VO;

public class CardNumDetailVO {

    /**
     * 用户ID
     */
    private Long userID;

    /**
     * 卡片总数
     */
    private int total;

    /**
     * 熟悉的卡片数
     */
    private int familiarNum;

    public CardNumDetailVO(Long userID, int total, int familiarNum) {
        this.userID = userID;
        this.total = total;
        this.familiarNum = familiarNum;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFamiliarNum() {
        return familiarNum;
    }

    public void setFamiliarNum(int familiarNum) {
        this.familiarNum = familiarNum;
    }
}
