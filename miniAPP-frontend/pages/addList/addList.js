// pages/addList/addList.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    titleCount: 0, //标题字数
    contentCount: 0, //正文字数
    title: '', //标题内容
    content: '' ,//正文内容
    images:'' ,//图片
    labelContent:'',//标签
    photoData:''
  },

  handleTitleInput: function (e) {
    var t_text = e.detail.value.length;
    var that = this
    that.setData({
      titleCount: t_text,
      title:e.detail.value
    }) 
  },

  handleContentInput: function (e) {
    var t_text = e.detail.value.length;
    var that = this
    that.setData({
      contentCount: t_text,
      content:e.detail.value
    })
  },

  handleLabelInput:function(e){
    var that = this
    that.setData({
      labelContent:e.detail.value
    })
  },

  imgChoose:function(e){
    var that = this
    wx.chooseImage({
      count:1,
      sizeType: ['original', 'compressed'],// 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'],// 可以指定来源是相册还是相机，默认二者都有
      success: function(res) {
        console.log(res) 
        that.setData({
          images: res.tempFilePaths
        })
        wx.uploadFile({
          url: app.globalData.urlPath + '/uploadPhoto', 
          filePath: res.tempFilePaths[0],//要上传文件资源的路径 String类型 
          name: 'photo',
          header: {
            "Content-Type": "multipart/form-data"
          },
          formData: {
            'sessionToken':app.globalData.sessionToken,
            'userID':app.globalData.userID
          },
          success: function (res) {
            if (res.statusCode == 200) {
              var data = JSON.parse(res.data)
              console.log("photo")
              console.log(res)
              console.log(data)
              that.setData({
                photoData: data.data
              })
            }
            if (res.statusCode != 200) {
              wx.showToast({
                title: '上传失败'
              })
            }
          }
        })
      },
    })
  },

  submit:function(e){
    var that = this
    if (that.data.titleCount <= 0 || that.data.titleCount > 20 || that.data.contentCount <= 0 || that.data.contentCount > 200){
      wx.showModal({
        title: '提示',
        content: '字数不在规定范围内',
        confirmText: '确定',
      })
      return
    }
    else{
      wx.showToast({
        title: '正在上传...',
        icon: 'loading',
        mask: true,
        duration: 500
      })
    console.log(that.data.title)
    console.log(that.data.content)
    console.log(that.data.photoData)
    console.log(that.data.labelContent)
    wx.request({
      url: app.globalData.urlPath + '/saveCard',
      data: {
        userID: app.globalData.userID,
        sessionToken: app.globalData.sessionToken,
        title: that.data.title,
        content: that.data.content,
        picUrl: that.data.photoData,
        labelContent: that.data.labelContent
      },
      method:'POST',
      header: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      success: function (res) {
        console.log(res)
        
      }
    })
    }
  }
})
