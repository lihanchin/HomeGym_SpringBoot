new Vue({
    el: "#app",
    data: {
        currentPage:[],
        orderDetail:[],
        totalPage:""
    },
    mounted() {
        let  token = localStorage.getItem("Authorization")
        axios.get("http://localhost:8080/memberArea/NGOrder",{
            headers: {
                Authorization: token
            }
        }).then((res) =>{
            this.currentPage = res.data.currentPage;
            this.orderDetail = res.data.orderDetail;
            this.totalPage = res.data.totalPage;
            console.log(res.data)

        })
    },
})