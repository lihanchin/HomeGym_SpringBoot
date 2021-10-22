
function doFirst(){
    if(localStorage['addItemList'] == null){                     //條件判斷 //防止重整後資料被清空
        localStorage['addItemList'] = ''; //localStorage.setItem('addItemList','');//key = value
    }

    countTotal=0;

    if(localStorage['addItemList'] == ''){                       //如果localStorage沒東西
        // console.log('hi')
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
        console.log(items);
        for(let i = 0; i < items.length; i++){
            let classInfo = localStorage.getItem(items[i]) //課程資訊
            console.log(classInfo)
            createList(classInfo);
            let classPrice = parseInt(classInfo.split('|')[2]) //pirce
            console.log(classPrice)
            countTotal += classPrice
        }
        total = document.createElement('p')
        total.className="mb-2 me-2 d-flex justify-content-end"
        checkoutLink = document.createElement('a')
        checkoutLink.setAttribute('href','./shoppingCar.html')
        checkoutLink.className="btn btn-primary d-block"
        checkoutLink.innerText='前往結帳'
        lastDiv = document.createElement('div')
        lastDiv.appendChild(total)
        lastDiv.appendChild(checkoutLink)
        document.getElementById('dropdown-menu').appendChild(lastDiv)
        total.innerText ='總計 NT$'+ countTotal;
    }
    
    
    function createList(classValue){                            //載入購物車時
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
    }
}
window.addEventListener('load',doFirst);