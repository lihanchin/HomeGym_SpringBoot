let storage = localStorage;
function doFirst(){
	
    if(storage['addItemList'] == null){                     //條件判斷 //防止重整後資料被清空
        storage['addItemList'] = ''; //storage.setItem('addItemList','');//key = value
    }

    countTotal=0;

    if(storage['addItemList'] == ''){                       //如果storage沒東西
        textDiv = document.createElement('div')
        text = document.createElement('p')
        text.className="text-center mt-3"
        text.id='textId'
        textDiv.id='textDivId'
        text.innerText="購物車內無選購商品"
        textDiv.appendChild(text)
        menu.appendChild(textDiv)
    }else{                                                  //如果storage有東西
        let itemString = storage.getItem('addItemList');
        items = itemString.substr(0, itemString.length - 2).split('， '); 
        for(let i = 0; i < items.length; i++){
        let classInfo = storage.getItem(items[i])
        let classPrice = parseInt(classInfo.split('|')[2])
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
    
    
    let itemString = storage.getItem('addItemList');
    items = itemString.substr(0, itemString.length - 2).split('， ');
    
    total = 0
    //取storage
    for(let i = 0; i < items.length; i++){
        let classInfo = storage.getItem(items[i])
        createCheckoutList(classInfo);
        // alert(classInfo)
        let classPrice = parseInt(classInfo.split('|')[2])
        total += classPrice
    }
    document.getElementById('checkoutPrice').innerText ='NT$'+ total;
}


function createCheckoutList(classValue){
    let name = classValue.split('|')[0];
    let classPrice = classValue.split('|')[2];
    let classUrl = classValue.split('|')[3];

    groupItem = document.createElement('li')
    groupItem.className="list-group-item p-3"
    groupItemDiv = document.createElement('div')
    groupItemDiv.className="row d-flex align-items-center justify-content-between"
    itemName = document.createElement('h6')
    itemName.className="col-6"
    itemLink = document.createElement('a')
    itemLink.setAttribute('href',classUrl)
    itemLink.innerText= name
    itemPrice = document.createElement('p')
    itemPrice.className = "col-6 text-end h5"
    itemPrice.innerText = "NT$" + classPrice

    itemName.appendChild(itemLink)
    groupItemDiv.appendChild(itemName)
    groupItemDiv.appendChild(itemPrice)
    groupItem.appendChild(groupItemDiv)

    document.getElementById('listGroup').appendChild(groupItem)


}

window.addEventListener('load', doFirst);