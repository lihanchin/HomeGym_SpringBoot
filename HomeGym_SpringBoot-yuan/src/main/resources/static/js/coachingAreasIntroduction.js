//////////////////上傳的部分

//上傳影片時，檢查上傳影片是否符合格式（非影片檔或檔案大小超過要給警告）下一步後，影片會導入畫面
fileupload = document.getElementById('fileupload');
fileupload.addEventListener('change',fileChange);

//拖拉功能
dropArea = document.getElementById('dropArea');
dropArea.addEventListener('dragenter',function(e){e.preventDefault()});
dropArea.addEventListener('dragover',function(e){e.preventDefault()});
dropArea.addEventListener('drop',dropped);

//按取消會自動清空<input>
btCancel = document.getElementById('btCancel');
btCancel.addEventListener('click',cancelInputFile);



function fileChange(){

    if(fileupload.files.length === 0){
        return;
    }

//限制影片大小
    theFile = fileupload.files[0];
    const videoMaxSize = 200000000; 
    if(theFile.size > videoMaxSize){
        alert("影片大小不得超過 200MB");
        fileupload.value= ''; 
        nextStep.disabled=true;
        return;
    }

//前面<input>我有設只能是影片檔的限制，這裡是篩選影片檔的副檔名。
    // let filenameExtension = fileupload.value.split(".");
    // switch(filenameExtension[1]){
    //     case "ogv" :
    //         break;
    //     case "webm" :
    //         break;
    //     case "mp4" :
    //         break;
    //     default:
    //         alert("請上傳副檔名為 .ogv/.webm/.mp4 的影片檔");
    //         fileupload.value= '';
    //         nextStep.disabled=true;
    //         return;
    // }
    nextStep.disabled=false;

//讀檔
    let readFile = new FileReader();
    readFile.readAsDataURL(theFile);
    readFile.addEventListener('load',function(){
        courseVideo = document.getElementById('courseVideo');
        courseVideo.src = readFile.result; 
    });

}

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
    });
}

//按下取消按鈕
function cancelInputFile(){
    fileupload.value='';
    nextStep.disabled=true;
}