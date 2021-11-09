new Vue({
    el:"#app",
    data:{
        orderOk:[
            {
                courseName:"2小時全身深層肌肉拉筋",
                orderNamber:"A20211005",
                orderPrice:1500,
                paymentMethod:"信用卡",
            },
            {
                courseName:"30分鐘核心健身",
                orderNamber:"A20010001",
                orderPrice:2000,
                paymentMethod:"信用卡",
            },
            {
                courseName:"十分鐘瑜伽精華",
                orderNamber:"A20180305",
                orderPrice:500,
                paymentMethod:"信用卡",
            },
        ]
    },
    mounted() {
        axios.get("http://localhost:3000/orderOk").then((res) =>{ //memberAreasMyOrderOk.json
            this.orderOk = res.data
        })
    },
})