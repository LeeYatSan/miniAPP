var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[],
    url:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    that.getPreCard()
  },

  getPreCard:function(){
    var that = this
    let prevPage = getCurrentPages()[getCurrentPages().length - 2] 
    console.log(prevPage.data.index)
    that.setData({
      list:prevPage.data.list[prevPage.data.index],
      url: 'http://134.175.11.69:8080/images/' + app.globalData.userID + '/'+prevPage.data.list[prevPage.data.index].card.picUrl
    })
    console.log("查看")
    console.log(that.data.list)
    console.log(that.data.url)
  },

  editCard:function(e){
    wx.navigateTo({
      url: '/pages/addList/addList?num=1',
    })
  },

  delCard:function(e){
    var that = this
    let prevPage = getCurrentPages()[getCurrentPages().length - 2] 
    wx.showModal({
      title: '提示',
      content: '确认删除该卡片',
      success(res) {
        if (res.confirm) {
          console.log('用户点击确定')
          wx.request({
            url: app.globalData.urlPath + '/delCard',
            data: {
              userID: app.globalData.userID,
              cardID: prevPage.data.list[prevPage.data.index].card.cardId,
              sessionToken: app.globalData.sessionToken
            },
            method: 'POST',
            header: {
              "Content-Type": "application/x-www-form-urlencoded"
            },
            success:function(res){
              console.log(res)
              if(res.statusCode==200){
                wx.showToast({
                  title: '删除成功',
                })
                wx.navigateTo({
                  url: '/pages/list/list',
                })
              }
              else{
                wx.showModal({
                  title: '提示',
                  content: '删除不成功',
                })
              }
            }
          })
        } else if (res.cancel) {
          console.log('用户点击取消')
        }
      }
    })
  }
})
