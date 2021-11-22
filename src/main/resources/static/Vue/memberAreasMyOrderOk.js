let  token = localStorage.getItem("Authorization")
new Vue({
    el:"#app",
    data:{
        currentPage:[],
        orderDetail:[],
        totalPage:"",
        pageNo:1
    },
    methods:{
        clickPage(index){
            let pageNo = index+1
            this.pageNo = pageNo
            axios.get("/memberArea/OKOrder?page="+pageNo,{
                headers: {
                    Authorization: token
                }
            }).then((res) =>{
                this.currentPage = res.data.currentPage;
                this.orderDetail = res.data.orderDetail;
                this.totalPage = res.data.totalPage;
            })
        },
    },
    mounted() {
        axios.get("/memberArea/OKOrder",{
                headers: {
                    Authorization: token
                }
            }).then((res) =>{
                this.currentPage = res.data.currentPage;
                this.orderDetail = res.data.orderDetail;
                this.totalPage = res.data.totalPage;
        })
    },
})