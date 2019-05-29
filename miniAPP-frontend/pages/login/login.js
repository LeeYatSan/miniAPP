// pages/login/login.js
var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    formId:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
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

  },

  submitInfo:function(res){
    var that = this
    that.setData({
      formId:res.detail.formId
    })
    console.log(that.data.formId)
  },

  bindGetUserInfo: function(res) {
    var that = this;
    let info = res;
    console.log(info);
    if(info.detail.userInfo) {
      console.log("点击了同意授权");
      wx.login({
        success: function (res) {
          if (res.code) {
            console.log("请求"),
            wx.request({
              url: app.globalData.urlPath + '/onRegister',
              method: 'POST',
              //url:'http://localhost:8081/onLogin',
              data: {
                code: res.code,
                formID:that.data.formId
              },
              header: {
                "Content-Type": "application/x-www-form-urlencoded"  // 默认值
              },
              success: function (res) {
                console.log(that.data.formId)
                console.log("成功");
                var userinfo = {};
                userinfo['id'] = res.data.id;
                userinfo['nickName'] = info.detail.userInfo.nickName;
                userinfo['avatarUrl'] = info.detail.userInfo.avatarUrl;
                wx.setStorageSync('userinfo', userinfo);

                console.log(res);
                app.globalData.userID = res.data.data.id;
                app.globalData.sessionToken = res.data.data.sessionToken;
                
                //跳转
                console.log("跳转");
                wx.switchTab({
                  url: '../list/list',
                })

              }
            })
          }else {
            console.log("授权失败");
          }
        },
      })

    } else {
      console.log("点击了拒绝授权");
    }
  },
  
  queryUsreInfo: function () {
    wx.request({
      url: app.globalData.urlPath + '/onLogin',
      data: {
        openid: app.globalData.openid
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        getApp().globalData.userInfo = res.data;
      }
    });
  },
})
