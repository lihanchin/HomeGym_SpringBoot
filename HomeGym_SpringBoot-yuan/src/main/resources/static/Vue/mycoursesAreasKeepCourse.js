

let mycoursesAreasKeepCourse = new Vue({
    el:'#app',
    data:{
        keep:{
            // isLike: true,
            islinkIcon:`<i class="fa fa-heart fs-4 text-danger" style="cursor:pointer;" aria-hidden="true"></i>`,
            notLinkIcon:`<i class="fa fa-heart-o fs-4" style="cursor:pointer;" aria-hidden="true"></i>`,
        },
        keepCourse:[
            {
                img:"https://fakeimg.pl/400x300/",
                star:3,
                courseName:"tabata腹部核心健身",
                typeTag:"上半身",
                locationTag:"腿部",
                coachingName:"Jack",
                courseContent:"運動能促進有「快樂荷爾蒙」之稱的多巴胺分泌。即使只是運動10分鐘，都能讓疲憊的大腦重新變得敏銳",
                courseUrl:"./purchasedCourse.html",
                isLike: true,
            },
            {
                img:"https://fakeimg.pl/400x300/",
                star:4,
                courseName:"十分鐘瑜伽舒壓",
                typeTag:"全身",
                locationTag:"全身",
                coachingName:"John",
                courseContent:"運動能促進有「快樂荷爾蒙」之稱的多巴胺分泌。即使只是運動10分鐘，都能讓疲憊的大腦重新變得敏銳",
                courseUrl:"./purchasedCourse.html",
                isLike: true,
            },
            {
                img:"https://fakeimg.pl/400x300/",
                star:2,
                courseName:"tabata爆汗核心健身",
                typeTag:"下半身",
                locationTag:"腿部",
                coachingName:"Jason",
                courseContent:"運動能促進有「快樂荷爾蒙」之稱的多巴胺分泌之稱的多巴胺分泌之稱的多巴胺分泌之稱的多巴胺分泌。即使只是運動10分鐘，都能讓疲憊的大腦重新變得敏銳",
                courseUrl:"./purchasedCourse.html",
                isLike: true,
            },
        ]
    },
    methods: {
        keepToggle:function(index){
            key = index;
            // console.log(key);
            this.keepCourse[index].isLike = !this.keepCourse[index].isLike;
            
            axios.put(`http://localhost:3000/keepCourse/${index+1}`,{
                img: this.keepCourse[index].img,
                star:this.keepCourse[index].star,
                courseName:this.keepCourse[index].courseName,
                typeTag:this.keepCourse[index].typeTag,
                locationTag:this.keepCourse[index].locationTag,
                coachingName:this.keepCourse[index].coachingName,
                courseContent:this.keepCourse[index].courseContent,
                courseUrl:this.keepCourse[index].courseUrl,
                isLike:this.keepCourse[index].isLike
                
            }).then((res) =>{
                console.log(res);
            })
        },

    },
    mounted() {
        axios.get("http://localhost:3000/keepCourse").then((res) =>{ //mycoursesAreasKeepCourseDate.json
            this.keepCourse = res.data
        })
    },
    
});

