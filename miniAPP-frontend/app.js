//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 获取用户信息
    wx.getSetting({
      success: function (data) {
        if (data.authSetting["scope.userInfo"]) {
          wx.getUserInfo({
            success: function (data) {
              console.log("用户已经授权")
              wx.checkSession({

                success: function (res) {
                  console.log('session')
                  //未过期
                  var app = getApp();
                  console.log(app.globalData.userID);
                  console.log(app.globalData.sessionToken);
                  wx.request({
                    url: app.globalData.urlPath + '/onLogin',
                    data: {
                      userID: app.globalData.userID,
                      sessionToken: app.globalData.sessionToken
                    },
                    header: {
                      "Content-Type": "application/x-www-form-urlencoded"  // 默认值
                    },
                    success: function (res) {
                      console.log(res)
                      wx.switchTab({
                        url: '/pages/list/list',
                      })
                    },
                    fail:function(res){
                      wx.redirectTo({
                        url: '/pages/login/login'
                      })
                    }
                  })
                },
                fail: function (res) {
                  // wx.redirectTo({
                  //   url: '/pages/login/login'
                  // })
                  // 登录
                  wx.login({
                    success: res => {
                      // 发送 res.code 到后台换取 openId, sessionKey, unionId
                    }
                  })
                }
              })
            }
          })
        }
        else{
          console.log('未授权')
          // wx.redirectTo({
          //   url: '/pages/login/login'
          // })
          //console.log('未授权')
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    urlPath: "http://134.175.11.69:8080/client",
    // urlPath: "http://localhost:8081",
    userID: null,
    sessionToken: null
  },
})
