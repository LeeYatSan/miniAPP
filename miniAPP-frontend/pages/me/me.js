// pages/me.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo:{},
    num:app.globalData.totalCards,
    unNum:0,
    day:app.globalData.loginDays
  },
  getNum:function(){
    var that = this;
    wx.request({
      url: app.globalData.urlPath + '/getFamiliarCardNum',
      data: {
        userID: app.globalData.userID,
        sessionToken: app.globalData.sessionToken
      },
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        that.setData({
          num:0
        })
      }
    });

  },
  getDay:function(){
    var that = this;
    wx.request({
      url: app.globalData.urlPath + '/getFamiliarCardNum',
      data: {
        userID: app.globalData.userID,
        sessionToken: app.globalData.sessionToken
      },
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        that.setData({
          day:0
        });
      }
    });
  },
  getUn:function(){
    var that = this;
    wx.request({
      url: app.globalData.urlPath + '/getFamiliarCardNum',
      data: {
        userID: app.globalData.userID,
        sessionToken: app.globalData.sessionToken
      },
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res.data);
        that.setData({
          unNum:res.data
        });
      }
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.login({
      success: function () {
        wx.getUserInfo({
          success: function (res) {
            console.log(res.userInfo);
            that.setData({
              userInfo: res.userInfo,
            });
          }
        })
      }
    });
  //  this.getDay();
    this.getNum();
    //this.getUn();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})