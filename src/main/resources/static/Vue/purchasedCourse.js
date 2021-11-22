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
        commentList:[],
        totalPage:"",
        //接收後端留言評價
        coachName:[ ],
        //接收後課程資料
        course:{
            starAndComment:[]
        },
        //接收後端教練資料
        coach:{ },
        //接收後端FQA
        fqa:[],
        //接收後端會員
        member:{
            memberName:"",
            memberImage:""
        },
        starAndComment:{
            courseStar:0,
            commentAmount:0
        }
    },
    methods: {

        //送出留言
        createComment(){
            //預防空白字串 傳送出去
            if(!this.courseComment.CommentInput) return false;
            let star = this.courseComment.star; //星星數radio
            let comment =this.courseComment.CommentInput; //留言input
            axios.post(`/course/addComment/${id}`,
                {
                    star : star,
                    commentContent : comment,
                }, {
                    headers: {
                        Authorization: token
                    }
                }).then((res) =>{
                axios.get("/course/"+id+"/showComment").then((res) =>{
                    this.commentList = res.data.courseComment
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
            if(!this.fqaInput.fqaContent) return false;
            let comment =this.fqaInput.fqaContent; //留言input

            axios.post(`/course/addFQA/${id}`,
                {
                    fqaContent:comment,
                }, {
                    headers: {
                        Authorization: token
                    }
                }).then((res) =>{
                    axios.get("/course/"+id, {
                        headers: {
                            Authorization: token
                        }
                    }).then((res) =>{
                        this.member.memberName = res.data.name
                        this.member.memberImage = 'data:'+res.data.mimeType+';base64,'+res.data.memberImage
                        this.fqa = res.data.fqaList
                    })
                 })

            //點擊後 input清空
            this.fqaInput.fqaContent ='';
        },


        getFqaUser(key){
            var userName = this.fqa[key].memberName;
            this.fqaUserIndex = this.fqa[key].fqaId;
            this.fqaUser = userName;
        },

        //送出FQAReply
        creatFqaReply(){
            //預防空白字串 傳送出去
            if(!this.fqaReplyInput.fqaReplyContent) return false;
            let fqaId = this.fqaUserIndex;
            let comment =this.fqaReplyInput.fqaReplyContent; //留言input
            axios.post(`/course/addFQAReply/${fqaId}`,
                {
                    fqaReplyContent:comment,
                },{
                    headers: {
                        Authorization: token
                    }
                }).then((res) =>{
                    axios.get("/course/"+id, {
                        headers: {
                            Authorization: token
                        }
                    }).then((res) =>{
                        this.member.memberName = res.data.name
                        this.member.memberImage = 'data:'+res.data.mimeType+';base64,'+res.data.memberImage
                        this.fqa = res.data.fqaList
                    })
            })
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
        },

        clickPage(index){
            let pageNo = index+1
            console.log(pageNo)
                axios.get("/course/"+id+"/showComment?pageNo="+pageNo).then((res) =>{
                    this.commentList = res.data.courseComment;
                    this.totalPage = res.data.totalPage;
                })
        },
    },
    mounted() {
        axios.get("/store/"+id).then((res) =>{
            if(res.data.course.starAndComment!=null){
                this.starAndComment = res.data.course.starAndComment
            }
            this.course = res.data.course
            this.coach = res.data.coach
            this.coachName = res.data.coachName
        });

        axios.get("/course/"+id, {
            headers: {
                Authorization: token
            }
        }).then((res) =>{
            this.member.memberName = res.data.name
            this.member.memberImage = 'data:'+res.data.mimeType+';base64,'+res.data.memberImage
            this.fqa = res.data.fqaList
        })
        //     .catch(error =>{
        //     console.log(error.response.data.message)
        //     window.alert("請重新登入");
        //     window.location.replace("/");
        // })

        axios.get("/course/"+id+"/showComment").then((res) =>{
            this.commentList = res.data.courseComment
            this.totalPage = res.data.totalPage
        });
    }
});