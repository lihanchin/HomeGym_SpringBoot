let  token = localStorage.getItem("Authorization")
new Vue({
    el: "#app",
    data: {
        memberPassword:{
            oldPassword:'',
            newPassword:'',
            newPasswordCheck:'',
        }
    },
    methods: {
        revisePassword(){
            axios.post(`/memberArea/changePassword`,{
                oldPassword:this.memberPassword.oldPassword,
                newPassword:this.memberPassword.newPassword,
                newPasswordCheck:this.memberPassword.newPasswordCheck,

            },  {
                headers: {
                    Authorization: token
                }
            }).then((res) =>{
                console.log("結束");
                console.log(res);

            }).catch(error =>{
                console.log(error.response.data.message)
                window.alert(error.response.data.message);
                // window.location.replace("http://localhost:8080/");
            })
            window.alert("修改成功")
            window.location.replace("/member");
            this.memberPassword.oldPassword="";
            this.memberPassword.newPassword="";
            this.memberPassword.newPasswordCheck="";

        }

    }
})