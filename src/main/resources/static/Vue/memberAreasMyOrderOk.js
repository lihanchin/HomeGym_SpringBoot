new Vue({
    el:"#app",
    data:{
        currentPage:[
            // {
            //     courseName:"2小時全身深層肌肉拉筋",
            //     orderNamber:"A20211005",
            //     orderPrice:1500,
            //     paymentMethod:"信用卡",
            // },
            // {
            //     courseName:"30分鐘核心健身",
            //     orderNamber:"A20010001",
            //     orderPrice:2000,
            //     paymentMethod:"信用卡",
            // },
            // {
            //     courseName:"十分鐘瑜伽精華",
            //     orderNamber:"A20180305",
            //     orderPrice:500,
            //     paymentMethod:"信用卡",
            // },
        ],
        orderDetail:[],
        totalPage:""
    },
    mounted() {
        let  token = localStorage.getItem("Authorization")
        axios.get("http://localhost:8080/memberArea/OKOrder",{
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