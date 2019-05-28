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
    content: '',//正文内容
    images: '',//图片
    labelContent: '',//标签
    photoData: '',
    formId:'',
    num:0,
    cardId:0,
    img_ocr:''
  },

  onLoad:function(res){
    var that = this
    that.setData({
      num:res.num
    })
    console.log(that.data.num)
    if(that.data.num==1){
      console.log('准备修改')
      let prevPage = getCurrentPages()[getCurrentPages().length - 2]
      that.setData({
        title:prevPage.data.list.card.title,
        content:prevPage.data.list.card.content,
        labelContent: prevPage.data.list.labels,
        images: prevPage.data.url,
        cardId: prevPage.data.list.card.cardId,
        photoData: prevPage.data.list.card.picUrl
      })
    }
  },

  submitInfo: function (res) {
    var that = this
    that.setData({
      formId: res.detail.formId
    })
    console.log(that.data.formId)
  },

  handleTitleInput: function (e) {
    var t_text = e.detail.value.length;
    var that = this
    that.setData({
      titleCount: t_text,
      title: e.detail.value
    })
  },

  handleContentInput: function (e) {
    var t_text = e.detail.value.length;
    var that = this
    that.setData({
      contentCount: t_text,
      content: e.detail.value
    })
  },

  handleLabelInput: function (e) {
    var that = this
    that.setData({
      labelContent: e.detail.value
    })
  },

  imgChoose: function (e) {
    var that = this
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],// 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'],// 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        console.log(res)
        that.setData({
          images: res.tempFilePaths[0]
        })
        console.log(that.data.images)
        wx.uploadFile({
          url: app.globalData.urlPath + '/uploadPhoto',
          filePath: res.tempFilePaths[0],//要上传文件资源的路径 String类型 
          name: 'photo',
          header: {
            "Content-Type": "multipart/form-data"
          },
          formData: {
            'sessionToken': app.globalData.sessionToken,
            'userID': app.globalData.userID,
            'ocr':"true"
          },
          success: function (res) {
            if (res.statusCode == 200) {
              var data = JSON.parse(res.data)
              data = JSON.parse(data.data)
              console.log("photo")
              console.log(res)
              console.log(data)
              that.setData({
                photoData: data.picUrl
              })
            }
            if (res.statusCode != 200) {
              wx.showModal({
                title: '提示',
                content: '上传失败',
                confirmText: '确定',
              })
            }
          }
        })
      },
    })
  },

  imgOCR:function(e){
    var that = this
    wx.chooseImage({
      count: 1,
      sizeType: ['original', 'compressed'],// 可以指定是原图还是压缩图，默认二者都有
      sourceType: ['album', 'camera'],// 可以指定来源是相册还是相机，默认二者都有
      success: function (res) {
        console.log(res)
        wx.uploadFile({
          url: app.globalData.urlPath + '/uploadPhoto',
          filePath: res.tempFilePaths[0],//要上传文件资源的路径 String类型 
          name: 'photo',
          header: {
            "Content-Type": "multipart/form-data"
          },
          formData: {
            'sessionToken': app.globalData.sessionToken,
            'userID': app.globalData.userID,
            'ocr': "true"
          },
          success: function (res) {
            console.log(res)
            if (res.statusCode == 200) {
              var data = JSON.parse(res.data)
              data = JSON.parse(data.data)
              console.log(data)
              that.setData({
                content: data.ocrText
              })
            }
            if (res.statusCode != 200) {
              wx.showModal({
                title: '提示',
                content: '提取文字失败',
                confirmText: '确定',
              })
            }
          }
        })
      },
    })
  },

  submit: function (e) {
    var that = this
    if (that.data.titleCount <= 0 || that.data.titleCount > 20 || that.data.contentCount <= 0 || that.data.contentCount > 200) {
      wx.showModal({
        title: '提示',
        content: '字数不在规定范围内',
        confirmText: '确定',
      })
      return
    }
    else {
      wx.showToast({
        title: '正在上传...',
        icon: 'loading',
        mask: true,
        duration: 500
      })
      if(that.data.num==0){
        wx.request({
          url: app.globalData.urlPath + '/saveCard',
          data: {
            userID: app.globalData.userID,
            sessionToken: app.globalData.sessionToken,
            title: that.data.title,
            content: that.data.content,
            picUrl: that.data.photoData,
            labelContent: that.data.labelContent,
            formID:that.data.formId
          },
          method: 'POST',
          header: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          success: function (res) {
            console.log(res)
            wx.switchTab({
              url: '../list/list',
            })
          }
        })
      }
      if(that.data.num==1){
        console.log(that.data.title)
        console.log(that.data.content)
        console.log(that.data.photoData)
        console.log(that.data.labelContent)
        console.log(that.data.cardId)
        wx.request({
          url: app.globalData.urlPath + '/editCard',
          data: {
            userID: app.globalData.userID,
            sessionToken: app.globalData.sessionToken,
            cardId:that.data.cardId,
            title: that.data.title,
            content: that.data.content,
            picUrl: that.data.photoData,
            labelContent: that.data.labelContent,
            formID: that.data.formId
          },
          method: 'POST',
          header: {
            "Content-Type": "application/x-www-form-urlencoded"
          },
          success: function (res) {
            console.log(res)
            wx.switchTab({
              url: '../list/list',
            })
          }
        })
      }
    }
  }
})
