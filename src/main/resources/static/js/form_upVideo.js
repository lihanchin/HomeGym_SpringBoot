let video_name = document.querySelector('#video_name');
let training_method = document.querySelector('#training_method');
let video_type = document.querySelector('#video_type');
let video_content = document.querySelector('#video_content');
let submit_up_video = document.querySelector('#submit_up_video');
let needs_validation_up_video = document.querySelector('#needs_validation_up_video')


video_name.addEventListener('blur',function(){
    if(video_name.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
});

training_method.addEventListener('blur',function(){
    if(training_method.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
});

video_type.addEventListener('blur',function(){
    if(video_type.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
});

video_content.addEventListener('blur',function(){
    if(video_content.value != ""){
        this.classList.remove('is-invalid');
        this.classList.add('is-valid');
    }else{
        this.classList.remove('is-valid');
        this.classList.add('is-invalid');
    }
});

needs_validation_up_video.addEventListener('click',function(){
    if(video_name.value!="" && training_method.value !="" && video_type.value !="" && video_content.value !=""){
        submit_up_video.classList.remove('disabled');
    }
})
