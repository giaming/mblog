(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3c0aade4"],{"3b8a":function(t,a,e){},b6c3:function(t,a,e){"use strict";e("3b8a")},c8b4:function(t,a,e){"use strict";e("d3db")},d3db:function(t,a,e){},ef4c:function(t,a,e){"use strict";e.r(a);var n=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("div",[e("div",{staticClass:"banner",style:t.cover},[e("h1",{staticClass:"banner-title"},[t._v("关于我")])]),e("v-card",{staticClass:"blog-container"},[e("div",{staticClass:"my-wrapper"},[e("v-avatar",{attrs:{size:"110"}},[e("img",{staticClass:"author-avatar",attrs:{src:t.avatar}})])],1),e("div",{ref:"about",staticClass:"about-content markdown-body",domProps:{innerHTML:t._s(t.aboutContent)}})])],1)},o=[],r=(e("ac1f"),e("5319"),e("d3b7"),e("25f0"),e("1276"),e("99af"),e("159b"),e("b311")),i=e.n(r),c={created:function(){this.getAboutContent()},destroyed:function(){this.clipboard.destroy()},data:function(){return{aboutContent:"",clipboard:null,imgList:[]}},methods:{getAboutContent:function(){var t=this,a=this;this.axios.get("/api/about").then((function(e){var n=e.data;t.markdownToHtml(n),t.$nextTick((function(){t.clipboard=new i.a(".copy-btn"),t.clipboard.on("success",(function(){t.$toast({type:"success",message:"复制成功"})}));for(var e=t.$refs.about.getElementsByTagName("img"),n=0;n<e.length;n++)t.imgList.push(e[n].src),e[n].addEventListener("click",(function(t){a.previewImg(t.target.currentSrc)}))}))}))},markdownToHtml:function(t){var a=e("d4cd"),n=e("1487"),o=new a({html:!0,linkify:!0,typographer:!0,highlight:function(t,a){var e=(new Date).getTime();window.performance&&"function"===typeof window.performance.now&&(e+=performance.now());for(var o="xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g,(function(t){var a=(e+16*Math.random())%16|0;return e=Math.floor(e/16),("x"==t?a:3&a|8).toString(16)})),r='<button class="copy-btn iconfont iconfuzhi" type="button" data-clipboard-action="copy" data-clipboard-target="#copy'.concat(o,'"></button>'),i=t.split(/\n/).length-1,c='<span aria-hidden="true" class="line-numbers-rows">',s=0;s<i;s++)c+="<span></span>";if(c+="</span>",a&&n.getLanguage(a)){var u=n.highlight(a,t,!0).value;return r+=u,i&&(r+='<b class="name">'+a+"</b>"),'<pre class="hljs"><code>'.concat(r,"</code>").concat(c,'</pre><textarea style="position: absolute;top: -9999px;left: -9999px;z-index: -9999;" id="copy').concat(o,'">').concat(t.replace(/<\/textarea>/g,"</textarea>"),"</textarea>")}}});this.aboutContent=o.render(t.data)},previewImg:function(t){this.$imagePreview({images:this.imgList,index:this.imgList.indexOf(t)})}},computed:{avatar:function(){return this.$store.state.blogInfo.websiteConfig.websiteAvatar},cover:function(){var t="";return this.$store.state.blogInfo.pageList.forEach((function(a){"about"==a.pageLabel&&(t=a.pageCover)})),"background: url("+t+") center center / cover no-repeat"}}},s=c,u=(e("b6c3"),e("c8b4"),e("2877")),d=e("6544"),l=e.n(d),b=e("8212"),p=e("b0af"),f=Object(u["a"])(s,n,o,!1,null,"587c6064",null);a["default"]=f.exports;l()(f,{VAvatar:b["a"],VCard:p["a"]})}}]);