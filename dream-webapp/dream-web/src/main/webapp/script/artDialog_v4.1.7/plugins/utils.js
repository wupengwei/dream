//格式化字符串,例如:
//var template = "欢迎{1}给{0}留言";  
//var msg = String.format(template, "马德成", "测试人员");  
String.format = function(src) {
	if (arguments.length == 0) return '';
	var args = Array.prototype.slice.call(arguments, 1);
	return src.replace(/\{(\d+)\}/g, function(m, i) {
		return args[i];
	});
};

/**       
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、12小时(h)、24小时(H)、分(m)、秒(s)、周(E)、季度(q) 可以用 1-2 个占位符
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * eg:
 * (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).format("yyyy-MM-dd E HH:mm:ss") ==> 2009-03-10 二 20:09:04
 * (new Date()).format("yyyy-MM-dd EE hh:mm:ss") ==> 2009-03-10 周二 08:09:04
 * (new Date()).format("yyyy-MM-dd EEE hh:mm:ss") ==> 2009-03-10 星期二 08:09:04
 * (new Date()).format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
 */
Date.prototype.format = function(fmt) {
	var o = {
		"M+": this.getMonth() + 1, //月份           
		"d+": this.getDate(), //日           
		"h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时           
		"H+": this.getHours(), //小时           
		"m+": this.getMinutes(), //分           
		"s+": this.getSeconds(), //秒           
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度           
		"S": this.getMilliseconds() //毫秒           
	};
	var week = {
		"0": "\u65e5",
		"1": "\u4e00",
		"2": "\u4e8c",
		"3": "\u4e09",
		"4": "\u56db",
		"5": "\u4e94",
		"6": "\u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f" : "\u5468") : "") + week[this.getDay() + ""]);
	}
	for (var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
};


/**
 * 基于右下角提示信息 基于artdialog
 */
artDialog.notice = function(options) {
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
		init: function(here) {
			api = this;
			aConfig = api.config;
			wrap = api.DOM.wrap;
			top = parseInt(wrap[0].style.top);
			hide = top + wrap[0].offsetHeight;

			wrap.css('top', hide + 'px')
				.animate({
					top: top + 'px'
				}, duration, function() {
					opt.init && opt.init.call(api, here);
				});
		},
		close: function(here) {
			wrap.animate({
				top: hide + 'px'
			}, duration, function() {
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

(function (config) {
    config['opacity'] = 0;
    config['padding'] = '10px 25px';
})(art.dialog.defaults);

/**
 * 右下角提示信息调用
 * @param msg 提示信息
 * @returns
 */
function $notice(msg, close) {
	return art.dialog.notice({
		title: '提示',
		width: 200, // 必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致artDialog收缩
		content: msg,
		icon: 'success',
		close: close,
		time: 3
	});
}

/**
 * 宽展方法-等待框，不会自动关闭,需要调用close方法
 * @param content 提示内容
 */
function $waiting(content, callback) {
	return artDialog({
			//id: 'Tips',
			title: false,
			cancel: false,
			fixed: true,
			icon: "loading",
			esc: false,
			lock: true
		})
		.content('<div style="padding: 0 2em;">' + content + '</div>');
}

/**
 * 提示信息
 * @param	{String}	消息内容
 * @param	{Function}	回调函数
 */
artDialog.messageBox = function(content, icon, callback) {
	return artDialog({
		title: '提示',
		icon: icon,
		fixed: true,
		lock: true,
		content: content,
		width: 160,
		ok: true,
		close: callback || $.noop
	});
};

/**
 * 确认
 * @param	{String}	消息内容
 * @param	{Function}	确定按钮回调函数
 * @param	{Function}	取消按钮回调函数
 */
artDialog.confirm = function(content, hasCancel, yes, no) {
	return artDialog({
		title: '确认',
		fixed: true,
		lock: true,
		width:300,
		padding:'28px 25px',
		content: content,
		ok: function(here) {
			return yes && yes.call(this, here);
		},
		cancel: hasCancel?function(here) {
			return no && no.call(this, here);
		} : ''
	});
};


/**
 * 类似于window.alert
 * @param msg 消息
 * @param callback 回调函数
 */
function $alert(msg, callback) {
	return art.dialog.alert(msg, false, callback);
}


/**
 * alert提示
 * @param	{String}	消息内容
 * @param	{Function}	确定按钮回调函数
 * @param	{Function}	取消按钮回调函数
 */
artDialog.alert = function(content, hasCancel, yes, no) {
	return artDialog({
		title: '提示',
		fixed: true,
		lock: true,
		width:300,
		padding:'28px 25px',
		content: content,
		ok: function(here) {
			return yes && yes.call(this, here);
		},
		cancel: hasCancel?function(here) {
			return no && no.call(this, here);
		} : ''
	});
};

/**
 * 类似于window.confirm
 * @param msg 消息
 * @param callback 回调函数
 */
function $confirm(msg, yes, no) {
	art.dialog.confirm(msg, true, yes, no);
}

/**
 * 成功提示信息
 * @param msg 消息
 * @param callback 回调函数
 */
function $success(msg, callback) {
	return $tips(msg, 'succeed', 1.5);
}

/**
 * 错误提示信息
 * @param msg 消息
 * @param callback 回调函数
 */
function $error(msg, callback) {
	return $tips(msg, 'error', 1.5);
}

/**
 * 短暂提示
 * @param	{String}	提示内容
 * @param	{Number}	显示时间 (默认1.5秒)
 */
artDialog.tips = function(options) {
	var config = $.extend({
		title: false,
		cancel: false,
		fixed: true,
		lock: true
	}, options);

	var tip = artDialog(config);

	if (config.time) tip.time(config.time);
	return tip
};

/**
  短暂提示
  */
function $tips(msg, icon, time) {
	return art.dialog.tips({
		content: msg,
		icon: icon,
		time: time
	});
}

/**
 * 打开对话框
 * @param options
 * @returns
 */
function openDialog(options) {
	var defaults = {
		title: '弹出窗口',
		fixed: true,
		max: false,
		min: false,
		lock: true,
		resize: true,
		cache: false,
		esc: false,
		okVal: '保存',
		dblclick: false, //双击不关闭对话框
		data: {}
	};

	var options = $.extend(defaults, options);

	if (options.url) { //使用扩展 基于iframe
		return art.dialog.open(options.url, options);
	}

	var response = '';
	if (options.ajaxUrl) { //实际还是基于content   data为ajax请求的参数	
		$.ajax({
			url: options.ajaxUrl,
			async: false,
			data: options.data,
			success: function(res) {
				options.content = res;
				response = res;
			},
			error: function() {
				$alert("请求url异常!");
			}
		});
	}

	dialog = art.dialog(options);
	if (options.ajaxSuccess) {
		options.ajaxSuccess.call(this, response);
	}
	return dialog;
}

/**
 * 打开详情对话框
 * 一般只需要传入content,或者url即可
 * @param {Object} options
 */
function detailDialog(options){
	var defaults = {
		title: '',
		id:'mdc-detail-dialog',
		padding:0,
		width:373,
		height:562,
		init : function(){
			var title = $(this.DOM.title[0]);
			title.addClass('no-title-bg');
			title.parent().css({position:'absolute', 'z-index':9, left:0, right:0,top:0, height:'40px'});
		},
		close:function(){
			var title = $(this.DOM.title[0]);
			title.removeClass('no-title-bg');
			title.parent().css({position:'inherit', height:'auto'});
		}
	};

	var options = $.extend(defaults, options);
	return openDialog(options);
}