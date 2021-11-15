new Vue({
    el:"#app",
    data:{
        courseList:{},
        totalPage:"",
        shoppingCourse:[],
    },
    mounted() {
        let  token = localStorage.getItem("Authorization")
        axios.get("/myCourse/allCourse",{
            headers: {
                Authorization: token
            }
        }).then((res) =>{
            console.log(res.data)
            this.courseList = res.data.courseList
            this.totalPage  = res.data.totalPage
            this.shoppingCourse=res.data.firstPage;

        })
    },
});