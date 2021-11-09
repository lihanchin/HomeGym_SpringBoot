//selecting all required elements
const info_box = document.querySelector(".info_box");
const continue_btn = info_box.querySelector(".restart"); //開始按鈕
const result_box = document.querySelector(".result_b");
const option_list = document.querySelector(".option_list");
const timeText = document.querySelector(".timer .time_left_txt");
const timeCount = document.querySelector(".timer .timer_sec");
const result_btn = document.querySelector(".result_btn") //查看結果按鈕



//點擊可以開始按鈕
continue_btn.onclick = ()=>{
    clearInterval(counter); //清空計時器時間
    result_btn.classList.add("d-none"); //隱藏「結果按鈕」

    showQuetions(0); //顯示題目
    queCounter(1); //底部會顯示題目數
    startTimer(timeValue); //開始時間
}


let timeValue =  3;
let que_count = 0; //第幾個題目(抓陣列題目)
let que_numb = 1; //目前題數(顯示給使用者第幾題)
let userScore = 0; //使用者答對數
let counter;

const next_btn = document.querySelector(".next_btn");

//點擊下一題按鈕的話
next_btn.onclick = ()=>{
    timeCount.textContent = "03";
    if(que_count < questions.length -1){ //如果題目數到最後一題
        que_count++; //
        que_numb++; //目前題數
        showQuetions(que_count); //顯示第幾個題目
        queCounter(que_numb); //目前題數
        clearInterval(counter); //清空計時器時間
        startTimer(timeValue); //開始計算時間
        timeText.textContent = "剩餘時間"; //使用者選擇題目時的顯示
        next_btn.classList.add("d-none"); //使用者選擇題目時的隱藏「下一題按鈕」
    }else{
        clearInterval(counter); //清空計時器時間
    }
}

//顯示題目選項
function showQuetions(index){
    const que_text = document.querySelector(".que_text");

    //標題
    let que_tag = '<span>'+ questions[index].numb + ". " + questions[index].question +'</span>'; 
    //題目項目
    let option_tag = '<div class="option"><span>'+ questions[index].options[0] +'</span></div>'
                   + '<div class="option"><span>'+ questions[index].options[1] +'</span></div>'
                   + '<div class="option"><span>'+ questions[index].options[2] +'</span></div>'
                   + '<div class="option"><span>'+ questions[index].options[3] +'</span></div>';
    que_text.innerHTML = que_tag; 
    option_list.innerHTML = option_tag; 
    
    const option = option_list.querySelectorAll(".option");//陣列型態的option
    //將每個元素塞入 屬性(點擊的話執行optionSelected方法)
    for(i=0; i < option.length; i++){
        option[i].setAttribute("onclick", "optionSelected(this)");
    }
}

//選擇正確或錯誤的Icon
let tickIconTag = '<div class="icon tick"><i class="fa fa-check" aria-hidden="true"></i></div>'; //錯誤的
let crossIconTag = '<div class="icon cross"><i class="fa fa-times" aria-hidden="true"></i></div>'; //正確的


//選擇答案
function optionSelected(answer){
    clearInterval(counter); //刪除紀錄(從新計算)
    let userAns = answer.textContent; //使用者所選的答案
    let correcAns = questions[que_count].answer; //正確答案
    const allOptions = option_list.children.length; //option_list有多少子元素
    
    if(userAns == correcAns){ //如果 使用者選擇到正確答案的話
        userScore += 1; //答對題數+1
        answer.classList.add("correct"); //顯示答對的樣式
        answer.insertAdjacentHTML("beforeend", tickIconTag); //標籤最後一個的子元素添加Icon標籤
        
    }else{ //如果 使用者選擇到錯誤答案的話
        answer.classList.add("incorrect"); //顯示答錯的樣式
        answer.insertAdjacentHTML("beforeend", crossIconTag); //adding cross icon to correct selected option

        for(i=0; i < allOptions; i++){
            if(option_list.children[i].textContent == correcAns){  //並顯示正確答案
                option_list.children[i].setAttribute("class", "option correct"); 
                option_list.children[i].insertAdjacentHTML("beforeend", tickIconTag);
            }
        }
    }
    for(i=0; i < allOptions; i++){ //添加class，預防事件發生
        option_list.children[i].classList.add("disabled"); 
    }
    
    if(que_count === questions.length-1){ //若最後一題時
        result_btn.classList.remove("d-none"); //顯示「結果按鈕」
        next_btn.classList.add("d-none"); //不顯示「下一題按鈕」
        showResult();//預先顯示在另外一個隱藏起來的Modal上，
    }else{
        next_btn.classList.remove("d-none"); //非最後一題時，顯示「下一題按鈕」
    }

}

//show出結果評論
function showResult(){
    const scoreText = result_box.querySelector(".score_text");
    if (userScore > 4){ 
        let scoreTag = `<h4 class="mb-3">有非常棒的健身常識</h4>
                        <p class="text-center">總共題${questions.length}答對題${userScore}</p>`;
        scoreText.innerHTML = scoreTag;  
    }else if(userScore > 3){ 
        let scoreTag = `<h4 class="mb-3">有一定水準的健身常識</h4>
                        <p class="text-center">總共題${questions.length}答對題${userScore}</p>`;
        scoreText.innerHTML = scoreTag;
    }else if(userScore >= 0){ 
        let scoreTag = `<h4 class="mb-3">要增強健身常識，小心健身受傷</h4>
                        <p class="text-center">總共題${questions.length}答對題${userScore}</p>`;
        scoreText.innerHTML = scoreTag;
    }
}

//開始計時
function startTimer(time){
    counter = setInterval(timer, 1000);
    //開始計時的方法
    function timer(){
        timeCount.textContent = time-1; //顯示秒數
        time--; //倒數
        if(time < 9){ //固定二位數
            let addZero = timeCount.textContent; 
            timeCount.textContent = "0" + addZero; 
        }
        if(time < 1){ //時間到的話
            clearInterval(counter); //刪除紀錄(從新計算)
            timeText.textContent = "時間到"; //更換字內容
            const allOptions = option_list.children.length; //option_list有多少子元素
            let correcAns = questions[que_count].answer; //目前該questions[x]的答案
            
            for(i=0; i < allOptions; i++){ //循環每一項元素
                if(option_list.children[i].textContent == correcAns){ //選擇questions[x]答案
                    option_list.children[i].setAttribute("class", "option correct"); //該questions[x]添加class
                    option_list.children[i].insertAdjacentHTML("beforeend", tickIconTag); //在 element 最後一個子元素後面插入Icon標籤
                    // console.log("Time Off: Auto selected correct answer.");
                }
            }
            for(i=0; i < allOptions; i++){
                option_list.children[i].classList.add("disabled"); //在該questions[x]添加 預防事件
            }
            if(que_count === questions.length-1){ //若最後一題時
                result_btn.classList.remove("d-none"); //顯示「結果按鈕」
                next_btn.classList.add("d-none"); //不顯示「下一題按鈕」
            }else{
                next_btn.classList.remove("d-none"); //顯示下一步的按鈕
            }
        }
    }
}

//底部會顯示題目數
const bottom_ques_counter = document.querySelector(".total_que");
function queCounter(index){
    let totalQueCounTag = `<div>總共${questions.length}題，目前第${index}題</div>`;
    bottom_ques_counter.innerHTML = totalQueCounTag;  //adding new span tag inside bottom_ques_counter
}

function reset_result_btn(){
    que_numb = 1;
    que_count = 0;
    userScore=0;
}