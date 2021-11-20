new Vue({
    el:"#course",
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

            axios.put(`/uploadedCourse/${key}`,{

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
        searchCourse(){
            let  token = localStorage.getItem("Authorization")
            console.log("this.keyword")
            console.log(this.keyword)
            axios.get(`/coachArea/keyword?keyword=`+this.keyword,{
                headers: {
                    Authorization: token
                }
            }).then((res) =>{
                console.log(res.data)
                this.currentPage = res.data.courseList
                this.totalPage = res.data.totalPage


            })

        },
        clickPage(index){
            let page = index+1
              if (this.keyword != "" &this.keyword !=null) {
                  console.log("000000000000000000")
                axios.get("/coachArea/keyword?keyword="+this.keyword+"&page="+page,{
                    headers: {
                        Authorization: token
                    }
                }).then((res) =>{
                    this.currentPage = res.data.currentPage;
                    this.totalPage = res.data.totalPage;

                })

            }else{
                  console.log("11111")
                  axios.get("/coachArea/mycourse?page="+page,{
                      headers: {
                          Authorization: token
                      }
                  }).then((res) =>{
                      console.log("222222")
                      console.log(res.data)
                      this.currentPage = res.data.currentPage;
                      this.totalPage = res.data.totalPage;

                  })

              }



        },
    },
    mounted() {
        let  token = localStorage.getItem("Authorization")
        axios.get("/coachArea/mycourse",{
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