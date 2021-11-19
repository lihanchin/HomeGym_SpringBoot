let  token = localStorage.getItem("Authorization")
new Vue({
    el:"#app",
    data:{
        //資料庫來的資料
        memberProfile:{},
        cacheContent:{},
        cacheName:"",
        cachePhone:"",
    },
    methods: {
        selectedImg(evt){
            const file = evt.target.files.item(0)
            const reader = new FileReader();
            reader.addEventListener('load',this.imageLoaded);
            reader.readAsDataURL(file);
        },
        imageLoaded(evt){
           this.memberProfile.memberImage = evt.target.result
        },
        editMember: function(item){
            
           this.cacheContent = item;
           this.cacheName = item.name
           this.cachePhone = item.phone
        },
        editMemberDone: function(item){
            key = item;
            console.log(key);
            item.name = this.cacheName
            item.phone = this.cachePhone

            axios.put(`/memberArea/edit`,{
                memberId: this.memberProfile.memberId,
                memberImage: this.memberProfile.memberImage,
                name: this.memberProfile.name,
                email: this.memberProfile.email,
                birthday: this.memberProfile.birthday,
                phone: this.memberProfile.phone
                
            }, {
                headers: {
                    Authorization: token
                }
            }).then((res) =>{
                console.log(res);
            })
                this.cacheName ='';
                this.cachePhone="",
                this.cacheContent ={};
        },
    },
    mounted() {

        axios.get("/memberArea/", {
            headers: {
                Authorization: token
            }
        }).then((res) =>{
            console.log(res);
            this.memberProfile = res.data
            this.memberProfile.memberImage= 'data:'+res.data.mimeType+';base64,'+res.data.memberImage
        }).catch(error =>{
            console.log(error.response.data.message)
            window.alert("請重新登入");
            window.location.replace("/");
        })

        axios.get("/memberArea/backFromECPay").then((res) =>{
            console.log(res);
            if(res.data!=null){
                window.alert("我要刪掉了")
                localStorage.removeItem("addItemList")
            }


        })
    }
});