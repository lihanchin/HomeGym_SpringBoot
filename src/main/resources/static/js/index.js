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

//動畫
VanillaTilt.init(document.querySelectorAll(".box"), {
    max: 30,
    speed: 400,
    glare: true,
    transition:true,
    "max-glare": 1
});
let box = document.querySelector(".box");
let ani = document.querySelector(".ani")
box.addEventListener("mouseover",function(){
    box.classList.add("active");
});
box.addEventListener("mouseout",function(){
    box.classList.remove("active")
});

ani.addEventListener("animationend",function(){
    ani.classList.add("scal")
});










