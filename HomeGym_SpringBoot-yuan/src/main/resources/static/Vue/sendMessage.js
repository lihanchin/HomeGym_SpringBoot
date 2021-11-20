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
            if(!this.message.visitorMessage) return false;
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

            let alert = document.querySelector('.alert');

            alert.classList.remove('hide');
            alert.classList.add('show');
            alert.classList.add('showAlert');
            setTimeout(function(){
                alert.classList.add('hide');
                alert.classList.remove('show');
            },1000);

        }
    },
    mounted(){
        //傳送訊息
        VanillaTilt.init(document.querySelectorAll(".box"), {
            max: 30,
            speed: 400,
            glare: true,
            transition:true,
            "max-glare": 1
        });
        let box = document.querySelector(".box");
        let ani = document.querySelector(".ani")
        box.addEventListener("mouseover",function(){
            box.classList.add("active");
        });
        box.addEventListener("mouseout",function(){
            box.classList.remove("active")
        });

        ani.addEventListener("animationend",function(){
            ani.classList.add("scal")
        });


        //首頁導覽列
        window.addEventListener("scroll",function(){
            let mainNav = this.document.querySelector('.main_nav');
            if(window.scrollY>100){
                mainNav.classList.remove('bg-primary');
            }else{
                mainNav.classList.add('bg-primary');
            }
        });
    }
})