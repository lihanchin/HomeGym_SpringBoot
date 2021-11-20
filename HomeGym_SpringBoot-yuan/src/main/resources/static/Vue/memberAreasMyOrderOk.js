new Vue({
    el:"#app",
    data:{
        currentPage:[],
        orderDetail:[],
        totalPage:""
    },
    mounted() {
        let  token = localStorage.getItem("Authorization")
        axios.get("/memberArea/OKOrder",{
                headers: {
                    Authorization: token
                }
            }).then((res) =>{
                this.currentPage = res.data.currentPage;
                this.orderDetail = res.data.orderDetail;
                this.totalPage = res.data.totalPage;
            console.log(res.data)

            // window.location.reload()
            // this.orderOk = res.data.OKOrder
        })
    },
})