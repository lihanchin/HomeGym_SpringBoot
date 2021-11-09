new Vue({
    el:"#app",
    data:{
        uploadedCourse:[
            {
                id:1,
                courseNmae:"十分鐘瑜伽",
                img:"https://fakeimg.pl/300x200/",
                typeTag:"上半身",
                star:3,
                locationTag:"腿部",
                courseContent:"如果你已經很久沒運動，或是入門新手的話，可以先從這支影片開始，幫你喚醒肌肉記憶！7分鐘的全身性訓練，包含了側抬腿、平板撐、捲腹、超人式、深蹲...等，雕塑腿型、臀腿、手臂，還能練出性感的腹肌線條",
                approvalStatus:"審核中",
                createdTime:"2021/9/14 下午11:40:41"
            },
            {
                id:2,
                courseNmae:"八分鐘爆汗核心",
                img:"https://fakeimg.pl/300x200/",
                typeTag:"全身",
                star:5,
                locationTag:"腹部",
                courseContent:"運動能促進有「快樂荷爾蒙」之稱的多巴胺分泌。即使只是運動10分鐘，都能讓疲憊的大腦重新變得敏銳",
                approvalStatus:"審核通過",
                createdTime:"2021/10/14 上午9:30:30"
            },

        ],
        cacheContent:{},
        cacheTitle:"",
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
        }
    },
    mounted() {
        axios.get("http://localhost:3000/uploadedCourse").then((res) =>{ //mycoursesAreasKeepCourseDate.json
            this.uploadedCourse = res.data
        })
    },
});