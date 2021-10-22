new Vue({
    el:"#app",
    data:{
        memberProfile:{
            id:1,
            img:"https://fakeimg.pl/1000x1000/",
            name:"陳天浩",
            email:"A123@gmail.com",
            birthday:"2021/11/30",
            phone:"0922555999"
        },
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
           this.memberProfile.img = evt.target.result
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
            this.cacheName ='';
            this.cacheContent ={};

            axios.put(`http://localhost:3000/memberProfile/`,{
                id: this.memberProfile.id,
                img: this.memberProfile.img,
                name: this.memberProfile.name,
                email: this.memberProfile.email,
                birthday: this.memberProfile.birthday,
                phone: this.memberProfile.phone
                
            }).then((res) =>{
                console.log(res);
            })
        },
    },
    mounted() {
        axios.get("http://localhost:3000/memberProfile").then((res) =>{ //memberAreasIntroduction.json
        console.log(res);
        this.memberProfile = res.data
        })
    }
});