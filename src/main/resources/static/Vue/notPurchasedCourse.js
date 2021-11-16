let id = (location.search.split('='))[1]
console.log(id);
new Vue({
    el:'#app',
    data: {
        keep:{
            isLike: true,
            islinkIcon:`<i class="fa fa-heart" aria-hidden="true"></i> 已加入收藏清單`,
            notLinkIcon:`<i class="fa fa-heart-o" aria-hidden="true"></i> 加入收藏清單`,
        },
        shopping:{
            isLike: true,
            islinkIcon:`<i class="fa fa-heart" aria-hidden="true"></i> 已加入購物車`,
            notLinkIcon:`<i class="fa fa-heart-o" aria-hidden="true"></i> 加入購物車`,
        },
        //從後端接收的資料
        courseComment:[ ],
        course:{},
        coach:{},
        coachName:"",
        totalPage:"",
        starAndComment:{
            courseStar:0,
            commentAmount:0
        }
    },
    methods: {
        keepToggle: function(){
            this.keep.isLike = !this.keep.isLike;
            console.log(this.keep.isLike);
        },
        shoppingToggle: function(){
            this.shopping.isLike = !this.shopping.isLike;
            console.log(this.shopping.isLike);
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

        axios.get("/course/"+id+"/showComment").then((res) =>{
            this.courseComment = res.data.courseComment
            this.totalPage = res.data.totalPage
        });

    }
});