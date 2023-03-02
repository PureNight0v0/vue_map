<template>
<div>
  <el-row>
    <el-input v-model="input" style="width:200px;" placeholder="请输入内容"></el-input>
    <el-button type="primary" icon="el-icon-search" @click="searchRoute">搜索</el-button>
    <el-button type="danger" icon="el-icon-plus">新增</el-button>
  </el-row>
   <el-table
    :data="routeList"
    highlight-current-row
    @current-change="handleCurrentChange"
    stripe
    style="width: 100%">
    <el-table-column
      prop="routeId"
      label="路径ID"
      width="80">
    </el-table-column>
    <el-table-column
      prop="routeTitle"
      label="路径名称"
      width="120">
    </el-table-column>
    <el-table-column
      prop="routePath"
      label="路径"
      width="260">
    </el-table-column>
    <el-table-column
      prop="routeComment"
      label="备注"
      width="200">
    </el-table-column>
    <el-table-column
      prop="routePic"
      label="预览图"
      width="150">
      <template slot-scope="scope">
         <img src="../../assets/images/beijing.jpg" alt="" style="width:80px;height:80px" v-if="scope.row.routePic==null"/>
                  <img :src="require('../../assets/images/' +scope.row.routePic)" width="80" height="80" class="head_pic" v-else/>
      </template>
    </el-table-column>
    <el-table-column
      prop="createTime"
      label="创建时间"
      width="120">
    </el-table-column>
    <el-table-column
      label="操作"
      width="120">
      <template>
        <el-dialog >111</el-dialog>
        <el-button type="text" icon="el-icon-edit" @click="edit"></el-button>
        <el-popover placement="top" width="80" v-model="visible">
          <p>确定删除吗？</p>
          <div style="text-align: right; margin: 0">
          <el-button size="mini" type="text" @click="visible = false">取消</el-button>
          <el-button type="primary" size="mini" @click="deleteFoot">确定</el-button>
         </div>
        <el-button type="text" icon="el-icon-delete" slot="reference"></el-button>
        </el-popover>
        <el-button type="text" icon="el-icon-search"></el-button>
      </template>
    </el-table-column>
  </el-table>
    <el-dialog title="修改路线信息" :visible.sync="FormVisible">
     <el-form :model="currentRow">
        <el-form-item label="路线ID" :label-width="formLabelWidth">
          <el-input v-model="currentRow.routeId"  disabled></el-input>
        </el-form-item>
        <el-form-item label="路线名称" :label-width="formLabelWidth">
          <el-input v-model="currentRow.routeTitle" ></el-input>
        </el-form-item>
        <el-form-item label="路径" :label-width="formLabelWidth">
          <el-input v-model="currentRow.routePath"  disabled></el-input>
        </el-form-item>
          <el-form-item label="备注" :label-width="formLabelWidth">
        <el-input v-model="currentRow.routeComment"></el-input>
        </el-form-item>
        <el-form-item label="路线图片" :label-width="formLabelWidth">
          <el-upload action="#" list-type="picture-card" ref="upload" :auto-upload="false">
            <i slot="default" class="el-icon-plus"></i>
            <div slot="file" slot-scope="{file}">
            <img class="el-upload-list__item-thumbnail" :src="file.url" alt="">
            <span class="el-upload-list__item-actions">
            <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
              <i class="el-icon-zoom-in"></i>
            </span>
            <span
              v-if="!disabled"
              class="el-upload-list__item-delete"
              @click="handleDownload(file)"><i class="el-icon-download"></i>
            </span>
           <span
            v-if="!disabled"
            class="el-upload-list__item-delete"
            @click="handleRemove(file)"><i class="el-icon-delete"></i>
           </span>
          </span>
          </div>
        </el-upload>
        </el-form-item>
        <el-form-item label="路线时间" :label-width="formLabelWidth">
          <el-date-picker v-model="date" type="date" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="FormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateRoute">确 定</el-button>
        </div>
        </el-dialog>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
</div>
</template>

<script>
export default {
  data () {
    return {
      routeList: [
        {
          routeId: '1',
          routeTitle: '北京一日游',
          routePath: '北京 > 景山 > 故宫 > 动物园 > 天坛',
          routeComment: '注意做好防晒，提早出门',
          routePic: '',
          createTime: '2022-05-02'
        }],
      input: '',
      currentRow: {
        footId: '1',
        footTitle: '北京一日游',
        footLng: '123.34324',
        footLat: '89.72837',
        footAddress: '上海市普陀区金沙江路 1518 弄',
        footPic: '',
        createTime: '2022-05-02'
      },
      FormVisible: false, // 修改信息弹出表单
      visible: false, // 删除确认消息
      form: {
        name: '1',
        region: '北京半日',
        date1: '故宫=>天坛公园',
        date2: '提早规划',
        resource: '2022-11-25'
      },
      formLabelWidth: '120px',
      dialogImageUrl: '',
      dialogImageName: '',
      dialogVisible: false,
      disabled: false,
      date: ''
    }
  },
  methods: {
    edit () {
      this.FormVisible = true
    },
    searchRoute () {
      const that = this
      const axios = require('axios')
      axios.get('/map/searchRoute', { params: { input: this.input } })
        .then(function (res) {
          that.tableData = res.data
        })
    },
    handleCurrentChange (val) {
      this.currentRow = val
    },
    tableRowClassName ({ row, rowIndex }) {
      // 把每一行的索引放进row
      row.index = rowIndex
    },
    showRouteList () {
      const that = this
      const userStr = localStorage.getItem('vuex') || '{}'
      const userId = JSON.parse(userStr).userInfo.id
      const axios = require('axios')
      axios.get('/map/routelist', { params: { id: userId } })
        .then(function (res) {
          that.routeList = res.data
        })
    },
    updateRoute () {
      const axios = require('axios')
      this.currentRow.routePic = this.dialogImageName
      this.currentRow.createTime = this.date
      axios.put('/map/updateRoute', { data: this.currentRow })
        .then(function (res) {
          console.log(res)
          location.reload()
        })
    },
    deleteRoute () {
      const axios = require('axios')
      axios.delete('/map/deleteRoute', this.currentRow)
        .then(function (res) {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          location.reload()
        })
    },
    handleRemove (file) {
      console.log(file)
      this.$message({
        message: file.name + '移除成功~',
        type: 'success'
      })
    },
    handlePictureCardPreview (file) {
      this.dialogImageName = file.name
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    handleDownload (file) {
      console.log(file)
    }
  },
  created () {
    this.showRouteList()
  }
}
</script>

<style>
.el-row{
  margin-bottom: 10px;
}
.el-input{
  margin:5px 5px 0 5px;
}
</style>
