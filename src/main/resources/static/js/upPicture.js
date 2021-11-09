function openFile(event){
    var inputUpPicture = event.target; //取得上傳檔案
    var reader = new FileReader(); //建立FileReader物件
  
    reader.readAsDataURL(inputUpPicture.files[0]); //以.readAsDataURL將上傳檔案轉換為base64字串
  
    reader.onload = function(){ //FileReader取得上傳檔案後執行以下內容
      var dataURL = reader.result; //設定變數dataURL為上傳圖檔的base64字串
      $('#output').attr('src', dataURL).show(); //將img的src設定為dataURL並顯示
    };
  }