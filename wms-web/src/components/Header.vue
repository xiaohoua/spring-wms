<template>
    <div style="display: flex;line-height: 60px">
      <div style="margin-top: 8px">
        <i :class="icon" style="font-size: 20px;cursor: pointer;" @click="collapse"></i>
      </div>
      <div style="flex: 1;text-align: center;font-size: 34px;">
        <span>欢迎来到管理系统</span>
      </div>
      <span>{{user.name}}</span>
        <el-dropdown>
          <i class="el-icon-arrow-down" style="margin-left: 15px"></i>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="toUser()">个人中心</el-dropdown-item>
            <el-dropdown-item @click.native="logout()">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
import collapse from "element-ui/packages/collapse";

export default {
  name: "Header",
  data(){
    return {
      user : JSON.parse(sessionStorage.getItem('CurUser'))//login.vue   JSON.parse转字符串
    }
  },
  props:{
    icon:String
  },
  methods:{
    toUser(){
      console.log('to_user')
      this.$router.push("/Home") //路由跳转//路由在index.js//Index的子路由
    },
    logout(){
      console.log('logout')

      this.$confirm('您确定退出登录吗？', '提示', {
        confirmButtonText: '确定',  //确认按钮的文字显示
        type: 'warning',
        center: true, //文字居中显示

      })
          .then(() => {
            this.$message({
              type:'success',
              message:'退出登录成功！'
            })
            this.$router.push("/") //退出跳转//路由跳转

            sessionStorage.clear() //清空数据
          })
          .catch(() => {
            this.$message({
              type:'info',
              message:'已取消退出登录'
            })

          });

    },
    collapse(){
      //子组件传给父组件
      this.$emit('doCollapse')
    }
  },
  created() {
    this.$router.push("/Home")
  }
}
</script>

<style scoped>

</style>