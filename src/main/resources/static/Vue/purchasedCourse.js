let id = (location.search.split('='))[1]
console.log(id);

new Vue({
    el:'#app',
    data: {
        //留言跟評價輸入
        courseComment:{
            CommentInput:'',
            star:'',
        },
        //FQA輸入
        fqaInput:{
            fqaContent:'',
        },
        //FQAReply輸入
        fqaReplyInput:{
            fqaReplyContent:'',
        },

        fqaUser:'',
        fqaUserIndex:'',
        //接收後端留言評價
        comment:[ ],
        //接收後端留言評價
        coachName:[ ],
        //接收後課程資料
        course:{ },
        //接收後端教練資料
        coach:{ },
        //接收後端FQA
        fqa:[],

        member:{
            memberName:"User",
            memberImg:"https://fakeimg.pl/40x40/"
        }

    },
    methods: {

        //送出留言
        createComment(){
            console.log("進入方法")

            //預防空白字串 傳送出去
            if(!this.courseComment.CommentInput) return false;
            let star = this.courseComment.star; //星星數radio
            let comment =this.courseComment.CommentInput; //留言input
            const date = new Date();
            let createdTime = date.toLocaleString(); //創建時間
            console.log("準備請求")
            axios.post(`http://localhost:8080/course/addComment`,
                {
                    // id : this.courseComment.length,
                    // name:this.member.memberName,
                    star : star,
                    commentContent : comment,
                    commentCreateTime:date
                }
            ).then((res) =>{

                console.log(res);
                console.log("請求結束")
            })


            // //畫面中推出留言
            let createditem = {
                id:this.courseComment.length,
                name:this.member.memberName,
                img:this.member.memberImg,
                star:star,
                commentContent:comment,
                commentCreateTime:createdTime
            };
            this.course.push(createditem);
            //點擊後 input清空
            this.CommentInput ='';


        },

        //送出FQA
        creatFqa(){
            //預防空白字串 傳送出去
            console.log("方法近來")
            if(!this.fqaInput.fqaContent) return false;
            console.log("通過檢查")
            let courseId = id;
            let comment =this.fqaInput.fqaContent; //留言input
            const date = new Date();
            let createdTime = date.toLocaleString(); //創建時間
            console.log("準備請求")
            axios.post(`http://localhost:8080/course/addFQA/${courseId}`,
                {
                    fqaContent:comment,
                    fqaCreateTime:date,
                },
            ).then((res) =>{
                console.log(res);
            })
            console.log("請求結束")

            this.fqa.push({
                id:this.courseFqa.length,
                name:this.member.memberName,
                img:this.member.memberImg,
                comment:comment,
                createdTime:createdTime,
            });
            //點擊後 input清空
            this.fqaInput.fqaContent ='';
        },


        getFqaUser(fqaId){
            var index = fqaId - 1;
            console.log("fqaId="+fqaId)
            console.log("index="+index)
            var userName = this.fqa[index].memberName;
            this.fqaUserIndex = fqaId;
            this.fqaUser = userName;
        },

        //送出FQAReply
        creatFqaReply(){
            console.log("方法近來")
            //預防空白字串 傳送出去
            if(!this.fqaReplyInput.fqaReplyContent) return false;
            let fqaId = this.fqaUserIndex;
            let index = fqaId-1;
            let comment =this.fqaReplyInput.fqaReplyContent; //留言input

            const date = new Date();
            let createdTime = date.toLocaleString(); //創建時間
            console.log("準備請求")
            axios.post(`http://localhost:8080/course/addFQAReply/${fqaId}`,
                {
                    fqaReplyContent:comment,
                    fqaCreateTime:date,
                },
                ).then((res) =>{
                console.log(res);
            })
            console.log("請求結束")

            let courseFqaReplyContent = {
                name:this.member.memberName,
                img:this.member.memberImg,
                comment:comment,
                createdTime:createdTime,
            }
            this.fqa[id].fqaReplies.push(courseFqaReplyContent);

            //點擊後 input清空
            this.fqaReplyInput.fqaReplyContent ='';


        },

        //取消回覆fqa
        closeFqa(){
            this.fqaUser ='';
            this.fqaReplyInput.fqaReplyContent ='';
            this.fqaUserIndex='';
        }
    },
    mounted() {

        axios.get("http://localhost:8080/store/"+id).then((res) =>{ //memberAreasIntroduction.json
            console.log(res);
            this.course = res.data.course
            this.coach = res.data.coach
            this.coachName = res.data.coachName
            this.comment = res.data.commentlist
        });

        axios.get("http://localhost:8080/course/"+id).then((res) =>{ //memberAreasIntroduction.json
            console.log(res);
        this.fqa = res.data
        });
        //
        // axios.get("http://localhost:3000/member").then((res) =>{ //memberAreasIntroduction.json
        // this.member = res.data
        // });
    }
});