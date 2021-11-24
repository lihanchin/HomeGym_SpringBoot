
let  token = localStorage.getItem("Authorization")

new Vue({

    el:"#app",
    data:{
        //接收後端教練資料
        coach:{},

        cacheContent:{},
        cacheSkill:"",
        cacheExperience:"",
        cacheCoachInfo:"",
        fileupload:"",


        video:{
            videoupload:'',
            videoName:'',
            partOfBody:'',
            price:'',
            equipment:'',
            level:'',
            videoInfo:'',
            image:''
        },
    },
    methods: {

        selectedCoachImg(evt){ //讀取圖片
            console.log(evt)
            const file = evt.target.files.item(0)
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.addEventListener('load',this.coachImageLoaded);
        },
        coachImageLoaded(evt){ //更新圖片路徑
            console.log((evt))
            this.video.image = evt.target.result
        },
        videoUpload(){
            let videoURL = document.getElementById("courseVideo").getAttribute("src")

            this.video.videoupload = videoURL


            axios.post(`/course/upload`,
                {
                    coursePath :this.video.videoupload,
                    courseName:this.video.videoName,
                    partOfBody:this.video.partOfBody,
                    price:this.video.price,
                    equipment:this.video.equipment,
                    level:this.video.level,
                    courseInfo:this.video.videoInfo,
                    courseImage:this.video.image,
                },
                {
                    headers: {
                        Authorization: token
                    }
                }
            ).then((res) =>{
                console.log(res);
            })
            this.video.videoupload ="";
            this.video.videoName ="";
            this.video.partOfBody ="";
            this.video.price ="";
            this.video.equipment="";
            this.video.level="";
            this.video.videoInfo="";

        },



        selectedImg(evt){ //讀取圖片
            const file = evt.target.files.item(0)
            const reader = new FileReader();
            reader.addEventListener('load',this.imageLoaded);
            reader.readAsDataURL(file);
        },
        imageLoaded(evt){ //更新圖片路徑
            this.coach.coachImage = evt.target.result
        },

        editMember: function(item){ //編輯資料
            this.cacheContent = item;
            this.cacheSkill = item.skill
            this.cacheExperience = item.experience
            this.cacheCoachInfo = item.coachInfo
        },
        editMemberDone: function(item){ //編輯完成
            item.skill = this.cacheSkill
            item.experience = this.cacheExperience
            item.coachInfo = this.cacheCoachInfo

            axios.put(`/coachArea/editInfo`,{
                coachId: this.coach.coachId,
                coachImage: this.coach.coachImage,
                skill: this.cacheSkill,
                experience: this.cacheExperience,
                coachInfo: this.coach.coachInfo

            },{
                headers: {
                    Authorization: token
                }
            }).then((res) =>{
                console.log(res);
            })
            this.cacheSkill ='';
            this.cacheExperience ='';
            this.cacheCoachInfo ='';
            this.cacheContent ={};
        },
        cancelInputFile(){ //按下取消會清空內容
            fileupload.value='';
            this.video.videoupload ="";
            this.video.videoName ="";
            this.video.partOfBody ="";
            this.video.price ="";
            this.video.equipment="";
            this.video.level="";
            this.video.videoInfo="";
            nextStep.classList.add('disabled'); //讀到檔案可以按下一步

        },
    },
    mounted() {

        axios.get("/coachArea/", {
            headers: {
                Authorization: token
            }
        }).then((res) =>{
            console.log(res);
            this.coach = res.data
        }).catch((err) =>{
            console.log(err)
            console.log(err.status)
            if(err.status=='403'){
                window.alert("請重新登入");
                window.location.replace("/");
            }
        })
    },
    updated(){

        let courseVideo = document.getElementById("courseVideo")

        window.addEventListener("click",function (){
            let loadingCircle = document.querySelector("#loadingCircle")
            let courseVideo = document.querySelector("#courseVideo")

            if(courseVideo.getAttribute('src') != ''){
                console.log(courseVideo.getAttribute('src'))
                loadingCircle.classList.add('d-none')
                courseVideo.classList.remove('d-none')
            }
        })


    }
});


//拖移上傳的功能
dropArea = document.getElementById('dropArea');
dropArea.addEventListener('dragenter',function(e){e.preventDefault()});
dropArea.addEventListener('dragover',function(e){e.preventDefault()});
dropArea.addEventListener('drop',dropped);


function dropped(e){
    e.preventDefault();
    let file = e.dataTransfer.files;

//篩副檔名
    switch(file[0].type){
        case "video/ogv" :
            break;
        case "video/webm" :
            break;
        case "video/mp4" :
            break;
        default:
            alert("請上傳副檔名為 .ogv/.webm/.mp4 的影片檔");
            fileupload.value= '';
            nextStep.disabled=true;
            return;
    }
    fileupload.files = file;
    nextStep.disabled=false;

//讀檔
    let readFile = new FileReader();
    readFile.readAsDataURL(file[0]);
    readFile.addEventListener('load',function(){
        courseVideo.src = readFile.result;
        let nextStep = document.querySelector('#nextStep');
        nextStep.classList.remove('disabled'); //讀到檔案可以按下一步

    });


}



//上傳表單必填阻擋
let course_name = document.querySelector('#course_name');
let course_classification = document.querySelector('#course_classification');
// let course_type = document.querySelector('#course_type');
let course_price = document.querySelector('#course_price');
let course_equipment = document.querySelector('#course_equipment');
let course_fit_person = document.querySelector('#course_fit_person');
let course_content = document.querySelector('#course_content');
let submit_up_video = document.querySelector('#submit_up_video');
// let needs_validation_up_video = document.querySelector('#needs_validation_up_video')


course_name.addEventListener('blur',function(){
    if(course_name.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
    OpenCloseBtn();
});

course_classification.addEventListener('blur',function(){
    if(course_classification.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
    OpenCloseBtn();
});

// course_type.addEventListener('blur',function(){
//     if(course_type.value != ""){
//         this.classList.remove('is-invalid');
//         this.classList.add('is-valid');
//     }else{
//         this.classList.remove('is-valid');
//         this.classList.add('is-invalid');
//     }
//     OpenCloseBtn();
// });

course_price.addEventListener('blur',function(){
    if(course_price.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
    OpenCloseBtn();
});

course_equipment.addEventListener('blur',function(){
    if(course_equipment.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
    OpenCloseBtn();
});

course_fit_person.addEventListener('blur',function(){
    if(course_fit_person.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
    OpenCloseBtn();
});

course_content.addEventListener('blur',function(){
    if(course_content.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
    OpenCloseBtn();
});

function OpenCloseBtn(){
    if(course_name.value!="" && course_classification.value !=""
        // && course_type.value !=""
        && course_price.value !="" && course_equipment.value !="" && course_fit_person.value !="" && course_content.value !=""){
        submit_up_video.classList.remove('disabled');
    }else{
        submit_up_video.classList.add('disabled');
    }


}

