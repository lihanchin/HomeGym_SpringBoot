

let app = new Vue({
    el:'#app',
    data: {
      //左側todolist
      todos:[
        {
          id:'1',
          src:'./video_2.mp4'
        },
        {
          id:'2',
          src:'./video_2.mp4'
        },
        {
          id:'3',
          src:'./video.mp4'
        },
      ],
      //右側資料庫
      videoDatabase:[
        {
          id:'1',
          name:'核心健身',
          tag:'背部',
          time:'13:00',
          src:'./video_2.mp4'
        },
        {
          id:'2',
          name:'抬腳核心',
          tag:'腳部',
          time:'20:00',
          src:'./video.mp4'
        },
        {
          id:'3',
          name:'腹部抬舉',
          tag:'腹部',
          time:'15:50',
          src:'./video_2.mp4'
        },
        {
          id:'4',
          name:'雙腳跳',
          tag:'腳部',
          time:'19:00',
          src:'./video.mp4'
        },
        {
          id:'5',
          name:'拉筋放鬆',
          tag:'全身',
          time:'10:00',
          src:'./video_2.mp4'
        },
      ]
    },
    methods: {
      //新增
      addTodo:function(key){
        let timestamp = Math.floor(Date.now());
        let src = this.videoDatabase[key].src;
        this.todos.push({
          id: timestamp,
          src: src
        })
      },
      //移除
      removeTodo:function(key){
        this.todos.splice(key,1);
      }
    }
  })