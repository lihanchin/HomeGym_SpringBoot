new Vue({
    el:"#app",
    data:{
        currentPage:[],
        totalPage:"",
        cacheContent:{},
        cacheTitle:"",
        keyword:"",
    },
    methods: {
        editContent: function(item){
            this.cacheContent = item;
            this.cacheTitle = item.courseContent;
        },
        editDone: function(item){
            key = item.id
            item.courseContent = this.cacheTitle;
            
            console.log(this.cacheContent);

            axios.put(`http://localhost:3000/uploadedCourse/${key}`,{

                courseNmae:this.cacheContent.courseNmae,
                img: this.cacheContent.img,
                typeTag:this.cacheContent.typeTag,
                star:this.cacheContent.star,
                locationTag:this.cacheContent.locationTag,
                courseContent:this.cacheTitle,
                approvalStatus:this.cacheContent.approvalStatus,
                createdTime:this.cacheContent.createdTime

            }).then((res) =>{
                console.log(res);
            })

            this.cacheTitle ='';
            this.cacheContent={};
        },
        searchCourse(keyword){
            let  token = localStorage.getItem("Authorization")
            axios.get(`http://localhost:8080/coachArea/keyword?keyword=${keyword}`,{
                headers: {
                    Authorization: token
                }
            }).then((res) =>{
                this.currentPage = res.data
                this.totalPage = res.data.totalPage

                console.log(res)
            })

        }
    },
    mounted() {
        let  token = localStorage.getItem("Authorization")
        axios.get("http://localhost:8080/coachArea/mycourse",{
            headers: {
                Authorization: token
            }
        }).then((res) =>{
            this.currentPage = res.data.currentPage
            this.totalPage = res.data.totalPage

            console.log(res.data)
        })
    },
});