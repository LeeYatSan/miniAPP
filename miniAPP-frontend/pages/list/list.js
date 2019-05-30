// pages/list/list.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    index:0,
    labelIndex:0,
    tagArray:['全部'],
    listLength:0,
    labelLength:0,
    //list:[{title:'',content:'',label:0,img:''}],
    list:[],
    memo:'',
    color:'#e2e2e2',
    url:'/images/WechatIMG7.png'
  },

  onLoad: function (options) {
    var that = this;
    that.getLabels()
    that.getCards()
  },

  onPullDownRefresh: function () {
    // 显示顶部刷新图标
    wx.showNavigationBarLoading();
    var that = this;
    that.setData({
      labelIndex:0
    })
    that.getCards()
    that.getLabels()
        
    // 隐藏导航栏加载框
    wx.hideNavigationBarLoading()
    // 停止下拉动作
    wx.stopPullDownRefresh();
  },

  getLabels:function(options){
    var that = this
    that.setData({
      tagArray: ['全部'],
    })
    //数据库得到标签数据
    wx.request({
      url: app.globalData.urlPath + '/getAllLabelsByUserID',
      data: {
        userID: app.globalData.userID,
        sessionToken: app.globalData.sessionToken
      },
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log('标签')
        console.log(res.data)
        that.setData({
          tagArray: that.data.tagArray.concat(res.data.data)
        })

        console.log(that.data.tagArray)
      }
    })
  },

  getCards:function(e){
    var that = this

    wx.request({
    url: app.globalData.urlPath + '/getAllCardsByUserID',
    data: {
      userID: app.globalData.userID,
      sessionToken: app.globalData.sessionToken
    },
    method: 'POST',
    header: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
    success: function (res) {
      console.log('列表')
      console.log(res)
      that.setData({
        list: res.data.data,
        // index: options.currentTarget .dataset.id
      })

      console.log(that.data.list)

    }
  })
  },

  bindPickerChange:function(e){
    var that = this;
    console.log('picker发送选择改变，携带值为', e.detail.value)
    that.setData(
      {
        labelIndex: e.detail.value
      }
    )
    wx.request({
      url: app.globalData.urlPath+'/getAllCardsByLabel',
      data:{
        userID:app.globalData.userID,
        sessionToken:app.globalData.sessionToken,
        labelContent:that.data.tagArray[that.data.labelIndex]
      },
      method: 'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log('标签选择')
        console.log(res)
        that.setData({
          list: res.data.data,
        })
      }
    })
  },

  buttonBind:function(e){
    wx.navigateTo({
      url: '/pages/addList/addList?num=0',
    })
  },

  cardContent:function(e){
    var that = this
    that.setData({
      index: e.currentTarget.dataset.id
    })
    wx.navigateTo({
      url: '/pages/content/content',
    })
  },

  isMemo:function(e){
    var that = this
    wx.request({
      url: app.globalData.urlPath+'/getAllFamiliarCards',
      data:{
        userID:app.globalData.userID,
        sessionToken:app.globalData.sessionToken
      },
      method:'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log('熟记')
        console.log(res)
        that.setData({
          list: res.data.data,
          color:'#F6d365',
          url: '/images/WechatIMG6.png'
        })
        console.log(that.data.list)
      }
    })
  }
})
