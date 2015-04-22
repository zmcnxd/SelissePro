/**
 * 获取URL中参数值
 * @param name
 * @returns
 */
function getPara(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return decodeURIComponent(r[2]);
	return "";
}

function goCommonInfo(str){
	window.location.href='commonInfo.html?name='+encodeURIComponent(str);
}

$(function(){
	// 如果没登录，跳转到登录页面
	if(window.location.href.indexOf("admin") > -1 && !localStorage.getItem("userID")){
		window.location.href="/Selisse/admin/login.html";
		return;
	}
	$("#search").val("请输入关键字");
	$("#search").focus(function(){
		if($(this).val() == "请输入关键字"){
			$(this).val("");
		}
	});
	$("#search").blur(function(){
		if($(this).val() == ""){
			$(this).val("请输入关键字");
		}
	});
	
	// 插入在线客服
	if(location.href.indexOf("admin")<=0){
		$("<div class='customer-service'><h3>在线客服<span></span></h3><div class='content'>"+
				"<div class='qq'>"+
				//"<p><span>客服小媛</span><a data-linkid='webim-shop-sidebar' target='_blank' href='http://wpa.qq.com/msgrd?v=3&amp;uin=627217155&amp;site=qq&amp;menu=yes'>"+
	            //"<img style='position:relative;top:6px;' onerror='javascript:this.onerror = null; this.src=\"http://pub.idqqimg.com/wpa/images/counseling_style_52.png\";' border='0' src='http://wpa.qq.com/pa?p=2:627217155:51' alt='点击这里给我发消息' title='点击这里给我发消息'>"+
	            //"</a></p>"+
				"<p><span>点击咨询</span><a data-linkid='webim-shop-sidebar' target='_blank' href='http://wpa.qq.com/msgrd?v=3&amp;uin=2399918277&amp;site=qq&amp;menu=yes'>"+
				"<img style='position:relative;top:6px;' onerror='javascript:this.onerror = null; this.src=\"http://pub.idqqimg.com/wpa/images/counseling_style_52.png\";' border='0' src='http://wpa.qq.com/pa?p=2:2399918277:51' alt='点击这里给我发消息' title='点击这里给我发消息'></a></p>"+
				"</div><img class='erweima' src='/Selisse/images/erweima.jpg' />"+
				"<img src='/Selisse/images/hotline.jpg' /></div></div>").appendTo($("body"));
		
	}
});

Date.prototype.Format = function (fmt) { //author: meizz 
  var o = {
    "M+": this.getMonth() + 1, //月份 
    "d+": this.getDate(), //日 
    "h+": this.getHours(), //小时 
    "m+": this.getMinutes(), //分 
    "s+": this.getSeconds(), //秒 
    "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
    "S": this.getMilliseconds() //毫秒 
  };
  if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
  for (var k in o)
  if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
  return fmt;
};

if (!Array.prototype.forEach) {  
    Array.prototype.forEach = function(callback, thisArg) {  
        var T, k;  
        if (this == null) {  
            throw new TypeError(" this is null or not defined");  
        }  
        var O = Object(this);  
        var len = O.length >>> 0; // Hack to convert O.length to a UInt32  
        if ({}.toString.call(callback) != "[object Function]") {  
            throw new TypeError(callback + " is not a function");  
        }  
        if (thisArg) {  
            T = thisArg;  
        }  
        k = 0;  
        while (k < len) {  
            var kValue;  
            if (k in O) {  
                kValue = O[k];  
                callback.call(T, kValue, k, O);  
            }  
            k++;  
        }  
    };  
}

// 公用方法
Common = {
		isAdmin: localStorage.getItem("userType") == "m"?true:false,
		//更新余额
		updateBalance: function(type,balance,agentID,successCallBack,failCallBack){
			$.ajax({
				url: "/Selisse/updateAgentInfo",
				type: "post",
				data: "agentID=" + agentID + "&value=" + balance + "&type=" + type,
				success: function(data){
					if(data == "000000"){
						if(!Common.isAdmin){
							localStorage.setItem("userBalance",balance);
						}
						successCallBack();
					}else{
						failCallBack();
					}
				}
			});
		},
		// 删除订单
		delOrder: function(id,agentID,charges,successCall){
			if(confirm("确认删除？")){
				$.ajax({
					url: "/Selisse/delOrder",
					type: "post",
					dataType: "json",
					data: "orderID=" + id,
					success: function(json){
					},
					complete: function(json){
						if(json.responseText == '000000'){
							alert("删除成功！");
							// 更新余额
							Common.updateBalance("add",parseFloat(charges),agentID,function(){
								if(successCall){
									successCall();
								}else{
									window.location.reload();
								}
							});
						}else{
							alert("删除失败！");
						}
					}
				});
			}
		},
		// 数据库时间对象转化为字符串
		timeToString: function(time){
			var strtime = new Date(time);
			return strtime.Format("yyyy-MM-dd hh:mm:ss");
		}
}

