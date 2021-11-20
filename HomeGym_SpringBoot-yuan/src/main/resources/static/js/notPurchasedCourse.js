
let notPurchasedCourse = new Vue({
    el:'#app',
    data :{
        keep:{
            isLike: true,
            islinkIcon:`<i class="fa fa-heart" aria-hidden="true"></i> 已加入收藏清單`,
            notLinkIcon:`<i class="fa fa-heart-o" aria-hidden="true"></i> 加入收藏清單`,
        },
        shopping:{
            isLike: true,
            islinkIcon:`<i class="fa fa-heart" aria-hidden="true"></i> 已加入購物車`,
            notLinkIcon:`<i class="fa fa-heart-o" aria-hidden="true"></i> 加入購物車`,
        }
    },
    methods:{
        keepToggle: function(){
            this.keep.isLike = !this.keep.isLike;
            console.log(this.keep.isLike);
        },
        shoppingToggle: function(){
            this.shopping.isLike = !this.shopping.isLike;
            console.log(this.shopping.isLike);
        }
    }
});
