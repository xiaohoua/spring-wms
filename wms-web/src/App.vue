<template>
  <div id="app">
<!--    <Index></Index>-->
    <router-view></router-view>
  </div>
</template>

<script>


// import Index from "@/components/Index.vue";

export default {
  name: 'App',
  components: {  },
  data(){
    return {//登录后取user
      user: JSON.parse(sessionStorage.getItem('CurUser')),
    }
  },
  watch:{//确保刷新后路由没有问题
    '$store.state.menu':{
      handler(val,old){//val:当前值  old：旧值
        console.log(val,old)
        if(!old && this.user && this.user.no){//old 旧的值没有了
          this.$store.commit("setMenu",val)//再次调用路由
        }
      },
      immediate:true
    }
  }
}
</script>

<style>
#app {
  height: 100%;

}
</style>
