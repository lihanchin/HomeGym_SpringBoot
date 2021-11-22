new Vue({
    el:"#app",
    data:{
        email:"",
    },
    methods: {
        checkPassword(){
            if(!this.email) return false; //阻止空字串送出
            axios.post(`/forget/checkMail`,{
                memberEmail:this.email
            }).then((res) =>{
                localStorage.setItem("memberEmail",this.email)
                    alert(res.data)
                    window.location.replace("/");
            })
        }
    }
})
