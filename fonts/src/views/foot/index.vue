<template>
<div>
  <el-row>
    <el-input v-model="input" style="width:200px;" placeholder="请输入内容"></el-input>
    <el-button type="primary" icon="el-icon-search" @click="searchFoot">搜索</el-button>
    <el-button type="danger" icon="el-icon-plus">新增</el-button>
  </el-row>
   <el-table
    :data="tableData"
    highlight-current-row
    @current-change="handleCurrentChange"
    stripe
    style="width: 100%">
    <el-table-column
      prop="footId"
      label="脚印ID"
      width="80">
    </el-table-column>
    <el-table-column
      prop="footTitle"
      label="脚印名称"
      width="120">
    </el-table-column>
    <el-table-column
      prop="footLng"
      label="经度"
      width="120">
    </el-table-column>
    <el-table-column
      prop="footLat"
      label="纬度"
      width="120">
    </el-table-column>
    <el-table-column
      prop="footAddress"
      label="地址"
      width="270"
      >
    </el-table-column>
    <el-table-column
      prop="footPic"
      label="预览图"
      width="100">
       <template slot-scope="scope">
         <img :src="require('../../assets/images/' +scope.row.footPic)" width="80" height="80" class="head_pic" v-if="scope.row.footPic!=null"/>
         <img src="../../assets/images/shanghai.jpg" alt="" style="width:80px;height:80px" v-else/>
      </template>
    </el-table-column>
    <el-table-column
      prop="createTime"
      label="创建时间"
      width="120">
    </el-table-column>
    <el-table-column
      label="操作"
      width="140">
        <el-button  type="text" icon="el-icon-edit" @click="edit"></el-button>
        <el-popover placement="top" width="80" v-model="visible">
          <p>确定删除吗？</p>
          <div style="text-align: right; margin: 0">
          <el-button size="mini" type="text" @click="visible = false">取消</el-button>
          <el-button type="primary" size="mini" @click="deleteFoot">确定</el-button>
         </div>
        <el-button type="text" icon="el-icon-delete" slot="reference"></el-button>
        </el-popover>
        <el-button type="text" icon="el-icon-search"></el-button>
    </el-table-column>
  </el-table>

  <el-dialog title="修改脚印信息" :visible.sync="FormVisible">
     <el-form :model="currentRow">
        <el-form-item label="脚印ID" :label-width="formLabelWidth">
          <el-input v-model="currentRow.footId"  disabled></el-input>
        </el-form-item>
        <el-form-item label="脚印名称" :label-width="formLabelWidth">
          <el-input v-model="currentRow.footTitle" ></el-input>
        </el-form-item>
        <el-form-item label="脚印经度" :label-width="formLabelWidth">
          <el-input v-model="currentRow.footLng"  disabled></el-input>
        </el-form-item>
          <el-form-item label="脚印纬度" :label-width="formLabelWidth">
        <el-input v-model="currentRow.footLat" disabled></el-input>
        </el-form-item>
        <el-form-item label="脚印地址" :label-width="formLabelWidth">
          <el-input v-model="currentRow.footAddress" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="脚印图片" :label-width="formLabelWidth">
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
        <el-form-item label="脚印时间" :label-width="formLabelWidth">
          <el-date-picker v-model="date" type="date" placeholder="选择日期时间"></el-date-picker>
        </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="FormVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateFoot">确 定</el-button>
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
      tableData: [{
        footId: '1',
        footTitle: '北京一日游',
        footLng: '123.34324',
        footLat: '89.72837',
        footAddress: '上海市普陀区金沙江路 1518 弄',
        footPic: 'sea.jpg',
        createTime: '2022-05-02'
      }],
      // currentRow: null,
      input: '',
      FormVisible: false, // 修改信息弹出表单
      visible: false, // 删除确认消息
      currentRow: {
        footId: '1',
        footTitle: '北京一日游',
        footLng: '123.34324',
        footLat: '89.72837',
        footAddress: '上海市普陀区金沙江路 1518 弄',
        createTime: '2022-05-02'
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
    handleCurrentChange (val) {
      this.currentRow = val
    },
    tableRowClassName ({ row, rowIndex }) {
      // 把每一行的索引放进row
      row.index = rowIndex
    },
    searchFoot () {
      const that = this
      const axios = require('axios')
      axios.get('/map/searchFoot', { params: { input: this.input } })
        .then(function (res) {
          that.tableData = res.data
        })
    },
    showFootList () {
      const that = this
      const userStr = localStorage.getItem('vuex') || '{}'
      const userId = JSON.parse(userStr).userInfo.id
      const axios = require('axios')
      axios.get('/map/footlist', { params: { id: userId } })
        .then(function (res) {
          that.tableData = res.data
        })
    },
    deleteFoot () {
      const axios = require('axios')
      console.log(this.currentRow)
      axios.delete('/map/deleteFoot', { data: this.currentRow })
        .then(function (res) {
          console.log(res)
          location.reload()
        })
    },
    updateFoot () {
      const axios = require('axios')
      this.currentRow.footPic = this.dialogImageName
      this.currentRow.createTime = this.date
      console.log(this.currentRow.footPic)
      axios.put('/map/updateFoot', this.currentRow)
        .then(function (res) {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          location.reload()
        })
    },
    // footId () {},
    edit () {
      this.FormVisible = true
    },
    handleRemove (file) {
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
    this.showFootList()
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
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 8px;
    color: #8c939d;
    width: 80px;
    height: 80px;
    line-height: 80px;
    text-align: center;
  }
  .avatar {
    width: 80px;
    height: 80px;
    display: block;
  }
</style>
