new Vue({
    el: "#message",
    data: {
        message:{
            visitorEmail:'',
            visitorName:'',
            visitorMessage:'',
        }
    },
    methods: {
        sendMessage(){
            axios.post(`/addMessage`,{
                visitorEmail:this.message.visitorEmail,
                visitorName:this.message.visitorName,
                visitorMessage:this.message.visitorMessage,

            }).then((res) =>{
                console.log("結束");
                console.log(res);
            })
                this.message.visitorEmail="";
                this.message.visitorName="";
                this.message.visitorMessage="";

        }

    }
})