<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;" v-show="false">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.name" placeholder="医院名称"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getUsers">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-show="false" @click="handleAdd">下载</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="users" highlight-current-row v-loading="listLoading"
				  @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column prop="id" label="编号" width="60">
			</el-table-column>
			<el-table-column prop="hospitalname" label="医院名称" width="120" >
			</el-table-column>
			<el-table-column prop="hospitaltype" label="医院类型" width="100"  >
			</el-table-column>
			<el-table-column prop="directorname" label="科主任姓名" width="100" >
			</el-table-column>
			<el-table-column prop="directorphone" label="手机" width="120" >
			</el-table-column>
			<el-table-column prop="directoremail" label="邮箱" width="120" >
			</el-table-column>


			<el-table-column prop="messagername" label="信息员姓名" width="100" >
			</el-table-column>
			<el-table-column prop="messagerphone" label="手机" width="120" >
			</el-table-column>
			<el-table-column prop="messageremail" label="邮箱" width="120" >
			</el-table-column>


			<el-table-column label="操作" width="150" v-if="false">
				<template scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="primary" @click="batchRemove" :disabled="this.sels.length===0">批量下载</el-button>
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange"
						   :page-size="1"
						   :total="total" style="float:right;">
			</el-pagination>
		</el-col>



		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
				<el-form-item label="姓名" prop="name">
					<el-input v-model="editForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别">
					<el-radio-group v-model="editForm.sex">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="年龄">
					<el-input-number v-model="editForm.age" :min="0" :max="200"></el-input-number>
				</el-form-item>
				<el-form-item label="生日">
					<el-date-picker type="date" placeholder="选择日期" v-model="editForm.birth"></el-date-picker>
				</el-form-item>
				<el-form-item label="地址">
					<el-input type="textarea" v-model="editForm.addr"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--新增界面-->
		<el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false">
			<el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
				<el-form-item label="姓名" prop="name">
					<el-input v-model="addForm.name" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="性别">
					<el-radio-group v-model="addForm.sex">
						<el-radio class="radio" :label="1">男</el-radio>
						<el-radio class="radio" :label="0">女</el-radio>
					</el-radio-group>
				</el-form-item>
				<el-form-item label="年龄">
					<el-input-number v-model="addForm.age" :min="0" :max="200"></el-input-number>
				</el-form-item>
				<el-form-item label="生日">
					<el-date-picker type="date" placeholder="选择日期" v-model="addForm.birth"></el-date-picker>
				</el-form-item>
				<el-form-item label="地址">
					<el-input type="textarea" v-model="addForm.addr"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="addFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
	import util from '../../common/js/util'
	//import NProgress from 'nprogress'
	import {
		getUserListPage,
		removeUser,
		batchRemoveUser,
		editUser,
		addUser,
		getBaseInfos,
		batchdownlaodBaseInfo, batchdownlaodBaseInfo2, batchdownlaodBaseInfo3
	} from '../../api/api';

	export default {
		data() {
			return {
				filters: {
					name: ''
				},
				users: [],
				total: 0,
				page: 1,
				pageSize:10,
				listLoading: false,
				sels: [],//列表选中列

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					name: [
						{ required: true, message: '请输入医院名称', trigger: 'blur' }
					]
				},
				//编辑界面数据
				editForm: {
					id: 0,
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
				},

				addFormVisible: false,//新增界面是否显示
				addLoading: false,
				addFormRules: {
					name: [
						{ required: true, message: '请输入医院名称', trigger: 'blur' }
					]
				},
				//新增界面数据
				addForm: {
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
				}

			}
		},
		methods: {
			//性别显示转换
			formatSex: function (row, column) {
				return row.sex == 1 ? '男' : row.sex == 0 ? '女' : '未知';
			},
			handleCurrentChange(val) {
				this.page = val;
				this.getUsers();
			},
			//获取用户列表
			getUsers() {
				var _this=this;
				let params = {
					pageNum: this.page,
					pageSize:this.pageSize
					// name: this.filters.name
				};
				this.listLoading = true;
				//NProgress.start();
				// getUserListPage(para).then((res) => {
				// 	this.total = res.data.total;
				// 	this.users = res.data.users;
				// 	this.listLoading = false;
				// 	//NProgress.done();
				// });
				getBaseInfos(params).then((res)=>{
					console.log("===接口数据=====")
					console.log(res)
					console.log(res.data.status)
					if(res.data.status==0){//成功
						console.log("===成功获取数据=====")
						this.total = res.data.data.pages;
						this.users=res.data.data.list
						this.listLoading = false;
					}else if(res.data.status==6){//需要登录
						console.log("===跳转到登录=====")
						_this.$router.push({path:"/login"});
					}


				})
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { id: row.id };
					removeUser(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getUsers();
					});
				}).catch(() => {

				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				this.editFormVisible = true;
				this.editForm = Object.assign({}, row);
			},
			//显示新增界面
			handleAdd: function () {
				this.addFormVisible = true;
				this.addForm = {
					name: '',
					sex: -1,
					age: 0,
					birth: '',
					addr: ''
				};
			},
			//编辑
			editSubmit: function () {
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.editLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.editForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							editUser(para).then((res) => {
								this.editLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['editForm'].resetFields();
								this.editFormVisible = false;
								this.getUsers();
							});
						});
					}
				});
			},
			//新增
			addSubmit: function () {
				this.$refs.addForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.addLoading = true;
							//NProgress.start();
							let para = Object.assign({}, this.addForm);
							para.birth = (!para.birth || para.birth == '') ? '' : util.formatDate.format(new Date(para.birth), 'yyyy-MM-dd');
							addUser(para).then((res) => {
								this.addLoading = false;
								//NProgress.done();
								this.$message({
									message: '提交成功',
									type: 'success'
								});
								this.$refs['addForm'].resetFields();
								this.addFormVisible = false;
								this.getUsers();
							});
						});
					}
				});
			},
			selsChange: function (sels) {
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.id).toString();
				console.log("======ids======")
				console.log(ids)
				let para = { ids: ids };
				batchdownlaodBaseInfo3(ids)

				// batchdownlaodBaseInfo(para).then((res) => {
				// 	console.log(res)
				// 	this.listLoading = false;
				// 	//NProgress.done();
				//
				// 	let blob = new Blob([res.data], { type: 'application/vnd.ms-excel;charset=utf-8' });
				// 	let downloadElement = document.createElement('a');
				// 	let href = window.URL.createObjectURL(blob); //创建下载的链接
				// 	downloadElement.href = href;
				// 	downloadElement.download = '医院基础信息.xls'; //下载后文件名
				// 	document.body.appendChild(downloadElement);
				// 	downloadElement.click(); //点击下载
				// 	document.body.removeChild(downloadElement); //下载完成移除元素
				// 	window.URL.revokeObjectURL(href); //释放掉blob对象
				//
				//
				// 	// this.$message({
				// 	// 	message: '成功',
				// 	// 	type: 'success'
				// 	// });
				//
				// });
			}
		},
		mounted() {
			this.getUsers();
		}
	}

</script>

<style scoped>

</style>