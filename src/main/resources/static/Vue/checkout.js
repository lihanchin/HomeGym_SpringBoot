let storage = localStorage;
function doFirst() {
    let itemString = storage.getItem('addItemList');
    items = itemString.substr(0, itemString.length - 2).split('， ');
    console.log(items);
}
window.addEventListener('load', doFirst);


new Vue({
    el:"#app",
    data:{
        paymentPage:"",
        checkOut:{
            price:"2580",
            orderItems:"BodyCombat 88 $580 # 操爆你的二頭肌 $500 # 瑜珈60 $580"
        }
    },
    mounted() {
        axios.post(`http://localhost:8080/test/buySoomething`,
            {
                price:"2580",
                orderItems:"BodyCombat 88 $580 # 操爆你的二頭肌 $500 # 瑜珈60 $580"
            }
        ).then((res) =>{ //shoppingdate.json
            console.log(res)
            this.paymentPage = res.data.paymentPage;
            // $('#app').html(res.data.paymentPage);
        })
    },
})






