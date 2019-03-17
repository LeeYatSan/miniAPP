// pages/login/login.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo')
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

  bindGetUserInfo: function(res) {
    let info = res;
    console.log(info);
    if(info.detail.userInfo) {
      console.log("点击了同意授权");
      wx.login({
        success: function (res) {
          if (res.code) {
            wx.request({
              url: 'http://localhost:8081',
              data: {
                code: res.code,
                nickName: info.detail.userInfo.nickName,
                city: info.detail.userInfo.city,
                province: info.detail.userInfo.province,
                avatarUrl: info.detail.userInfo.avatarUrl
              },
              header: {
                'content-type': 'application/json' // 默认值
              },
              success: function (res) {
                var userinfo = {};
                userinfo['id'] = res.data.id;
                userinfo['nickName'] = info.detail.userInfo.nickName;
                userinfo['avatarUrl'] = info.detail.userInfo.avatarUrl;
                wx.setStorageSync('userinfo', userinfo);
              }
            })
          } else {
            console.log("授权失败");
          }
        },
      })

    } else {
      console.log("点击了拒绝授权");
    }
  }
})
