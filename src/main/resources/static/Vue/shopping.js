let keyword = (location.search.split('keyword='))[1]

let partOfBody = (location.search.split('partOfBody='))[1]
new Vue({
    el:"#app",
    data:{
        totalPage:"",
        CourseValue:"",
        shoppingCourse:[],
        buyCourse:[]
    },
    methods: {
        pushCourseValue(item){
            let classInfo =  this.CourseValue = item.courseName+'|'+item.courseImage+'|'+item.price+'|'+'/product?id='+item.courseId;
            if(localStorage['addItemList'] == ''){              //如果是第一次加入購物車
                textDivId.remove(textId)
                this.addCountArea()
                this.addClass('classId'+item.courseId,classInfo);
            }else{                                         //加入時購物車已經有東西
                this.addClass('classId'+item.courseId,classInfo);
                this.buyCourse.push('classId'+item.courseId)
            }
        },
        clickPage(index){
            let pageNo = index+1

            if(keyword!=null&keyword!=''){
                if(pageNo!=null&pageNo!=''){
                    axios.get("/store/keyword?keyword="+keyword+"&page="+pageNo).then((res) =>{
                        this.shoppingCourse = res.data.courseList;
                        this.totalPage = res.data.totalPage;
                    })
                }
            }else {
                if(partOfBody!=null&&partOfBody!=''){
                    axios.get("/store/allCourse?page="+pageNo+"&partOfBody="+partOfBody).then((res) =>{
                        this.shoppingCourse = res.data.currentPage;
                        this.totalPage = res.data.totalPage;
                    })
                }else {
                    axios.get("/store/allCourse?page="+pageNo).then((res) =>{
                        this.shoppingCourse = res.data.currentPage;
                        this.totalPage = res.data.totalPage;
                    })
                }
            }
        },
        doFirst(){

            if(localStorage['addItemList'] == null){                     //條件判斷 //防止重整後資料被清空
                localStorage['addItemList'] = ''; //localStorage.setItem('addItemList','');//key = value
            }

            countTotal=0;

            if(localStorage['addItemList'] == ''){                       //如果localStorage沒東西
                textDiv = document.createElement('div')
                text = document.createElement('p')
                text.className="text-center mt-3"
                text.id='textId'
                textDiv.id='textDivId'
                text.innerText="購物車內無選購商品"
                textDiv.appendChild(text)
                menu.appendChild(textDiv)
            }else{                                                  //如果localStorage有東西
                let itemString = localStorage.getItem('addItemList');
                items = itemString.substr(0, itemString.length - 2).split('， '); //id名稱的陣列
                // console.log(items);
                this.buyCourse = items;
                for(let i = 0; i < items.length; i++){
                    let classInfo = localStorage.getItem(items[i]) //課程資訊
                    let classPrice = parseInt(classInfo.split('|')[2]) //pirce
                    countTotal += classPrice
                }
                total = document.createElement('p')
                total.className="mb-2 me-2 d-flex justify-content-end"
                checkoutLink = document.createElement('a')
                checkoutLink.setAttribute('href','/shoppingCart')
                checkoutLink.className="btn btn-primary d-block"
                checkoutLink.innerText='前往結帳'
                lastDiv = document.createElement('div')
                lastDiv.appendChild(total)
                lastDiv.appendChild(checkoutLink)
                document.getElementById('dropdown-menu').appendChild(lastDiv)
                total.innerText ='總計 NT$'+ countTotal;
            }


        },
        addClass(classId,classValue){                      //加入購物車時
            if(localStorage[classId]){ //如果有表示選過
                // document.getElementById('inputBtn').innerText = '前往購物車';
            }else{
                localStorage['addItemList'] += `${classId}， `;
                localStorage.setItem(classId,classValue);
                let name = classValue.split('|')[0];
                let classImg = classValue.split('|')[1];
                let classPrice = classValue.split('|')[2];
                let classUrl = classValue.split('|')[3];

                cardDiv = document.createElement('div')
                cardDiv.className="card border-0 mb-2 border-bottom pb-2"
                classLink = document.createElement('a')
                classLink.setAttribute('href',classUrl)
                classLink.className = "row g-0"
                menu = document.getElementById('menu')
                cardDiv.appendChild(classLink)
                menu.appendChild(cardDiv)

                colDiv = document.createElement('div')
                colDiv.className="col-md-4";
                image = document.createElement('img')
                image.className =" w-100";
                image.src= classImg
                colDiv.appendChild(image)
                classLink.appendChild(colDiv)

                colBodyDiv = document.createElement('div')
                colBodyDiv.className="col-md-8"
                bodyDiv = document.createElement('div')
                bodyDiv.className="card-body"
                title = document.createElement('h5')
                title.className="card-title"
                title.innerText=name
                price = document.createElement('p')
                price.className="card-text text-muted"
                price.innerText='NT$' + classPrice

                bodyDiv.appendChild(title)
                bodyDiv.appendChild(price)
                bodyDiv.appendChild(price)
                colBodyDiv.appendChild(bodyDiv)
                classLink.appendChild(colBodyDiv)

                countTotal+= parseInt(classPrice)
                document.getElementById('total')
                total.innerText ='總計 NT$'+ countTotal;
            }
        },
        createList(classValue){                            //載入購物車時
            let name = classValue.split('|')[0];
            let classImg = classValue.split('|')[1];
            let classPrice = classValue.split('|')[2];
            let classUrl = classValue.split('|')[3];


            cardDiv = document.createElement('div')
            cardDiv.className="card border-0 mb-2 border-bottom pb-2"
            classLink = document.createElement('a')
            classLink.setAttribute('href',classUrl)
            classLink.className = "row g-0"
            menu = document.getElementById('menu')
            cardDiv.appendChild(classLink)
            menu.appendChild(cardDiv)

            colDiv = document.createElement('div')
            colDiv.className="col-md-4"
            image = document.createElement('img')
            image.className =" w-100"
            image.src= classImg
            colDiv.appendChild(image)
            classLink.appendChild(colDiv)

            colBodyDiv = document.createElement('div')
            colBodyDiv.className="col-md-8"
            bodyDiv = document.createElement('div')
            bodyDiv.className="card-body"
            title = document.createElement('h5')
            title.className="card-title"
            title.innerText=name
            price = document.createElement('p')
            price.className="card-text text-muted"
            price.innerText='NT$' + classPrice

            bodyDiv.appendChild(title)
            bodyDiv.appendChild(price)
            bodyDiv.appendChild(price)
            colBodyDiv.appendChild(bodyDiv)
            classLink.appendChild(colBodyDiv)
        },
        addCountArea(){                                    //建構前往結帳區
            total = document.createElement('p')
            total.className="mb-2 me-2 d-flex justify-content-end"
            checkoutLink = document.createElement('a')
            checkoutLink.setAttribute('href','/shoppingCart')
            checkoutLink.className="btn btn-primary d-block"
            checkoutLink.innerText='前往結帳'
            lastDiv = document.createElement('div')
            lastDiv.appendChild(total)
            lastDiv.appendChild(checkoutLink)
            document.getElementById('dropdown-menu').appendChild(lastDiv)

        }

    },
    mounted() {
        console.log("==================")
        console.log(partOfBody)
        console.log(keyword)
        if(keyword!=null&keyword!=''){
            console.log("---------------------")
            axios.get("/store/keyword?keyword="+keyword).then((res) =>{
                console.log(res.data)
                this.shoppingCourse = res.data.courseList;
                this.totalPage = res.data.totalPage;
            })
        } else if(partOfBody!=null&partOfBody!='') {
            console.log("搜部位")
            if(partOfBody !='所有') {
                axios.get("/store/allCourse?partOfBody="+partOfBody).then((res) =>{
                    this.shoppingCourse = res.data.currentPage;
                    this.totalPage = res.data.totalPage;
                })
                console.log("搜部位2222222222")

            }else {
                console.log("搜部位11111111")
                window.location.replace("/shop")
            }
        } else {
            console.log("????????????")
            axios.get("/store/").then((res) =>{
                this.shoppingCourse = res.data.firstPage;
                this.totalPage = res.data.totalPage;
            })
        }
        window.addEventListener('load',this.doFirst);

    }
})