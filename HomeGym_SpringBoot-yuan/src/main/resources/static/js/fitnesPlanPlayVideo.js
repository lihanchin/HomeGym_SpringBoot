//假設匯入5部影片
let allVideos = [
    {
       name: "",
       src: "./video.mp4",
       id: "vid_1"
    },
    {
       name: "",
       src: "./video.mp4",
       id: "vid_2"
    },
    {
       name: "",
       src: "./video.mp4",
       id: "vid_3"
    },
    {
       name: "",
       src: "./video_2.mp4",
       id: "vid_4"
    },
    {
       name: "",
       src: "./video.mp4",
       id: "vid_5"
    }
];
let musicIndex = 0;
let lastVideo;

//load進網頁時會動態產生影片標籤
//點擊菜單列表內的指定影片，會在video內播放
window.addEventListener('load',creatList);
const courseList = document.getElementById('courseList');

//功能：當左側列表出現影片要暫停，若消失要開始 OK
const video_menu = document.getElementById('video_menu');
video_menu.addEventListener('hidden.bs.offcanvas', function () {
    if(lastVideo === true){
        courseVideo.pause();
        return;
    }else if(courseVideo.currentTime !== 0 && courseVideo.paused){
        courseVideo.play();
        stopVideo.innerText = '休息一下';
    }
});
video_menu.addEventListener('shown.bs.offcanvas', function () {
    if(!courseVideo.paused && !courseVideo.end){
        courseVideo.pause();
        stopVideo.innerText = '繼續運動！';
    }
});

//功能：暫停休息一下按鈕 OK
const stopVideo = document.getElementById('stopVideo');
stopVideo.addEventListener('click', videoStop);

//影片監聽 OK
const courseVideo = document.getElementById('courseVideo');
courseVideo.addEventListener('play', timeStart);
courseVideo.addEventListener('pause', timeStop);

//功能：計時開始時間 OK
const workoutTime = document.getElementById('workoutTime');
let totalSeconds = 1;

//功能：影片連續播放功能 OK
courseVideo.addEventListener('ended',playNext);

//關閉彈跳視窗 OK
const btAd = document.getElementById('btAd');
const adArea = document.getElementById('adArea');
btAd.addEventListener('click',()=>{
    adArea.style.visibility = "hidden";
})


// 以下為方法定義
function videoStop(){
    if(!courseVideo.paused && !courseVideo.ended){ 
        courseVideo.pause();
        stopVideo.innerText = '休息一下';
        
    }else{
        courseVideo.play();
        stopVideo.innerText = '繼續運動';
        
    }
}

function timeStart(){
    stopVideo.innerText = '休息一下';
    intervalID = setInterval(showTime,1000);
    for(let i = 0; i < allVideos.length; i++){
        if(courseVideo.src === document.getElementById(`list_${i}`).firstElementChild.src && musicIndex === i ){
            document.getElementById(`list_${i}`).className = "list-group-item list-group-item-action active";
        }else{
            document.getElementById(`list_${i}`).className = "list-group-item list-group-item-action";
        }
    }
}

function timeStop(){
    stopVideo.innerText = '繼續運動';
    clearInterval(intervalID);
    for(let i = 0; i < allVideos.length; i++){
        if(courseVideo.src === document.getElementById(`list_${i}`).firstElementChild.src && musicIndex === i ){
            document.getElementById(`list_${i}`).className = "list-group-item list-group-item-action active";
        }else{
            document.getElementById(`list_${i}`).className = "list-group-item list-group-item-action";
        }
    }
    
}

function showTime(){
    let secs = totalSeconds % 60;
    let mins = Math.floor(Math.floor(totalSeconds % 3600) / 60);
    let hours = Math.floor(totalSeconds / 3600);
    workoutTime.innerHTML = `運動時數 ${hours} : ${mins} : ${secs}`;
    totalSeconds++;
}

function creatList(){
    for(let i = 0; i < allVideos.length; i++){
        addli = document.createElement("li");
        courseList.appendChild(addli);
        addli.className = "list-group-item list-group-item-action";
        // addli.style = "cursor: pointer";
        addli.innerHTML = `運動影片第${i+1}部`;
        addli.id = `list_${i}`;
        // addli.onclick = function(){
        //     lastVideo = false;
        //     courseVideo.src = addvideo.src; 
        //     musicIndex = i;
        //     document.getElementById(`list_${i}`).className = "list-group-item list-group-item-action active";
        //     for(let x = 0; x < allVideos.length; x++){
        //         if( x !== i){
        //             document.getElementById(`list_${x}`).className = "list-group-item list-group-item-action"
        //         }
        //     }
        // }
        let addvideo = document.createElement("video");
        addli.appendChild(addvideo);
        addvideo.src = allVideos[i].src;
        addvideo.style = "display: none";
    }
    courseVideo.src = document.getElementById('list_0').firstElementChild.src;
    document.getElementById('list_0').className = "list-group-item list-group-item-action active";
} 
   
function playNext(){    
    musicIndex++; 
    lastVideo = false;
    if(musicIndex > allVideos.length-1){
        adArea.animate([
            {transform: "scale(0.9)"},
            {transform: "scale(1.1)"},
            {transform: "scale(1)"},
            {visibility: "visible"}
        ], duration= 1000)
        adArea.style.visibility = "visible";
        courseVideo.pause();
        lastVideo = true;
    }else{
        run(allVideos[musicIndex]);
    }
}
                   
function run(videos) {
    courseVideo.src = videos.src;
    courseVideo.load();
    courseVideo.play();
}

