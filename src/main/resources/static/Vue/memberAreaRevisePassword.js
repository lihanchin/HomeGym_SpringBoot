let  token = localStorage.getItem("Authorization")
new Vue({
    el: "#app",
    data: {
        memberPassword:{
            oldPassword:'',
            newPassword:'',
            newPasswordCheck:'',
        },
        message:''
    },
    methods: {
        revisePassword(){
            error = document.querySelector(".errortext");
            let correct;
            axios.post(`/memberArea/changePassword`,{
                oldPassword:this.memberPassword.oldPassword,
                newPassword:this.memberPassword.newPassword,
                newPasswordCheck:this.memberPassword.newPasswordCheck,
            },  {
                headers: {
                    Authorization: token
                }
            }).then((res) =>{
                console.log(res);
                correct = res.data.message
                this.memberPassword.oldPassword="";
                this.memberPassword.newPassword="";
                this.memberPassword.newPasswordCheck="";
                error.classList.remove('show');
                window.alert(correct)
                window.location.replace("/member")
            }).catch((err) =>{
                this.message = err.response.data.message;
                error.classList.add('show');
            })
        }
    }
})