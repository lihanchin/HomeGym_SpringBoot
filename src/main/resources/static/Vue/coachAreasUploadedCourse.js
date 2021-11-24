new Vue({
    el:"#course",
    data:{
        currentPage:[],
        totalPage:"",
        cacheContent:{},
        cacheTitle:"",
        keyword:"",
        pageNo:1
    },
    methods: {

        searchCourse(){
            let  token = localStorage.getItem("Authorization")
            axios.get(`/coachArea/keyword?keyword=`+this.keyword,{
                headers: {
                    Authorization: token
                }
            }).then((res) =>{
                this.currentPage = res.data.courseList
                this.totalPage = res.data.totalPage
            })
        },
        clickPage(index){
            let page = index+1
            this.pageNo = page
            if (this.keyword != "" &this.keyword !=null) {
                axios.get("/coachArea/keyword?keyword="+this.keyword+"&page="+page,{
                    headers: {
                        Authorization: token
                    }
                }).then((res) =>{
                    this.currentPage = res.data.currentPage;
                    this.totalPage = res.data.totalPage;
                })
            }else{
                axios.get("/coachArea/mycourse?page="+page,{
                    headers: {
                        Authorization: token
                    }
                }).then((res) =>{
                    this.currentPage = res.data.currentPage;
                    this.totalPage = res.data.totalPage;
                })
            }
        },
    },
    mounted() {
        let  token = localStorage.getItem("Authorization")
        axios.get("/coachArea/mycourse",{
            headers: {
                Authorization: token
            }
        }).then((res) =>{
            this.currentPage = res.data.currentPage
            this.totalPage = res.data.totalPage
        })
    },
});
