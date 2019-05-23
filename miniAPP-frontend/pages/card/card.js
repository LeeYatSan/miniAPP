var app = getApp();
let touchDotX = 0; //X按下时坐标
let touchDotY = 0; //y按下时坐标
Page({
  data: {
    imgsIndex:0,
    imgs: [{ "name": "cake", "pic_url": "/images/cake.jpg", "desc": "描述" }, { "name": "carrot", "pic_url": "/images/carrot.jpg", "desc": "描述" }],
    imgUrl:[],
    cardId:'',
    formId: '',
    items: [
      { name: '1', value: '记住', checked: 'true' },
      { name: '2', value: '忘记', }
    ],
    isRemember:false,
    isFront1: true,
    animationData1: {},
    animationData2: {},
    animationData3: {},
    ballTop1: 240,
    ballTop2: 230,
    ballTop3: 220,
    ballWidth1: 680,
    ballWidth2: 640,
    ballWidth3: 605,
    index1: 3,
    index2: 2,
    index3: 1,
    statusBarHeight: getApp().globalData.statusBarHeight,
  },
  onLoad(options) {
    this.getPhotoUrl();
   },

  /**
   *  卡片1手势
   */
  touchstart1: function (event) {
    touchDotX = event.touches[0].pageX; // 获取触摸时的原点
    touchDotY = event.touches[0].pageY;
    console.log("起始点的坐标X:" + touchDotX);
    console.log("起始点的坐标Y:" + touchDotY);
  },
  // 移动结束处理动画
  touchend1: function (event) {
    // 手指离开屏幕时记录的坐标
    let touchMoveX = event.changedTouches[0].pageX;
    let touchMoveY = event.changedTouches[0].pageY;
    // 起始点的坐标(x0,y0)和手指离开时的坐标(x1,y1)之差
    let tmX = touchMoveX - touchDotX;
    let tmY = touchMoveY - touchDotY;
    // 两点横纵坐标差的绝对值
    let absX = Math.abs(tmX);
    let absY = Math.abs(tmY);
    //起始点的坐标(x0,y0)和手指离开时的坐标(x1,y1)之间的距离
    let delta = Math.sqrt(absX * absX + absY * absY);
    console.log('起始点和离开点距离:' + delta + 'px');
    // 如果delta超过60px（可以视情况自己微调）,判定为手势触发
    if (delta >= 60) {
      // 如果 |x0-x1|>|y0-y1|,即absX>abxY,判定为左右滑动
      if (absX > absY) {
        // 如更tmX<0，即(离开点的X)-(起始点X)小于0 ，判定为左滑
        if (tmX < 0) {
          console.log("左滑=====");
          // 执行左滑动画
          this.Animation1(-500);
          // 如更tmX>0，即(离开点的X)-(起始点X)大于0 ，判定为右滑
        } else {
          console.log("右滑=====");
          // 执行右滑动画
          this.Animation1(500);
        }
        // 如果 |x0-x1|<|y0-y1|,即absX<abxY,判定为上下滑动
      } else {
        // 如更tmY<0，即(离开点的Y)-(起始点Y)小于0 ，判定为上滑
        if (tmY < 0) {
          console.log("上滑动=====");
          this.setData({
            isFront1: !this.data.isFront1
          });
          // 如更tmY>0，即(离开点的Y)-(起始点Y)大于0 ，判定为下滑
        } else {
          console.log("下滑动=====");
          this.setData({
            isFront1: !this.data.isFront1
          });
        }
      }
    } else {
      console.log("手势未触发=====");
    }

    // 让上一张卡片展现正面（如果之前翻转过的话）
    this.setData({
      isFront3: true,
    });
  },

  /**
   *  卡片2手势
   */
  touchstart2: function (event) {
    touchDotX = event.touches[0].pageX; // 获取触摸时的原点
    touchDotY = event.touches[0].pageY;

    console.log("起始点的坐标X:" + touchDotX);
    console.log("起始点的坐标Y:" + touchDotY);

  },
  // 移动结束处理动画
  touchend2: function (event) {
    let touchMoveX = event.changedTouches[0].pageX;
    let touchMoveY = event.changedTouches[0].pageY;
    let tmX = touchMoveX - touchDotX;
    let tmY = touchMoveY - touchDotY;
    let absX = Math.abs(tmX);
    let absY = Math.abs(tmY);
    let delta = Math.sqrt(absX * absX + absY * absY);
    console.log('起始点和离开点距离:' + delta + 'px');
    if (delta >= 60) {
      if (absX > absY) {
        if (tmX < 0) {
          console.log("左滑=====");
          this.Animation2(-500);
        } else {
          console.log("右滑=====");
          this.Animation2(500);
        }
      } else {
        if (tmY < 0) {
          console.log("上滑动=====");
          this.setData({
            isFront2: !this.data.isFront2
          });
        } else {
          console.log("下滑动=====");
          this.setData({
            isFront2: !this.data.isFront2
          });
        }

      }
    } else {
      console.log("手势未触发=====");
    }

    this.setData({
      isFront1: true,
    });

  },

  /**
   *  卡片3手势
   */
  touchstart3: function (event) {
    touchDotX = event.touches[0].pageX; // 获取触摸时的原点
    touchDotY = event.touches[0].pageY;
    console.log("起始点的坐标X:" + touchDotX);
    console.log("起始点的坐标Y:" + touchDotY);
  },
  // 移动结束处理动画
  touchend3: function (event) {
    let touchMoveX = event.changedTouches[0].pageX;
    let touchMoveY = event.changedTouches[0].pageY;
    let tmX = touchMoveX - touchDotX;
    let tmY = touchMoveY - touchDotY;
    let absX = Math.abs(tmX);
    let absY = Math.abs(tmY);
    let delta = Math.sqrt(absX * absX + absY * absY);
    console.log('起始点和离开点距离:' + delta + 'px');
    if (delta >= 60) {
      if (absX > absY) {
        if (tmX < 0) {
          console.log("左滑=====");
          this.Animation3(-500);
        } else {
          console.log("右滑=====");
          this.Animation3(500);
        }
      } else {

        if (tmY < 0) {
          console.log("上滑动=====");
          this.setData({
            isFront3: !this.data.isFront3
          });
        } else {
          console.log("下滑动=====");
          this.setData({
            isFront3: !this.data.isFront3
          });
        }
      }
    } else {
      console.log("手势未触发=====");
    }

    this.setData({
      isFront2: true,
    });

  },

  /**
   * 卡片1:
   * 左滑动右滑动动画
   */
  Animation1: function (translateXX) {
    var that = this;
    var index = that.data.imgsIndex;
    console.log("下标" + index);
    if (index> that.data.imgs.length-2){
      console.log("true");
      var count = 0;
      that.setData({
        imgsIndex: count
      })
    }else{
      console.log("fasle");
      index = index + 1;
      that.setData({
        imgsIndex: index
      })
    }
    console.log("索引："+that.data.imgs.length);
    console.log("长度："+that.data.imgs.length);
    this.rememberOrNot();
    let animation = wx.createAnimation({
      duration: 680,
      timingFunction: "ease",
    });
    this.animation = animation;

    if (translateXX > 0) {
      this.animation.translateY(0).rotate(20).translateX(translateXX).opacity(0).step();
    } else {
      this.animation.translateY(0).rotate(-20).translateX(translateXX).opacity(0).step();
    }

    this.animation.translateY(0).translateX(0).opacity(1).rotate(0).step({
      duration: 10
    });

    this.setData({
      animationData1: this.animation.export(),
    });

    setTimeout(() => {
      this.setData({
        ballTop1: 220,
        ballLeft1: -302.5,
        ballWidth1: 605,
        index1: 1,

        ballTop2: 240,
        ballLeft2: -340,
        ballWidth2: 680,
        index2: 3,

        ballTop3: 230,
        ballLeft3: -320,
        ballWidth3: 640,
        index3: 2,
      })
    }, 500);
  },
  checkboxChange: function (e) {
    console.log("tests" + e.detail.value);
    var that = this;
    var index = e.target.dataset.index;
    var arr = e.detail.value;
    var new_itmes = [
      { name: '1', value: '记住'},
      { name: '2', value: '忘记', }
    ]
    //判断是否被选中
    if (arr.length > 1) {
      for (var i = 0; i < new_itmes.length; i++) {
        if (new_itmes[i]['name'] == arr[1]) {
          new_itmes[new_itmes[i]['name'] - 1]['checked'] = 'false'
          if (new_itmes[i]['name'] == '记住'){
            that.setData({
              isRemember:true
            })
          }else{
            that.setData({
              isRemember: false
            })
          }
        }
      }
      that.setData({
        items: new_itmes
      });
      return;
    }
  },
  rememberOrNot:function(){
    wx.request({
      url: app.globalData.urlPath + '/rememberCardOrNot',
      data: {
        userID: app.globalData.userID,
        sessionToken: app.globalData.sessionToken,
        cardID:1,
        remember: that.data.isRemember,
        formID:''
      },
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
          console.log(res.data);
      }
    });
  },

  getPhotoUrl:function(){
    var that = this;
    wx.request({
      url: 'http://localhost:8081/getAllCardsByUserID',
      data: {
        userID: app.globalData.userID,
        sessionToken: app.globalData.sessionToken
      },
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        that.setData({
          imgUrl:res.data
        })
      }
    });
  },
  sharePhoto:function(){
    var that = this;
    wx.request({
      url: 'http://localhost:8081/shareCard',
      data: {
        userID: app.globalData.userID,
        sessionToken: app.globalData.sessionToken,
        cardID: 1,
        remember: true,
        formID:''
      },
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        that.setData({
          imgUrl: res.data
        })
      }
    });
  },
  refreshPhoto:function(){
    var that = this;
    wx.request({
      url: 'http://localhost:8081/getUnFamiliarCard',
      data: {
        userID: app.globalData.userID,
        sessionToken: app.globalData.sessionToken,
        cardID: 1,
        remember: true,
        formID:''
      },
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        that.setData({
          imgUrl:res.data
        })
      }
    });
  }
})