new Vue({
    el:"#app",
    data:{
        orderNg:[
            {
                courseName:"10小時全身深層肌肉拉筋",
                orderNamber:"A20211005",
                orderPrice:15000,
                paymentMethod:"信用卡",
                reasonForPaymentFailure:"信用卡餘額不足",
            },
            {
                courseName:"30分鐘瑜伽",
                orderNamber:"A20010001",
                orderPrice:2000,
                paymentMethod:"信用卡",
                reasonForPaymentFailure:"網路中斷交易",
            },
            {
                courseName:"十分鐘核心健身精華",
                orderNamber:"A20180305",
                orderPrice:5000,
                paymentMethod:"信用卡",
                reasonForPaymentFailure:"信用卡餘額不足",
            },
        ]
    },
    mounted() {
        axios.get("http://localhost:3000/orderNg").then((res) =>{ //shoppingdate.json
            this.orderNg = res.data
        })
    },
})