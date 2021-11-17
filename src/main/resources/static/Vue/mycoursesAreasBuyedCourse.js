let  token = localStorage.getItem("Authorization")
new Vue({
    el:"#app",
    data:{
        courseList:{},
        totalPage:"",
        shoppingCourse:[],
    },
    mounted() {
        axios.get("/myCourses/allCourse",{
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