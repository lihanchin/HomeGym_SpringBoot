//首頁導覽列
window.addEventListener("scroll",function(){
    let mainNav = this.document.querySelector('.main_nav');
    if(window.scrollY>100){
        mainNav.classList.remove('bg-primary');
    }else{
        mainNav.classList.add('bg-primary');
    }
});


//登入驗證
window.addEventListener('load', function () {
let login = document.getElementById('needs_validation_login');
login.addEventListener('submit', function (event) {
    if (login.checkValidity() === false) {
    event.preventDefault();
    event.stopPropagation();
    }
    login.classList.add('was-validated');
}, false);
}, false);


/////////////////////////////////////////////////////////
//註冊驗證
let input = document.querySelectorAll('#needs_validation_signup input');
let span = document.querySelectorAll('#needs_validation_signup span');
let submitSignup = document.getElementById('btn_signup');


//定義 輸入錯誤的方法(input順序,顏色,文字內容)
function changErrorColor(id, color, text){
    input[id].classList.add("is-invalid") //輸入框錯誤顯示
    span[id].classList.add(color);
    span[id].classList.remove("d-none"); //移除display:none
    span[id].innerText = text;
}
//定義 輸入正確的方法(input順序,顏色)
function changColor(id, color){
  input[id].classList.remove("is-invalid") 
  input[id].classList.add("is-valid") //輸入框正確顯示
  span[id].classList.add(color);
  span[id].classList.add("d-none"); //加入display:none
}

// 信箱格式
let reg_email = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-])+\.([A-Za-z]{2,4})$/;
// 至少8-16個字符，至少1個大寫字母，1個小寫字母和1個數字，其他可以是任意字符
let reg_pwd =/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/;
// 輸入中文，字數2到8。 輸入英文，字數3到20
let reg_name =/^([\u4e00-\u9fa5]{2,8}|[a-zA-Z.\s]{3,20})$/;
// 輸入手機09開頭 後面隨意8位數字
let reg_phone =/^09\d{8}$/;

input.forEach( e => {
    e.addEventListener('blur',function(){
        if(this.id == 'member_email'){ //信箱
            if(this.value ==''){
                changErrorColor(0, 'text-danger', 'Email不能空白');
            }else if(!reg_email.test(this.value)){
                changErrorColor(0, 'text-danger', 'Email格式不正確');
            }else{
                changColor(0, 'text-success')
            }

        }else if(this.id == 'pwd'){ //密碼
            if(this.value ==''){
                changErrorColor(1, 'text-danger', '請輸入密碼');
            }else if(!reg_pwd.test(this.value)){
                changErrorColor(1, 'text-danger', '長度8位以上，至少1個大寫字母，1個小寫字母和1個數字');
            }else{
                changColor(1, 'text-success')
            }
        }else if(this.id == 'repassword'){ //二次密碼

            if(this.value ==''){
                changErrorColor(1, 'text-danger', '請先輸入密碼');
            }else if(this.value!=input[1].value){
                changErrorColor(2, 'text-danger', '兩次密碼不一致');
            }else{
                changColor(2, 'text-success')
            }

        }else if(this.id == 'member_name'){ //會員姓名

            if(this.value ==''){
                changErrorColor(3, 'text-danger', '姓名不能空白');
            }else if(!reg_name.test(this.value)){
              changErrorColor(3, 'text-danger', '請輸入正確姓名');
            }else{
                changColor(3, 'text-success')
            }
        }else if(this.id == 'member_phone'){ //會員電話

            if(this.value ==''){
                changErrorColor(4, 'text-danger', '電話不能空白');
            }else if(!reg_phone.test(this.value)){
              changErrorColor(4, 'text-danger', '請輸入正確手機電話');
            }else{
                changColor(4, 'text-success')
            }
        }else if(this.id == 'member_birthday'){ //會員生日

            if(this.value ==''){
                changErrorColor(5, 'text-danger', '生日不能空白');
            }else{
                changColor(5, 'text-success');
            }
        }
        //全部輸入完才能按下按鈕
        if(input[0].value !== '' && input[1].value !== '' && input[3].value !== '' && input[4].value !== '' && input[5].value !== ''){
            submitSignup.classList.remove('disabled');
        }
        
    });
});

//按下按鈕後清空內容
function clearFormContent(){
    input.forEach(function(n){
        n.value='';
        n.classList.remove('is-invalid')
        n.classList.remove('is-valid')
    }),
    span.forEach(function(s){
        s.innerText='';
    })
    submitSignup.classList.add('disabled');
}

/* 說明：先點擊重複密碼，會focus到密碼欄，並會跳出訊息提醒用戶 */
function pleasePwd(){
let pwd = document.getElementById("pwd");
if(pwd.value == ''){
    document.getElementById("pwd").focus();
    changErrorColor(1, 'text-danger', '請先輸入密碼');
    }
}

//註冊發送連結按鈕 50秒內不能再按
btResend = document.getElementById('btResend');
function resendMail(){

    //旁邊計時器，秒數要比按鈕 -1 才會同步
    let seconds = 5;//若要改時間，在此變數改
    let intervalID = setInterval(()=>{
        countSeconds.innerHTML = `${seconds}s`;
        seconds--;
        if(seconds+1 === 0){
            clearInterval(intervalID);
            countSeconds.innerHTML = ''
        }
    },1000)
    
    //按鈕按下後會失效50秒
    btResend.disabled = true;
    setTimeout(() => {
        btResend.disabled = false;
     }, ((seconds+1)*1000));
     
}

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







