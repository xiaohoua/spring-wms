<template>
  <div>
    <div style="margin-bottom: 5px;">
      <el-input v-model="name" placeholder="请输入物品名" suffix-icon="el-icon-search" style="width: 200px;"
                @keyup.enter.native="loadPost"></el-input>
      <el-select v-model="storage" placeholder="请选择仓库" style="margin-left: 5px">
        <el-option
            v-for="item in storageData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
      <el-select v-model="commodityCategory" placeholder="请选择商品种类" style="margin-left: 5px">
        <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>

      <el-button type="primary" style="margin-left: 5px;" @click="loadPost">查询</el-button>
      <el-button type="success" @click="resetParam">重置</el-button>

      <el-button type="primary" style="margin-left: 5px;" @click="add" v-if="user.roleId!=2">新增</el-button>
      <el-button type="primary" style="margin-left: 5px;" @click="inGoods" v-if="user.roleId!=2">入库</el-button>
      <el-button type="primary" style="margin-left: 5px;" @click="outGoods" v-if="user.roleId!=2">出库</el-button>
    </div>
    <el-table :data="tableData"
              :header-cell-style="{ background: '#f2f5fc', color: '#555555' }"
              border
              highlight-current-row
              @current-change="selectCurrentChange"
    >
      <el-table-column prop="id" label="ID" width="60">
      </el-table-column>
      <el-table-column prop="no" label="商品码" width="80">
      </el-table-column>
      <el-table-column prop="name" label="物品名" width="100">
      </el-table-column>
      <el-table-column prop="image" label="图片" >
      </el-table-column>
      <el-table-column
          prop="commodityCategory"
          label="商品种类" width="120"
          :formatter="formatGoodstype"
          :filters="[{ text: '饼干', value: '饼干' }, { text: '膨化', value: '膨化' },
                      { text: '辣条', value: '辣条' }, { text: '饮料', value: '饮料' }]"
          filter-placement="bottom-end">
        <template v-slot:default="scope">
          <el-tag
              :type="setType(scope.row.commodityCategory)"
              disable-transitions>{{scope.row.commodityCategory === 0 ? '饼干' :
              scope.row.commodityCategory === 1 ? '膨化' :
                  scope.row.commodityCategory === 2 ? '辣条' :'饮料'}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="stock" label="库存" width="60">
      </el-table-column>
      <el-table-column prop="storage" label="仓库" width="60" :formatter="formatStorage">
      </el-table-column>
      <el-table-column prop="salesUnitPrice" label="单价" >
      </el-table-column>
      <el-table-column prop="dateOfManufacture" label="生产日期" >
      </el-table-column>
      <el-table-column prop="qualityGuaranteePeriod" label="保质期" >
      </el-table-column>
      <el-table-column prop="operate" label="操作" v-if="user.roleId!=2">
        <template v-slot:default="scope">
          <el-button size="small" type="success" @click="mod(scope.row)">编辑</el-button>
          <el-popconfirm
              title="确定删除吗？"
              @confirm="del(scope.row.id)"
              style="margin-left: 5px;"
          >
            <el-button slot="reference" size="small" type="danger" >删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNum"
        :page-sizes="[5, 10, 20,30]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
    </el-pagination>

    <el-dialog
        title="物品管理"
        :visible.sync="centerDialogVisible"
        width="30%"
        center>
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="物品名" prop="name">
          <el-col :span="20">
            <el-input v-model="form.name"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <el-col :span="20">
            <el-input v-model="form.image"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="商品类别" prop="commodityCategory">
          <el-col :span="20">
            <el-select v-model="form.commodityCategory" placeholder="请选择商品种类" style="margin-left: 5px">
              <el-option
                  v-for="item in goodstypeData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>
          </el-col>
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-col :span="20">
            <el-input v-model="form.stock"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="仓库" prop="storage" >
          <el-col :span="20">
            <el-select v-model="form.storage" placeholder="请选择仓库" style="margin-left: 5px">
              <el-option
                  v-for="item in storageData"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
            </el-select>

          </el-col>
        </el-form-item>
        <el-form-item label="单价" prop="sales_unitPrice">
          <el-col :span="20">
            <el-input v-model="form.salesUnitPrice"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="生产日期" prop="dateOfManufacture">
          <el-col :span="20">
            <el-input v-model="form.dateOfManufacture"></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="保质期" prop="qualityGuaranteePeriod">
          <el-col :span="20">
            <el-input v-model="form.qualityGuaranteePeriod"></el-input>
          </el-col>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="centerDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="save">确 定</el-button>
  </span>
    </el-dialog>

    <el-dialog
        title="出入库"
        :visible.sync="inDialogVisible"
        width="30%"
        center>
      <el-dialog
          width="60%"
          title="用户选择"
          :visible.sync="innerVisible"
          append-to-body>
        <SelectUser @doSelectUser="doSelectUser"></SelectUser>
        <span slot="footer" class="dialog-footer">
            <el-button @click="innerVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmUser">确 定</el-button>
        </span>
      </el-dialog>
      <el-form ref="form1" :rules="rules1" :model="form1" label-width="80px">
        <el-form-item label="物品名">
          <el-col :span="20">
            <el-input v-model="form1.goodsname" readonly></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="申请人">
          <el-col :span="20">
            <el-input v-model="form1.username" readonly @click.native="selectUser"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="数量" prop="count">
          <el-col :span="20">
            <el-input v-model="form1.count"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-col :span="20">
            <el-input v-model="form1.remark"></el-input>
          </el-col>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="inDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doInGoods">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>
<script>
import row from "element-ui/packages/row";
import SelectUser from '../user/SelectUser.vue'

export default {
  name: "GoodsManage",
  computed: {
    row() {
      return row
    }
  },
  components:{SelectUser},
  data() {
    return {
      user : JSON.parse(sessionStorage.getItem('CurUser')),
      storageData:[],
      goodstypeData:[],
      tableData: [],
      pageSize:10,
      pageNum:1,
      total:0,
      name:'',
      storage:'',
      commodityCategory:'',
      centerDialogVisible:false,
      inDialogVisible:false,
      innerVisible:false,
      currentRow:{},
      tempUser:{},
      form:{
        no:'',
        image:'',
        name:'',
        dateOfManufacture:'',
        stock:'0',
        salesUnitPrice:'',
        commodityCategory:'2',
        qualityGuaranteePeriod:'',
        storage:''
      },
      form1:{
        goods:'',
        count:'',
        goodsname:'',
        username:'',
        storage:'',
        userid:'',
        adminId:'',
        remark:'',
        action:'1'
      },
      rules1:{

      },
      rules: {
        commodityCategory:[
          {required: true, message: '请输入物品种类', trigger: 'blur'}
        ],
        storage:[
          {required: true, message: '请输入仓库', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入物品名', trigger: 'blur'}
        ],
      }
    }
  },
  //方法
  methods: {
    doSelectUser(val){
      console.log(val)
      this.tempUser = val
    },
    confirmUser(){
      this.form1.username = this.tempUser.name
      this.form1.userid = this.tempUser.id

      this.innerVisible = false

    },
    selectCurrentChange(val) {
      this.currentRow = val;
    },
    formatStorage(row){
      let temp = this.storageData.find(item=>{//可以不用find用foreach匹配
        return item.id == row.storage
      })
      return temp && temp.name
    },
    formatGoodstype(row){
      let temp = this.goodstypeData.find(item=>{//可以不用find用foreach匹配
        return item.id == row.commodityCategory
      })
      return temp && temp.name
    },
    setType(myType){
      if (myType===0){
        return 'danger'
      }else if (myType===1){
        return 'success'
      }else if(myType===2){
        return 'info'
      }else {
        return 'warning'
      }
    },
    resetForm() {//重置表单
      this.$refs.form.resetFields();
    },
    resetInForm(){
      this.$refs.form1.resetFields();
    },
    del(id){
      this.$axios.get(this.$httpUrl+'/Goods/del?id='+id).then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible=false;//关闭表单
          this.loadPost();//刷新页面
          //this.resetForm();表单重置
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      })
    },
    mod(row){
      this.centerDialogVisible=true;
      this.$nextTick(()=>{
        //赋值到表单
        this.form.id = row.id;
        this.form.name = row.name;
        this.form.commodityCategory = row.commodityCategory;
        this.form.stock = row.stock;
        this.form.storage = row.storage;
        this.form.salesUnitPrice = row.salesUnitPrice;
        this.form.dateOfManufacture = row.dateOfManufacture;
        this.form.qualityGuaranteePeriod = row.qualityGuaranteePeriod;

      })
    },
    add(){
      this.centerDialogVisible=true;
      this.$nextTick(()=>{
        this.resetForm();
        this.form.id = ''
      })
    },
    inGoods(){
      if(!this.currentRow.id){
        alert('请选择记录');
        return;

      }
      this.inDialogVisible=true;
      this.$nextTick(()=>{
        this.resetInForm();
      })
      this.form1.goodsname = this.currentRow.name
      this.form1.goods = this.currentRow.id
      this.form1.adminId = this.user.id
      this.form1.action = '1'
    },
    outGoods(){
      if(!this.currentRow.id){
        alert('请选择记录');
        return;

      }
      this.inDialogVisible=true;
      this.$nextTick(()=>{
        this.resetInForm();
      })
      this.form1.goodsname = this.currentRow.name
      this.form1.goods = this.currentRow.id
      this.form1.adminId = this.user.id
      this.form1.action = '2'
    },
    selectUser(){
      this.innerVisible=true
    },
    doSave(){
      //约定的验证规则
      this.$axios.post(this.$httpUrl+'/Goods/save',this.form).then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible=false;//关闭表单
          this.loadPost();//刷新页面
          //this.resetForm();表单重置
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      })
    },
    doMod(){
      //约定的验证规则
      this.$axios.post(this.$httpUrl+'/Goods/update',this.form).then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.centerDialogVisible=false;//关闭表单
          this.loadPost();//刷新页面
          //this.resetForm();表单重置
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      })
    },
    save(){
      this.$refs.form.validate((valid) => {//先进行表单验证
        if (valid) {
          if(this.form.id){//判断保存或修改，可用saveormod更简单
            this.doMod();
          }else{
            this.doSave();
          }
        }else {
          console.log('error submit!!');
          return false;
        }
      });

    },
    doInGoods(){
      //约定的验证规则
      this.$axios.post(this.$httpUrl+'/record/save',this.form1).then(res=>res.data).then(res=>{
        if(res.code==200){
          this.$message({
            message: '操作成功！',
            type: 'success'
          });
          this.inDialogVisible=false;//关闭表单
          this.loadPost();//刷新页面
          this.resetInForm();//表单重置
        }else{
          this.$message({
            message: '操作失败！',
            type: 'error'
          });
        }
      })
    },
    //返回符合条件的数据
    //参数分别是：选中的条件 、当前行 、当前列
    filterTag(value, row, column) {//筛选
      const property = column['property'];
      return row[property] === value;
    },

    //分页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageNum=1
      this.pageSize=val
      this.loadPost()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum=val
      this.loadPost()
    },
    resetParam(){
      this.name=''
      this.storage=''
      this.commodityCategory=""
    },
    loadPost(){
      this.$axios.post(this.$httpUrl+'/Goods/listPage',{
        pageSize:this.pageSize,
        pageNum:this.pageNum,
        param:{
          name:this.name,
          commodityCategory:this.commodityCategory+'',
          storage:this.storage+''
        }
      }).then(res=>res.data).then(res=>{
        if(res.code==200){
          this.tableData=res.data
          this.total=res.total
        }else{
          alert('获取数据失败')
        }

      })
    },
    loadStorage(){
      this.$axios.get(this.$httpUrl+'/storage/list').then(res=>res.data).then(res=>{
        if(res.code==200){
          this.storageData=res.data
        }else{
          alert('获取数据失败')
        }

      })
    },
    loadGoodstype(){
      this.$axios.get(this.$httpUrl+'/goodstype/list').then(res=>res.data).then(res=>{
        if(res.code==200){
          this.goodstypeData=res.data
        }else{
          alert('获取数据失败')
        }

      })
    }
  },
  beforeMount() {
    this.loadStorage()
    this.loadGoodstype()
    this.loadPost()
  },
  mounted() {
    console.log(this.user);
  }
}
</script>

<style scoped>

</style>