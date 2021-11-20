let  token = localStorage.getItem("Authorization")
new Vue({
    el:"#app",
    data:{
        currentPage:[],
        orderDetail:[],
        totalPage:""
    },
    methods:{
        clickPage(index){
            let pageNo = index+1
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