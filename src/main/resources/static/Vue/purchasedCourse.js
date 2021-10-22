new Vue({
    el:'#app',
    data: {
        CommentInput:'',
        star:'',
        fqaInput:'',
        fqaReplyInput:'',
        fqaUser:'',
        fqaUserIndex:'',
        courseComment:[
            {
                id:1,
                name:"Jason",
                img:"https://fakeimg.pl/40x40/",
                evaluation: 3,
                comment:"我覺得很棒",
                createdTime:"2021/9/27 下午2:40:22",
            },
            {
                id:2,
                name:"John",
                img:"https://fakeimg.pl/40x40/",
                evaluation: 5,
                comment:"太棒了",
                createdTime:"2021/9/14 下午11:40:41"
            },
            {
                id:3,
                name:"abby",
                img:"https://fakeimg.pl/40x40/",
                evaluation: 1,
                comment:"就很爛",
                createdTime:"2021/9/30 下午10:40:12"
            },
        ],
        courseFqa:[
            {
                id:1,
                name:"Cindy",
                img:"https://fakeimg.pl/40x40/",
                comment:"我想請問一下抬腿的姿勢如何做才是正確的",
                createdTime:"2021/10/17 下午5:40:22",
                courseFqaReply:[
                    {
                        id:1,
                        name:"John",
                        img:"https://fakeimg.pl/40x40/",
                        comment:"你可以動一下腦袋",
                        createdTime:"2021/10/19 下午10:50:12",
                    },
                    {
                        id:1,
                        name:"Jason",
                        img:"https://fakeimg.pl/40x40/",
                        comment:"對啊！不能動一下腦袋嗎",
                        createdTime:"2021/10/20 下午11:50:12",
                    },
                ],
            },
            {
                id:1,
                name:"Dindy",
                img:"https://fakeimg.pl/40x40/",
                comment:"我想請問一下抬腿的姿勢如何做才是正確的",
                createdTime:"2021/10/17 下午5:40:22",
                courseFqaReply:[
                    {
                        id:1,
                        name:"John",
                        img:"https://fakeimg.pl/40x40/",
                        comment:"你可以動一下腦袋",
                        createdTime:"2021/10/27 下午10:50:12",
                    },
                ],
            }
        ],
        courseContent:{
            courseEquipment: "瑜珈墊",
            courseSuitableForPeople:"想學瑜伽的人",
            courseIntroduction:"課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹課程介紹",
        },
        coaching:{
            coachingName:"Jason",
            coachingExpertise:"瑜伽",
            coachingExperience:"瑜伽教練"
        },
        member:{
            memberName:"User",
            memberImg:"https://fakeimg.pl/40x40/"
        }

    },
    methods: {
        createComment(){

            //預防空白字串 傳送出去
            if(!this.CommentInput) return false;

            let star = this.star; //星星數radio
            let comment =this.CommentInput; //留言input
            const date = new Date();
            let createdTime = date.toLocaleString(); //創建時間
            let createditem = {
                id : this.courseComment.length,
                id:this.courseComment.length,
                name:this.member.memberName,
                img:this.member.memberImg,
                evaluation:star,
                comment:comment,
                createdTime:createdTime
            };
            this.courseComment.push(createditem);

            //點擊後 input清空
            this.CommentInput ='';

            axios.post(`http://localhost:3000/courseComment`,{
                id : this.courseComment.length,
                name:this.member.memberName,
                img:this.member.memberImg,
                evaluation : star,
                comment : comment,
                createdTime:createdTime

            }).then((res) =>{
                console.log(res);
            })

        },
        creatFqa(){
            //預防空白字串 傳送出去
            if(!this.fqaInput) return false;

            let comment =this.fqaInput; //留言input
            const date = new Date();
            let createdTime = date.toLocaleString(); //創建時間
            this.courseFqa.push({
                id:this.courseFqa.length,
                name:this.member.memberName,
                img:this.member.memberImg,
                comment:comment,
                createdTime:createdTime,
                courseFqaReply:[]
            });
            //點擊後 input清空
            this.fqaInput ='';

            axios.post(`http://localhost:3000/courseFqa`,{
                id:this.courseFqa.length,
                name:this.member.memberName,
                img:this.member.memberImg,
                comment:comment,
                createdTime:createdTime,
                courseFqaReply:[]

            }).then((res) =>{
                console.log(res);
            })


        },
        getFqaUser(index){

            var userName = this.courseFqa[index].name;
            this.fqaUserIndex = index;
            this.fqaUser = userName;

            // console.log(get);
        },
        creatFqaReply(){
            
            //預防空白字串 傳送出去
            if(!this.fqaReplyInput) return false;

            let comment =this.fqaReplyInput; //留言input
            let timestamp = Math.floor(Date.now()); //用時間建id
            const date = new Date();
            let createdTime = date.toLocaleString(); //創建時間
            let id = this.fqaUserIndex;
            // let array = [];
            let courseFqaReplyContent = {
                id:timestamp,
                name:this.member.memberName,
                img:this.member.memberImg,
                comment:comment,
                createdTime:createdTime,
            }


            this.courseFqa[id].courseFqaReply.push(courseFqaReplyContent);
            //點擊後 input清空
            this.fqaReplyInput ='';

            
            console.log(this.courseFqa[id].courseFqaReply);
            
            axios.post(`http://localhost:3000/courseFqa/${id}`,{
                data : courseFqaReplyContent


            }).then((res) =>{
                console.log(res);
            })


        },
        closeFqa(){
            this.fqaUser ='';
        }
    },
    mounted() {
        axios.get("http://localhost:3000/courseComment").then((res) =>{ //memberAreasIntroduction.json
        this.courseComment = res.data
        });

        axios.get("http://localhost:3000/courseFqa").then((res) =>{ //memberAreasIntroduction.json
        this.courseFqa = res.data
        });

        axios.get("http://localhost:3000/courseContent").then((res) =>{ //memberAreasIntroduction.json
        this.courseContent = res.data
        });

        axios.get("http://localhost:3000/coaching").then((res) =>{ //memberAreasIntroduction.json
        this.coaching = res.data
        });

        axios.get("http://localhost:3000/member").then((res) =>{ //memberAreasIntroduction.json
        this.member = res.data
        });
    }
});