new Vue({

    
    el:"#app",
    data:{
        coachingData:{
            id:1,
            img:"https://fakeimg.pl/200x200/",
            coachingName:"Jack",
            specialty:"健美",
            experience:"台北市立大學體育老師",
            coachingIntroduction:"無論您正開始一個新的健身計畫，或者您可能已經運動一段時間了，我們相信，一些幫助可以讓您達到期望的目標。我們的私人教練會根據您的需求及能力",
        },
        cacheContent:{},
        cacheSpecialty:"",
        cacheExperience:"",
        cacheCoachingIntroduction:"",
    },
    methods: {
        selectedImg(evt){ //讀取圖片
            const file = evt.target.files.item(0)
            const reader = new FileReader();
            reader.addEventListener('load',this.imageLoaded);
            reader.readAsDataURL(file);
        },
        imageLoaded(evt){ //更新圖片路徑
           this.coachingData.img = evt.target.result
        },

        editMember: function(item){ //編輯資料
           this.cacheContent = item;
           this.cacheSpecialty = item.specialty
           this.cacheExperience = item.experience
           this.cacheCoachingIntroduction = item.coachingIntroduction
        },
        editMemberDone: function(item){ //編輯完成
            item.specialty = this.cacheSpecialty
            item.experience = this.cacheExperience
            item.coachingIntroduction = this.cacheCoachingIntroduction
            key = item.id

            axios.put(`http://localhost:3000/coachingData`,{
                id: this.coachingData.id,
                img: this.coachingData.img,
                coachingName: this.coachingData.coachingName,
                specialty: this.coachingData.specialty,
                experience: this.coachingData.experience,
                coachingIntroduction: this.coachingData.coachingIntroduction

            }).then((res) =>{
                console.log(res);
            })


            this.cacheSpecialty ='';
            this.cacheExperience ='';
            this.cacheCoachingIntroduction ='';
            this.cacheContent ={};
        },
        fileChange: function(){ //讀取到檔案
            if(fileupload.files.length === 0){
                return;
            }else{
                let nextStep = document.querySelector('#nextStep');
                nextStep.classList.remove('disabled'); //讀到檔案可以按下一步
            }
            console.log(fileupload.files.length);
        
            //限制影片大小
            theFile = fileupload.files[0];
            const videoMaxSize = 200000000; 
            if(theFile.size > videoMaxSize){
                alert("影片大小不得超過 200MB");
                fileupload.value= ''; 
                // nextStep.disabled=true;
                return;
            }
            // nextStep.disabled=false;
        
            //讀檔
            let readFile = new FileReader();
            readFile.readAsDataURL(theFile);
            readFile.addEventListener('load',function(){
                let courseVideo = document.getElementById('courseVideo');
                courseVideo.src = readFile.result;
            });
        },
        cancelInputFile: function(){ //按下取消會清空內容
            fileupload.value='';
            nextStep.disabled=true;
        },
        
    },
    mounted() {
        axios.get("http://localhost:3000/coachingData").then((res) =>{ //memberAreasIntroduction.json
        console.log(res);
        this.coachingData = res.data
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
let course_type = document.querySelector('#course_type');
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

course_type.addEventListener('blur',function(){
    if(course_type.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
    OpenCloseBtn();
});

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
    if(course_name.value!="" && course_classification.value !="" && course_type.value !="" && course_price.value !="" && course_equipment.value !="" && course_fit_person.value !="" && course_content.value !=""){
        submit_up_video.classList.remove('disabled');
    }else{
        submit_up_video.classList.add('disabled');
    }
}


