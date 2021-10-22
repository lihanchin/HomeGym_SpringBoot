let storage = localStorage;
function doFirst(){
	let itemString = storage.getItem('addItemList');
    items = itemString.substr(0, itemString.length - 2).split('， ');
    // console.log(items);
    
    total = 0
    if(storage['addItemList'] == ''){
        createBlankArea();
    }else{

        //取storage
        for(let i = 0; i < items.length; i++){
            let classInfo = storage.getItem(items[i])
            // console.log(classInfo)
            createCartList(items[i],classInfo);
            let classPrice = parseInt(classInfo.split('|')[2])
            // console.log(classPrice)
            total += classPrice
        }
        document.getElementById('total').innerText ='NT$'+ total;
    }

}

function createCartList(classId,classValue){
    // console.log(classValue)
    let name = classValue.split('|')[0];
    let classImage = classValue.split('|')[1];
    let classPrice = classValue.split('|')[2];
    let classUrl = classValue.split('|')[3];

    cardDiv = document.createElement('div')
    cardDiv.className="card mt-3 mb-3"
    rowDiv = document.createElement('div')
    rowDiv.className="row  d-flex align-items-center"

    cardDiv.appendChild(rowDiv)
    document.getElementById('cartList').append(cardDiv);
    //圖
    imageDiv = document.createElement('div')
    imageDiv.className="col-4"
    image = document.createElement('img')
    image.className="img-fluid"
    image.src = classImage;

    imageDiv.appendChild(image)
    rowDiv.appendChild(imageDiv)
    
    //課程資料
    colDiv = document.createElement('div')
    colDiv.className="col-8 row"

    classNameDiv = document.createElement('div')
    classNameDiv.className="col-6 d-flex flex-column justify-content-center"
    classTitle = document.createElement('h6')
    classTitle.innerText='課程名稱'
    classLink = document.createElement('a')
    classLink.setAttribute('href', classUrl);
    classLink.innerText = name

    classNameDiv.appendChild(classTitle)
    classNameDiv.appendChild(classLink)
    colDiv.appendChild(classNameDiv)

    classPriceDiv = document.createElement('div')
    classPriceDiv.className="col-6 pt-3"
    price = document.createElement('p')
    price.className="h3 text-end me-4"
    price.innerText = 'NT$'+classPrice
    btnDiv = document.createElement('div')
    btnDiv.className="d-grid gap-2 d-md-flex justify-content-md-end me-4"
    btnDiv.id=classId
    delBtn = document.createElement('button')
    delBtn.className="btn btn-outline-primary"
    delBtn.innerText="移除"
    // bucketListBtn = document.createElement('button')
    // bucketListBtn.className="btn btn-outline-primary"
    // bucketListBtn.innerText="移至收藏"

    btnDiv.appendChild(delBtn)
    // btnDiv.appendChild(bucketListBtn)
    classPriceDiv.appendChild(price)
    classPriceDiv.appendChild(btnDiv)


    colDiv.appendChild(classPriceDiv)
    rowDiv.appendChild(colDiv)

    delBtn.addEventListener('click',deletClass)
    // bucketListBtn.addEventListener('click',deletClass)

}

function deletClass(){
    // alert(this.parentNode.id)
    //扣金額
    let classId = this.parentNode.id
    let value = storage.getItem(classId)
    let price = parseInt(value.split('|')[2])
    total -= price;
    document.getElementById('total').innerText ='NT$'+ total;

    // //刪storage
    storage.removeItem(classId)
    storage['addItemList'] = storage['addItemList'].replace(`${classId}， `,'');//找到子字串取代空字串


    //刪節點
    this.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.removeChild(this.parentNode.parentNode.parentNode.parentNode.parentNode)
    
    if(storage['addItemList'] == ''){
        createBlankArea()
    }

}


function createBlankArea(){
    let div = document.createElement('div')
    div.className="my-5 text-center"
    let text = document.createElement('h2')
    text.className="mb-5"
    text.innerText='尚未選購課程'
    let link = document.createElement('a')
    link.setAttribute('href','./shopping.html')
    link.innerText='前往選購'
    div.appendChild(text)
    div.appendChild(link)
    document.getElementById('cartList').appendChild(div)
}

window.addEventListener('load', doFirst);