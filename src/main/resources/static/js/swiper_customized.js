
// 廣告版
let course_banner = new Swiper('.swiper_course_banner',{
    autoplay: {
        delay: 7000,  
        disableOnInteraction: false,
    },
    speed: 700,
    allowTouchMove: false,
    lazy: {
        loadPrevNext: true,
        loadPrevNextAmount: 3,
    },
    centeredSlides: true,
    spaceBetween : 50,
    slidesOffsetBefore: 40,
    loop: true,
    slidesPerView : 'auto',
    on: {
        slideChangeTransitionEnd: function(){
            this.slides.transition(this.params.autoplay.delay+this.params.speed).transform('translate3d(-60px, 0, 0)');
        },
        slideChangeTransitionStart: function(){
            this.slides.transition(this.params.speed).transform('translate3d(0, 0, 0)');
        },
    },
    pagination: {
        el: '.swiper-pagination',
        clickable :true,
        renderBullet: function (index, className) {
            return '<div class="' + className + '"><span></span><i></i></div>';
        },
    }
});

// window.onresize=function(){
//     swiper.update();
// }

// 課程推薦版
let course_recommendation_space = new Swiper(".swiper_course_group", {
    autoplay: {
        delay: 6000,  
        disableOnInteraction: false,
    },
    speed: 700,
    slidesPerView: 1,
    spaceBetween: 30,
    loop: true,
    navigation: {
      nextEl: "#course_recommendation_space_next",
      prevEl: "#course_recommendation_space_prev",
    },
});


//課程教練版
let swiper_course_coaching = new Swiper(".swiper_course_coaching", {
    autoplay: {
        delay: 5000,  
        disableOnInteraction: false,
    },
    speed: 700,
    slidesPerView: 1,
    spaceBetween: 30,
    loop: true,
});

