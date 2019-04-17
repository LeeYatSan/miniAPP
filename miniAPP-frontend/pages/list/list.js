// pages/list/list.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    index:0,
  },

  onLoad: function (options) {
    var that = this;

    //数据库得到标签数据
//     wx.request({
//       url: that.globalData.urlPath,
//       data: {},
//       header: {
//         "Content-Type": "application/x-www-form-urlencoded" 
//       },
//       success:  function  (res)  {
//         console.log(res.data)
//         that.setData({
//           tagArray: res.data //设置数据
//         })
//       }
//     })

    //数据库得到列表标签
    // wx.request({
    //   url: app.globalData.urlPath,
    //   data:  {},
    //   header:  {
    //     "Content-Type": "application/x-www-form-urlencoded"
    //   },
    //   success:  function  (res)  {

    //     console.log(res.data)
    //     that.setData({
    //       Industry:  res.data //设置数据
    //     })
    //   },
    //   fail:  function  (err)  {
    //     console.log(err)
    //   }
    // }),

    that.setData(
      {
        tagArray: ['python', 'java', 'php']
      }
    )
  },

  bindPickerChange:function(e){
    var that = this;
    console.log('picker发送选择改变，携带值为', e.detail.value)
    that.setData(
      {
        index: e.detail.value
      }
    )
  }
})
