
 /**
  * 基于右下角提示信息 基于artdialog
  */
 artDialog.notice = function (options) {
	    var opt = options || {},
	        api, aConfig, hide, wrap, top,
	        duration = 800;
	        
	    var config = {
	      	//id: 'Notice',
	        left: '100%',
	        top: '100%',
	        fixed: true,
	        drag: false,
	        resize: false,
	        follow: null,
	        lock: false,
	        init: function(here){
	            api = this;
	            aConfig = api.config;
	            wrap = api.DOM.wrap;
	            top = parseInt(wrap[0].style.top);
	            hide = top + wrap[0].offsetHeight;
	            
	            wrap.css('top', hide + 'px')
	                .animate({top: top + 'px'}, duration, function () {
	                    opt.init && opt.init.call(api, here);
	                });
	        },
	        close: function(here){
	            wrap.animate({top: hide + 'px'}, duration, function () {
	                opt.close && opt.close.call(this, here);
	                aConfig.close = $.noop;
	                api.close();
	            });
	            
	            return false;
	        }
	    };	
	    
	    for (var i in opt) {
	        if (config[i] === undefined) config[i] = opt[i];
	    };
	    
	    return artDialog(config);
	};

/**
 * 右下角提示信息调用
 * @param msg 提示信息
 * @returns
 */
 function $notice(msg, close, time){
	return  art.dialog.notice({
		    title: "提示信息",
		    width: 200,// 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
		    content: msg,
		    icon: 'success',
		    close:close,
		    time:time ? time : 3});
 }
 
 /**
  * 宽展方法-等待框，不会自动关闭,需要调用close方法
  * @param content 提示内容
  */
 function $waiting(content, callback){
 	return artDialog({
        //id: 'Tips',
        title: false,
        cancel: false,
        fixed: true,
        icon:"loading",
        lock: true,
        padding:0
    })
    .content('<div><p>' + content + '</p></div>');
 }
 
/**
 * 警告
 * @param	{String}	消息内容
 */
artDialog.alert = function (content, callback) {
    return artDialog({
        //id: 'Alert',
        icon: 'warning',
        fixed: true,
        lock: true,
        content: content,
        ok: true,
        close: callback
    });
};

/**
 * 类似于window.alert
 * @param msg 消息
 * @param callback 回调函数
 */
 function $alert(msg, callback) {
	return art.dialog.alert(msg, callback? callback : $.noop);
}

 artDialog.confirm = function (title, content, yes, no) {
	    return artDialog({
	        //id: 'Confirm',
	        icon: 'question',
	        title: title,
	        fixed: true,
	        lock: true,
	        //opacity: .1,
	        content: '<div class="alertColor">' + content + '</div>',
	        ok: function (here) {
	            return yes.call(this, here);
	        },
	        cancel: function (here) {
	            return no && no.call(this, here);
	        }
	    });
	};
 
function $confirmWithTitle(title, msg, yes,no) {
	art.dialog.confirm(title, msg, yes,no);
}


function $confirm(msg, yes,no) {
	$confirmWithTitle("消息", msg, yes,no);
}
/**
 * 短暂提示
 * @param	{String}	提示内容
 * @param	{Number}	显示时间 (默认1.5秒)
 */
artDialog.tips = function (content, time) {
    return artDialog({
        //id: 'Tips',
        title: false,
        cancel: false,
        fixed: true,
        lock: true,
        padding:0
    })
    .content('<div class="tips-all"><img class="center-block" src="../themes/default/images/icon/check-big.png"><p class="text-center tips-words">' + content + '</p></div>')
    .time(time || 1); 
};
/**
  短暂提示
  */
function $tips(msg,time) {
	if(time) {
		art.dialog.tips(msg, time);
	} else {
		art.dialog.tips(msg);
	}
}
/**
 * 打开对话框 
 * @param options
 * @returns
 */
function openDialog(options){
	
	var defaults = {
		//id:'simple-dialog-id',
		title:"弹出窗口",
		fixed:true,
		drag: false,
		max: false,
	    min: false,
	    lock: true,
	    resize:false,
	    cache:false,
	    okVal:'保存',
	    cancelVal:'取消',
		data:{}
	    //cancel:true
	};
	
	var options = $.extend(defaults, options);
	
	if(options.url) { //使用扩展 基于iframe
		//options.content = options.url + (options.url.indexOf('?') == -1? '?' : '&') + 'include';
		return art.dialog.open(options.url, options);
		
	} else if(options.content) {
		return art.dialog(options);
	}
	
	var response = '';
	if(options.ajaxUrl) { //实际还是基于content   data为ajax请求的参数	
		
		$.ajax({
			url:options.ajaxUrl,
			async:false,
			type:"POST",
			data:options.data,
			cache:false,
			success:function(res){
				options.content = res;
				response = res;
				/**dialog = art.dialog(options);
				 if(options.ajaxSuccess){
					options.ajaxSuccess.call(this, response);
				 }*/
			},
			error:function(){
				$alert("请求url异常!");
			}
		});	
	}
	 dialog = art.dialog(options);
	 if(options.ajaxSuccess){
			options.ajaxSuccess.call(this, response);
		}
	 return dialog;
}

//日期格式化函数
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

String.prototype.endWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
     return false;
  if(this.substring(this.length-s.length)==s)
     return true;
  else
     return false;
  return true;
 }

 String.prototype.startWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
   return false;
  if(this.substr(0,s.length)==s)
     return true;
  else
     return false;
  return true;
 }
 