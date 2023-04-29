<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
        .el-table .warning-row {
            background: oldlace;
        }
        .el-table .success-row {
            background: #f0f9eb;
        }
    </style>

</head>
<body>
<div id="app">

    <!--搜索表单-->
    <el-form :inline="true" :model="brand" class="demo-form-inline">

        <el-form-item label="当前状态">
            <el-select v-model="brand.status" placeholder="当前状态">
                <el-option label="启用" value="1"></el-option>
                <el-option label="禁用" value="0"></el-option>
            </el-select>
        </el-form-item>

        <el-form-item label="企业名称">
            <el-input v-model="brand.companyName" placeholder="企业名称"></el-input>
        </el-form-item>

        <el-form-item label="品牌名称">
            <el-input v-model="brand.brandName" placeholder="品牌名称"></el-input>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="onSelect">查询</el-button>
        </el-form-item>
    </el-form>

    <!--按钮-->

    <el-row>

        <el-button type="danger" plain  @click="deleteByIds">批量删除</el-button>
        <el-button type="primary" plain @click="dialogVisible = true">新增</el-button>

    </el-row>
    <!--添加数据对话框表单-->
    <el-dialog
            title="编辑品牌"
            :visible.sync="dialogVisible"
            width="30%"
    >
        <el-form ref="form" :model="brand" label-width="80px">
            <el-form-item label="品牌名称">
                <el-input v-model="brand.brandName"></el-input>
            </el-form-item>

            <el-form-item label="企业名称">
                <el-input v-model="brand.companyName"></el-input>
            </el-form-item>

            <el-form-item label="排序">
                <el-input v-model="brand.ordered"></el-input>
            </el-form-item>

            <el-form-item label="备注">
                <el-input type="textarea" v-model="brand.description"></el-input>
            </el-form-item>

            <el-form-item label="状态">
                <el-switch v-model="brand.status"
                           active-value="1"
                           inactive-value="0"
                ></el-switch>
            </el-form-item>


            <el-form-item>
                <el-button type="primary" @click="addBrand">提交</el-button>
                <el-button @click="dialogVisible = false">取消</el-button>
            </el-form-item>
        </el-form>

    </el-dialog>


    <!--表格-->
    <template>
        <el-table
                :data="tableData"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                @selection-change="handleSelectionChange"
        >
            <el-table-column
                    type="selection"
                    width="55">
            </el-table-column>
            <el-table-column
                    type="index"
                    width="50">
            </el-table-column>

            <el-table-column
                    prop="brandName"
                    label="品牌名称"
                    align="center"
            >
            </el-table-column>
            <el-table-column
                    prop="companyName"
                    label="企业名称"
                    align="center"
            >
            </el-table-column>
            <el-table-column
                    prop="companyName"
                    label="企业描述"
                    align="center"
            >
            </el-table-column>
            <el-table-column
                    prop="ordered"
                    align="center"
                    label="排序">
            </el-table-column>
            <el-table-column
                    prop="status"
                    align="center"
                    label="当前状态">
            </el-table-column>


            <el-table-column
                    align="center"
                    label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" plain @click="handleEdit(scope.$index, scope.row)">修改</el-button>
                    <el-button type="danger"  @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>

        </el-table>
    </template>

    <!--分页工具条-->
    <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :current-page.sync="currentPage"
            :page-sizes="[5, 10, 15, 20]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="totalCount">
    </el-pagination>

</div>


<script src="js/vue.js"></script>
<script src="js/axios-0.18.0.js"></script>
<<link rel="stylesheet" href="element-ui/lib/theme-chalk/index.css">
<script src="element-ui/lib/index.js"></script>

<script>
    var flag=1;
    new Vue({
        el: "#app",
        mounted(){
            // 页面加载完成后，发送异步请求，查询数据
            this.brand={}
            this.selectAll();

        },
        methods: {
            selectAll(){
                axios({
                    method:"post",
                    url:"http://localhost:8080/brand-case/brand/selectByPageAndCondition?currentPage="+this.currentPage+"&pageSize="+this.pageSize,
                    data: this.brand
                }).then(resp=> {
                    //这里写this指代的是axios对象,所以要用 var _this = this，指代Vue
                    //设置表格数据
                    this.tableData = resp.data.rows;
                    //设置总记录数
                    this.totalCount=resp.data.totalCount ;
                })
            },
            tableRowClassName({row, rowIndex}) {
                if (rowIndex === 1) {
                    return 'warning-row';
                } else if (rowIndex === 3) {
                    return 'success-row';
                }
                return '';
            },
            // 复选框选中后执行的方法
            handleSelectionChange(val) {

                this.multipleSelection = val;
            },
            // 查询方法
            onSelect() {
                this.currentPage=1;
                //console.log(this.brand);
                this.selectAll();

            },
            // 添加数据
            addBrand(){
                //flag==0代表是修改对话框，flag==1 代表是新增对话框
                if(flag==0){
                    flag=1;
                    axios({
                        method:"post",
                        url:"http://localhost:8080/brand-case/brand/updata",
                        data:this.brand
                    }).then(resp=> {
                        if(resp.data =="updataBrandSuccess"){
                            this.dialogVisible = false;
                            this.brand={};
                            //重新查询数据
                            this.selectAll();
                            this.$message({
                                message: '修改成功',
                                type:'success'
                            });
                        }else {
                            this.$message({
                                message: '公司名和品牌名不能为空！',
                                type:'false'
                            });
                        };
                    })
                }else{
                    var _this = this;
                    axios({
                        method:"post",
                        url:"http://localhost:8080/brand-case/brand/add",
                        data:_this.brand
                    }).then(function (resp) {
                        //这里写this指代的是axios对象,所以要用 var _this = this，指代Vue
                        if(resp.data=="addBrandSuccess"){
                            _this.dialogVisible = false;
                            _this.brand={};
                            //重新查询数据
                            _this.currentPage=Math.ceil(_this.totalCount/_this.pageSize);
                            _this.brand={};
                            _this.selectAll();
                            _this.$message({
                                message: '数据添加成功',
                                type:'success'
                            });
                        }else {
                            _this.$message({
                                message: '请检查公司名和品牌名是否填写！',
                                type:'false'
                            });
                        };
                    })
                }

            },
            //批量删除
            deleteByIds(){
                if(this.multipleSelection.length==0){
                    return
                }
                //弹出确认框
                this.$confirm('此操作将彻底删除该数据，是否继续?','提示',{
                    confirmButtonText:'确定',
                    cancelButtonText:'取消',
                    type: 'warning'
                }).then(() => {
                    //用户点击确认按钮
                    //创建id数组，从复选框this.multipleSelection中获取
                    for(let i=0;i<this.multipleSelection.length;i++){
                        let selectElement=this.multipleSelection[i];
                        this.selectedIds[i]=selectElement.id;
                    }
                    var _this = this;
                    axios({
                        method:"post",
                        url:"http://localhost:8080/brand-case/brand/deleteByIds",
                        data:_this.selectedIds
                    }).then(function (resp) {
                        //这里写this指代的是axios对象,所以要用 var _this = this，指代Vue
                        if(resp.data =="deleteSuccess"){
                            //重新查询数据
                            _this.selectAll();
                            _this.$message({
                                message: '删除成功',
                                type:'success'
                            });
                        }
                    })
                }).catch(( )=> {
                    this.$message({
                        type: 'info' ,
                        message: '已取消删除'});
                });
            },
            handleEdit(index, row) {
                flag=0
                this.brand=row;
                this.dialogVisible = true;
                //转到对话框的监听方法addBrand()方法中去了。
                //console.log(index, row);

            },
            handleDelete(index, row) {
                //console.log(index, row);
                this.selectedIds=[];
                this.selectedIds[0]=row.id;
                //弹出确认框
                this.$confirm('此操作将彻底删除该数据，是否继续?','提示',{
                    confirmButtonText:'确定',
                    cancelButtonText:'取消',
                    type: 'warning'
                }).then(() => {
                    //用户点击确认按钮
                    var _this = this;
                    axios({
                        method:"post",
                        url:"http://localhost:8080/brand-case/brand/deleteByIds",
                        data:_this.selectedIds
                    }).then(function (resp) {
                        //这里写this指代的是axios对象,所以要用 var _this = this，指代Vue
                        if(resp.data =="deleteSuccess"){
                            //重新查询数据
                            _this.selectAll();
                            _this.$message({
                                message: '删除成功',
                                type:'success'
                            });
                        }
                    })
                }).catch(( )=> {
                    this.$message({
                        type: 'info' ,
                        message: '已取消删除'});
                });
            },
            //分页
            handleSizeChange(val) {
                //console.log(`每页 ${val} 条`);
                this.pageSize=val;
                this.selectAll();
            },
            handleCurrentChange(val) {
                //console.log(`当前页: ${val}`);
                this.currentPage=val;
                this.selectAll();
            }

        },
        data() {
            return {
                //每页显示的条数
                pageSize:10,
                //总记录数
                totalCount:100,
                // 当前页码
                currentPage: 1,
                // 添加数据对话框是否展示的标记
                dialogVisible: false,

                // 品牌模型数据
                brand: {
                    status: '',
                    brandName: '',
                    companyName: '',
                    id:"",
                    ordered:"",
                    description:""
                },
                //被选中的id数组
                selectedIds:[],
                // 复选框选中数据集合
                multipleSelection: [],
                // 表格数据
                tableData: [{
                    brandName: '华为',
                    companyName: '华为科技有限公司',
                    ordered: '100',
                    status: "1"
                }]
            }
        }
    })

</script>

</body>
</html>