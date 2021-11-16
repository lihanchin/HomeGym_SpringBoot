let id = (location.search.split('='))[1]
console.log(id);
let  token = localStorage.getItem("Authorization")
new Vue({
    el:'#app',
    data: {
        //留言跟評價輸入
        courseComment:{
            CommentInput:'',
            star:'3',
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
        totalPage:"",
        //接收後端留言評價
        coachName:[ ],
        //接收後課程資料
        course:{ },
        //接收後端教練資料
        coach:{ },
        //接收後端FQA
        fqa:[],
        //接收後端會員
        member:{
            memberName:"",
            memberImage:""
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
            console.log("準備請求")

            axios.post(`/course/addComment/${id}`,
                {
                    star : star,
                    commentContent : comment,
                }, {
                    headers: {
                        Authorization: token
                    }
                }).then((res) =>{
                console.log(res);
                console.log("請求結束")
                axios.get("/course/"+id+"/showComment").then((res) =>{
                    this.comment = res.data.courseComment
                    this.totalPage = res.data.totalPage
                });
            })

            //點擊後 input清空
            this.courseComment.CommentInput ='';
            this.courseComment.star ='';

        },

        //送出FQA
        creatFqa(){
            //預防空白字串 傳送出去
            console.log("方法近來")
            if(!this.fqaInput.fqaContent) return false;
            console.log("通過檢查")
            let comment =this.fqaInput.fqaContent; //留言input
            console.log("準備請求")
            axios.post(`/course/addFQA/${id}`,
                {
                    fqaContent:comment,
                }, {
                    headers: {
                        Authorization: token
                    }
                }).then((res) =>{
                    console.log(res);
                    axios.get("/course/"+id, {
                        headers: {
                            Authorization: token
                        }
                    }).then((res) =>{
                        console.log(res);
                        this.member.memberName = res.data.name
                        this.member.memberImage = 'data:'+res.data.mimeType+';base64,'+res.data.memberImage
                        this.fqa = res.data.fqaList
                    })
                 })
            console.log("請求結束")

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
            console.log("準備請求")
            axios.post(`/course/addFQAReply/${fqaId}`,
                {
                    fqaReplyContent:comment,
                },{
                    headers: {
                        Authorization: token
                    }
                }).then((res) =>{
                    console.log(res);
                    axios.get("/course/"+id, {
                        headers: {
                            Authorization: token
                        }
                    }).then((res) =>{
                        console.log(res);
                        this.member.memberName = res.data.name
                        this.member.memberImage = 'data:'+res.data.mimeType+';base64,'+res.data.memberImage
                        this.fqa = res.data.fqaList
                    })
            })
            console.log("請求結束")

            //點擊後 input清空
            this.fqaReplyInput.fqaReplyContent ='';
            this.fqaUserIndex='';
            this.fqaUserIndex='';
        },

        //取消回覆fqa
        closeFqa(){
            this.fqaUser ='';
            this.fqaReplyInput.fqaReplyContent ='';
            this.fqaUserIndex='';
        }
    },
    mounted() {
        axios.get("/store/"+id).then((res) =>{
            console.log(res);
            this.course = res.data.course
            this.coach = res.data.coach
            this.coachName = res.data.coachName
            this.comment = res.data.commentlist
        });

        axios.get("http://localhost:8080/course/"+id, {
            headers: {
                Authorization: token
            }
        }).then((res) =>{
            console.log(res);
            this.member.memberName = res.data.name
            this.member.memberImage = 'data:'+res.data.mimeType+';base64,'+res.data.memberImage
            this.fqa = res.data.fqaList
        }).catch(error =>{
            console.log(error.response.data.message)
            window.alert("請重新登入");
            window.location.replace("/");
        })

        axios.get("/course/"+id+"/showComment").then((res) =>{
            this.comment = res.data.courseComment
            this.totalPage = res.data.totalPage
        });
    }
});