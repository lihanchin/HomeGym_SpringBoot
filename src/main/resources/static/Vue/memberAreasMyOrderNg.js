new Vue({
    el: "#app",
    data: {
        orderDetail: [
            // {
            // courseName: '',
            // orderNamber: '',
            // orderPrice: '',
            // paymentMethod: "信用卡",
            // reasonForPaymentFailure: '',
            // },
        ],
        totalPage:'',
        currentPage:[{

        }
        ]
    },
    mounted() {
        axios.get("http://localhost:8080/memberArea/NGOrder", ).then((res) => { //shoppingdate.json
            this.totalPage = res.data.totalPage
            this.orderDetail = res.data.orderDetail
            this.currentPage = res.data.currentPage


        })
    },
})