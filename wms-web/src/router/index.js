import VueRouter from "vue-router";
const routes = [
    {
        path:'/',
        name:'login',
        component:()=>import('../components/Login.vue')
    },
    {
        path:'/Index',
        name:'index',
        component:()=>import('../components/Index.vue'),
        children:[
            {
                path:'/Home',
                name:'home',
                meta:{//导航
                    title:'首页'
                },
                component:()=>import('../components/Home.vue'),//作为二级子路由要跳转到Index.vue的Main区域
            },
            // {
            //     path:'/Admin',
            //     name:'admin',
            //     meta:{//导航
            //         title:'管理员管理'
            //     },
            //     component:()=>import('../components/admin/AdminManage.vue'),
            // },
            // {
            //     path:'/User',
            //     name:'user',
            //     meta:{//导航
            //         title:'用户管理'
            //     },
            //     component:()=>import('../components/user/UserManage.vue'),
            // },
        ]
    }
]
const router = new VueRouter({
    mode:'history',
    routes
})
export function resetRouter() {//重复路由的错误处理
    router.matcher = new VueRouter({
        mode:'history',
        routes: []
    }).matcher
}
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}
export default router;