new Vue({
    el:"#app",
    data:{
        psw:"",
        repsw:"",
        memberEmail:"",
        message:''
    },
    methods: {
        inputpsw(){
            if(!this.memberEmail) return false; //阻止空字串送出

            axios.post(`/forget/reset`,{
                memberEmail:this.memberEmail,
                newPassword:this.psw,
                newPasswordCheck:this.repsw
            }).then((res)=>{

                if(res.data =="修改成功"){
                    alert(res.data)
                    localStorage.removeItem("memberEmail")
                    window.location.replace("/");
                }else {
                    this.message = res.data
                }
            })
        }
    },
    mounted(){
        this.memberEmail = localStorage.getItem("memberEmail")
    }
})