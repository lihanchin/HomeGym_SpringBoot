
//健身規劃的排序
const dragArea_left = document.querySelector(".wrapper_left");

new Sortable(dragArea_left,{
  animation: 350,
  handle: ".my-handle"
});