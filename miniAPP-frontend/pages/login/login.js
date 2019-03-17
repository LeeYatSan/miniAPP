const app = getApp()

Page({
  data: {
  },

  onLoad: function (params) {

  },

  // 登录  
  doLogin: function (e) {
    wx.login({
      success: function (res) {
        console.log(res)
        // 调用后端，获取微信的session_key, secret
        wx.request({
          url: "http://127.0.0.1:8081/onLogin",
          data: {
            'code': res.code
          },
          method: 'POST',
          header: {
            'content-type':'application/x-www-form-urlencoded'
          },
          success: function (result) {
            console.log(result);
          }
        })
      }
    })
  }
})