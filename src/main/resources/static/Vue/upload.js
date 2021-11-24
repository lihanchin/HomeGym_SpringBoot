import { initializeApp } from "https://www.gstatic.com/firebasejs/9.5.0/firebase-app.js";
import { getStorage, ref, uploadBytes, getDownloadURL } from "https://www.gstatic.com/firebasejs/9.5.0/firebase-storage.js";

// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
    apiKey: "AIzaSyA4-BX-xkWAQZTAJt-KcOIw9lq3OkynA5k",
    authDomain: "homegym-5ed5f.firebaseapp.com",
    databaseURL: "https://homegym-5ed5f-default-rtdb.firebaseio.com",
    projectId: "homegym-5ed5f",
    storageBucket: "homegym-5ed5f.appspot.com",
    messagingSenderId: "115618265561",
    appId: "1:115618265561:web:06914919394fbb1c4581c8",
    measurementId: "G-FC0KTKH9KY"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const storage = getStorage(app, "gs://inductive-album-332014.appspot.com");


new Vue({
    el:"#video",
    data:{
        video:{
            video: "",
        }
    },
    methods:{
        fileChange: function(){ //讀取到檔案

            let nextStep = document.querySelector('#nextStep');

            if(fileupload.files.length === 0){
                return;
            }else{
                nextStep.classList.remove('disabled'); //讀到檔案可以按下一步
            }
            console.log(fileupload.files.length);

            //限制影片大小
            const theFile = fileupload.files.item(0);
            const videoMaxSize = 150000000;
            if(theFile.size > videoMaxSize){
                alert("影片大小不得超過 200MB");
                fileupload.value= '';
                // nextStep.disabled=true;
                return;
            }
            // nextStep.disabled=false;

            //讀檔
            //待改
            let readFile = new FileReader();
            readFile.readAsDataURL(theFile);
            readFile.addEventListener("load", this.videoLoaded);
        },
        uploadFile() {
            let course = document.getElementById('fileupload').files
            console.log(course)
            const storageRef = ref(storage, course[0].name);

            uploadBytes(storageRef, course[0]).then((snapshot) => {
                getDownloadURL(snapshot.ref).then((url) => {
                    console.log(url)
                    document.getElementById("courseVideo").setAttribute("src", url)
                })
            });

        }
    }
})


