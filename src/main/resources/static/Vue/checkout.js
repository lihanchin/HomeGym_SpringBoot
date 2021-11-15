let storage = localStorage;

new Vue({
    el:"#app",
    data:{
        paymentPage:"",
    },
    mounted() {
        let itemString = storage.getItem('addItemList');
        items = itemString.substr(0, itemString.length - 2).split('， ');

        courseId = [];

        items.forEach(function(item){
            let i = item.lastIndexOf("d")
            let id = item.substring(i+1)
            courseId.push(id)
        });
        console.log(courseId)
        axios.post(`/test/buySoomething`,

                courseId

        ).then((res) =>{
            console.log(res)
            this.paymentPage = res.data.paymentPage;
            $('#app').html(res.data.paymentPage);
        })
    },
})






