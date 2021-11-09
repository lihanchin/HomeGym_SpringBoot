//首頁導覽列
window.addEventListener("scroll",function(){
    let mainNav = this.document.querySelector('.main_nav');
    if(window.scrollY>100){
        mainNav.classList.remove('bg-primary');
    }else{
        mainNav.classList.add('bg-primary');
    }
});


///////////////////////////////////////////////////////////
//傳送訊息按鈕
let alert = document.querySelector('.alert');
function subimtShow(){
    // e.preventDefault();
    alert.classList.remove('hide');
    alert.classList.add('show');
    alert.classList.add('showAlert');
    setTimeout(function(){
        alert.classList.add('hide');
        alert.classList.remove('show');
    },1000);
}


////////







