<template>
  <div class="card">
    <!--展示名片头部--头像名称-->
    <div class="card-header">
      <el-avatar :size="50" :src="user.avatar"></el-avatar>
      <p style="margin-top: 0px;">GIAMING</p>
      <el-divider></el-divider>
    </div>
    <!--卡片中部--github地址-->
    <div class="card-middle">
      <div style="max-width: 40px;margin-left: 40px">
        <el-image :fit="fit"
                  src="https://lf-cdn-tos.bytescm.com/obj/static/xitu_extension/static/github.46c47564.png"
                  style="cursor:pointer" @click="goToGithub"></el-image>
      </div>
    </div>
    <!--卡片底部--个人喜好-->
    <div class="card-footer">
      <el-collapse>
        <el-collapse-item name="1" style="padding-left: 20px;padding-right: 20px" title="最喜欢的动漫">
          <div>这是个秘密</div>
        </el-collapse-item>
        <el-collapse-item name="2" style="padding-left: 20px;padding-right: 20px" title="最喜欢的女孩子">
          <div>这是个秘密</div>
        </el-collapse-item>
        <el-collapse-item name="3" style="padding-left: 20px;padding-right: 20px" title="最喜欢的游戏">
          <div>这是个秘密</div>
        </el-collapse-item>
        <el-collapse-item name="4" style="padding-left: 20px;padding-right: 20px" title="最喜欢的颜色">
          <div>这是个秘密</div>
        </el-collapse-item>
        <el-collapse-item name="5" style="padding-left: 20px;padding-right: 20px" title="最喜欢的歌手">
          <div>这是个秘密</div>
        </el-collapse-item>
      </el-collapse>
    </div>
  </div>
</template>

<script>
export default {
  name: "Card",
  data() {
    return {
      hasLogin: false,
      fit: 'cover',
      user: {
        avatar: "http://r7eb8ajdr.hd-bkt.clouddn.com/avatar/avatar9.webp"
      },
    }
  },
  methods: {
    //跳转到个人github
    goToGithub() {
      window.location.href = "https://github.com/Giaming"
    }
  },
  created() {
  }
}
</script>

<style scoped>
.card {
  width: 250px;
  padding: 20px 0px 0px 0px;
  background-color: white;
}
.card-header {
  text-align: center;
}
.card-footer {
  margin-top: 10px;
}
.card:hover {
  -webkit-box-shadow: #ccc 0px 10px 10px;
  -moz-box-shadow: #ccc 0px 10px 10px;
  box-shadow: #ccc 0px 10px 10px;
}
</style>