/*
Copyright (C) 2012
Author:		周小欠
Department: 研发中心应用产品部
Company:    
*/
format = function(source, params) {
	if ( arguments.length == 1 )
		return function() {
			var args = $.makeArray(arguments);
			args.unshift(source);
			return format.apply( this, args );
		};
	if ( arguments.length > 2 && params.constructor != Array  ) {
		params = $.makeArray(arguments).slice(1);
	}
	if ( params.constructor != Array ) {
		params = [ params ];
	}
	$.each(params, function(i, n) {
		source = source.replace(new RegExp("\\{" + i + "\\}", "g"), n);
	});
	return source;
};

var regexEnum = {
	number:"^\\d*$",						//数字
	intege:"^-?[1-9]\\d*$",					//整数
	intege1:"^[1-9]\\d*$",					//正整数
	intege2:"^-[1-9]\\d*$",					//负整数
	num:"^([+-]?)\\d*\\.?\\d+$",			//数字
	num1:"^([1-9]\\d*|0)$",					//正数（正整数 + 0）
	num2:"^(-[1-9]\\d*|0)$",				//负数（负整数 + 0）
	decmal:"^([+-]?)\\d*\\.\\d+$",			//浮点数
	decmal1:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$",	//正浮点数
	decmal2:"^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$", //负浮点数
	decmal3:"^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$", //浮点数
	decmal4:"^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$", //非负浮点数（正浮点数 + 0）
	decmal5:"^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$",//非正浮点数（负浮点数 + 0）
	email:"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$", //邮件
	color:"^[a-fA-F0-9]{6}$",				//颜色
	url:"^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$",	//url
	chinese:"^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$",					//仅中文
	ascii:"^[\\x00-\\xFF]+$",				//仅ACSII字符
	zipcode:"^\\d{6}$",						//邮编
	mobile:"^(13|14|15|17|18)[0-9]{9}$",				//手机
	ip4:"^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$",	//ip地址
	mask:"^(255|0)\\.(255|0)\\.(255|0)\\.(255|0)$",//子网掩码验证方式
	ip4section:"^((25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d))(-(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d))?$",   //ip段
	notempty:"(^\\S[\\S\\s]*\\S$)|(^\\S$)",				//非空
	noBlank:"^\\S+$",							//不允许出现空格
	picture:"(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$",	//图片
	rar:"(.*)\\.(rar|zip|7zip|tgz)$",								//压缩文件
	date:"^\\d{4}(\\-|\\/|\.)\\d{1,2}\\1\\d{1,2}$",					//日期
	qq:"^[1-9]*[1-9][0-9]*$",				//QQ号码
	tel:"^(([\(]?|[\（]?)?([0\\+]\\d{2,3})?(0\\d{2,3}))?(\\-?|[\)]?|[\）]?)?(\\d{5,8})((\\d{3,}))?$",	//电话号码的函数(包括验证国内区号,国际区号,分机号)
	username:"^\\w+$",						//用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	letter:"^[A-Za-z]+$",					//字母
	letter_u:"^[A-Z]+$",					//大写字母
	letter_l:"^[a-z]+$",					//小写字母
	idcard:"^[1-9]([0-9]{14}|[0-9]{17})$",	//身份证
	mac:"[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}:[A-F\\d]{2}",//MAC地址
	ipport:"^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d):([1-9]\\d{1,4})$",//ip地址+端口号
	specialchar:"^([a-z]|[A-Z]|[0-9]|\\/|\\.|\\\\|\\:)*$",
	specialchar1:"^[^(\"|'|<|>)]|[^(\"|'|<|>)]+|[^(\"|'|<|>)]$",
	specialchar2:"^[^+/?%#&=￥~$@]+$",
	numAndcharAndchinese:"^[A-Za-z0-9\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$",
	netmask:"^(254|252|248|240|224|192|128|0)\.0\.0\.0|255\.(254|252|248|240|224|192|128|0)\.0\.0|255\.255\.(254|252|248|240|224|192|128|0)\.0|255\.255\.255\.(255|254|252|248|240|224|192|128|0)$",
	port:"(^[1-9]\\d{0,3}$)|(^[1-5]\\d{4}$)|(^6[0-4]\\d{3}$)|(^65[0-4]\\d{2}$)|(^655[0-2]\\d$)|(^6553[0-5]$)", //端口号
	numAndchar:"^[A-Za-z0-9]+$", //数字字母组合
    //urlHttp:"^(http[s]?:\/\/)[a-z0-9.]*:?[a-z0-9\/\-]*$", //以http[s]开关的url
	urlHttp:"^(http|ftp|https|rtsp|mms):\/\/[\\w\\-]+([\\w\\-_]+)+([\\w\-\\.,@?^=%&:\/~\\+#]*[\\w\\-\\@?^=%&\/~\\+#])?",
	ipAndrealm:"^((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)(\.((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)){3}$|^([a-zA-Z0-9]([a-zA-Z0-9\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$" //ip和域名
}
var messages = {
	number:"请输入数字",						//数字
	intege:"请输入正确的整数",					//整数
	intege1:"请输入大于0的整数",				//正整数
	intege2:"请输入小于0的整数",				//负整数
	num:"请输入正确的数字",						//数字
	num1:"请输入大于0的正整数",						//正数（正整数 + 0）
	num2:"请输入小于0的数",						//负数（负整数 + 0）
	decmal:"请输入带小数的数字",					//浮点数
	decmal1:"请输入大于0且带小数的数字",	//正浮点数
	decmal2:"请输入小于0且带小数的数字",	 	//负浮点数
	decmal3:"请输入带小数的数字",				//浮点数
	decmal4:"请输入大于等于0且带小数的数字", 	//非负浮点数（正浮点数 + 0）
	decmal5:"请输入小于等于0且带小数的数字",	//非正浮点数（负浮点数 + 0）
	email:"请输入正确的邮件格式，如:example@xdja.com", //邮件
	color:"颜色数据格式不正确",					//颜色
	url:"请输入正确的URL地址",					//url
	chinese:"请输入中文",						//仅中文
	ascii:"请输入ACSII字符",					//仅ACSII字符
	zipcode:"邮编格式不正确",					//邮编
	mobile:"手机号码格式不正确",					//手机
	ip4:"IP地址格式不正确",						//ip地址
	mask:"子网掩码格式不正确",                     //子网掩码验证方式
	ip4section:"IP区间格式不正确，如：192.168.0.1或192.168.0.1-192.168.0.254，中间不能有空格",                //ip段
	notempty:"不能为空且首尾不能有空格",		//非空
	noBlank:"不允许出现空格",					//不允许出现空格
	picture:"请选择正确的图片格式",				//图片
	rar:"请选择正确的压缩文件格式",				//压缩文件
	date:"日期格式不正确",						//日期
	qq:"QQ号码格式不正确",						//QQ号码
	tel:"电话号码格式不正确",						//电话号码的函数(包括验证国内区号,国际区号,分机号)
	username:"请输入数字、英文字母或下划线",	//用来用户注册。匹配由数字、26个英文字母或者下划线组成的字符串
	letter:"请输入英文字母",					//字母
	letter_u:"请输入大写英文字母",				//大写字母
	letter_l:"请输入小写英文字母",				//小写字母
	idcard:"身份证号码格式不正确",				//身份证
	equalsTo: format("请输入相同的值"),			//值相同对比
	maxLength: format("长度不能大于{0}"),		//最大长度
	minLength: format("长度不能小于{0}"),		//最小长度
	mac:"MAC地址格式不正确，请参考：00:24:21:19:BD:E4",//MAC地址
	ipport:"IP端口格式不正确，请参考：192.168.1.1:8080",
	scopeOf:format("数字不在规定范围内，请正确输入"),//验证数字范围
	specialchar:"包含特殊符号，只能输入字母、数字、/、\\、.、:等符号",
	specialchar1:"不能含有\"，'，&lt;，&gt;等特殊字符",
	specialchar2:"不能输入+,/,?,%,#,&,=,￥,@,~,$等特殊字符",
	numAndcharAndchinese:"请输入汉字、字母或数字",
	netmask:"子网掩码格式不正确",
	port:"请输入数字1-65535",
	numAndchar:"请输入数字或字母",
	urlHttp:"url格式不正确",
	ipAndrealm:"ip或域名格式不正确，请参考：192.168.1.1或www.xdja.com"
}
var yOffset = 10; // 提示框相对鼠标偏移的Y值

$(function(){
	showTip();
	$("body").on("blur", "[reg]", function(){//失去焦点，验证，先本地验证，本地验证成功后远程验证
		var localValid = false;//本地验证是否成功
		if($(this).attr("reg") != undefined) {
			localValid = validate($(this), $(this).attr("reg"));
		}
		if(localValid && $(this).attr("url") != undefined) {//本地验证通过并且定义了url，远程验证
			ajax_validate($(this), $(this).attr("url"));
		}
	});
	$(":text,textarea").each(function(){
		var thisObj = $(this);
		if(thisObj.attr("reg") == undefined) {
			$("body").on("blur", thisObj, function() {
				if($(this).attr("novali") == undefined) {//先判断是否有"没有验证属性"的标志
					//没有标志，判断是否需要添加标志
					if($(this).attr("tip") == undefined && $(this).attr("message") == undefined) {
						$(this).attr("novali","true");
						if(!validate($(this), null)) {
							$("body").on("mouseenter", this, function(){
								buildTip($(this));
							}).on("mouseleave", this, function(){
								$("#vtip").remove();
							});
						} else {
							$("body").off("mouseenter", this);
							$("body").off("mouseleave", this);
							
							$("#vtip").remove();
							$(this).removeAttr("tip");
							$(this).removeAttr("message");
						}
					} else {
						validate($(this), null);
					}
				} else {//有novali标识，也就是没有设置tip和message，验证并提示
					if(!validate($(this), null)) {
						$("body").on("mouseenter", this, function(){
							buildTip($(this));
						}).on("mouseleave", this, function(){
							$("#vtip").remove();
						});
					} else {
						$("body").off("mouseenter", this);
						$("body").off("mouseleave", this);
						
						$("#vtip").remove();
						$(this).removeAttr("tip");
						$(this).removeAttr("message");
					}
				}
			});
		}
	})
	$("form").submit(function(){//表单提交，先进行验证
		return validateForm($(this));
	});
});

/**
 * 显示提示信息
 * @return
 */
function showTip() {
	$("body").on("mouseenter", "[reg],[tip],[message]", function(e) {
		if($(this).attr('tip') != undefined && $.trim($(this).attr('tip')) != "") {//鼠标移到目标上显示提示信息
			buildTip($(this));
		}
	}).on("mouseleave", "[reg],[tip],[message]", function() {
		if($(this).attr('tip') != undefined){//鼠标移走不显示提示信息s
			$("#vtip").remove();
		}
	});
}

function buildTip(obj) {
	var tip = obj.attr("tip");
	if(tip.indexOf("boundId:") == 0) {
		tip = $("#" + tip.split(":")[1]).html();
	}
	$('body').append('<div id="vtip" style="display:none;z-index:20000;min-width:120px"><span id="vtipArrow" class="vtipArrowUp"></span>' + tip + '</div>');
	//计算弹出框的宽度和高度，计算坐标加上宽高是否超出窗口的宽高
	var tip_width = $('#vtip').width() + getPadding($('#vtip'), "h");
	var left = obj.offset().left;
	//设置tip的宽度
	var objWidth = obj.width() + getPadding(obj, "h");//获取目标对象的宽度
	var windowWidth = $(window).width();//窗口宽度
	var windowWidthTemp = windowWidth - 50;
	if(objWidth > windowWidthTemp) {//如果宽度超过了窗口宽度
		objWidth = windowWidthTemp;
	}
	if(tip_width > objWidth) {
		var tempWidth = objWidth > 300 ? objWidth : 300;
		tip_width = tip_width > 300 ? tempWidth : tip_width;
		$('#vtip').width(tip_width - 20);
	} else if(obj.css("text-align") == "center" || obj.attr("align") == "center"){
		left = left + (objWidth - tip_width) / 2;//如果内容居中，提示居中
	}
	//判断提示框是否超出了窗口
	var tipRightX = left + tip_width + 40;//tip右边横坐标,偏移40像素
	if(tipRightX > windowWidth) {//左移提示框
		var overstep = tipRightX - windowWidth;
		left = left - overstep;
		$("#vtipArrow").css("left", overstep + "px");
	}
	var objHeight = obj.height() + getPadding(obj, "v");
	var top = obj.offset().top  + objHeight + yOffset;//左顶点y坐标是输入域的y坐标+输入域高+输入域的padding值
	var tip_height = $('#vtip').height() + getPadding($('#vtip'), "v");//计算tip高度
	if(top + tip_height + 10 > $(window).height()) {//计算是否需要竖直方向翻
		top = top - tip_height - objHeight - 22;
		$("#vtipArrow").removeClass("vtipArrowUp");//修改箭头的样式，箭头向下
		$("#vtipArrow").addClass("vtipArrowDown");
		$("#vtipArrow").css("top", (tip_height - 1) + "px");
	}
	$('#vtip').css("top", top+"px").css("left", left+"px");
	$('#vtip').show();
}

function getPadding(obj, type) {
	if(type == "v") {
		var padd_top_str = obj.css("padding-top");//获取padding-top的字符形式:带px
		var padd_bottom_str = obj.css("padding-bottom");//获取padding-bottom的字符形式:带px
		var padding = parseInt(padd_top_str.substring(0, padd_top_str.indexOf("px"))) + parseInt(padd_bottom_str.substring(0, padd_bottom_str.indexOf("px")));
	} else if(type == "h") {
		var padd_left_str = obj.css("padding-left");
		var padd_right_str = obj.css("padding-right");
		var padding = parseInt(padd_left_str.substring(0, padd_left_str.indexOf("px"))) + parseInt(padd_right_str.substring(0, padd_right_str.indexOf("px")));
	}
	return padding;
}

/**
* 验证form表单
* @param form jquery对象$("#formId")
* @return
*/
function validateForm(form) {
var isSubmit = true
form.find("[reg]").each(function(){
	if($(this).attr("reg") != undefined) {
		isSubmit = validate($(this), $(this).attr("reg")) && isSubmit;
	}
	if(isSubmit && $(this).attr("url") != undefined) {
		isSubmit = ajax_validate($(this), $(this).attr("url")) && isSubmit;
	}
});

if(isSubmit) {
	$(":text,textarea").each(function(){
		var thisObj = $(this);
		if(thisObj.attr("reg") == undefined) {
			isSubmit = validate(thisObj, null) && isSubmit;
		}
	});
}

var extendsValidateResult = true;
if(typeof(isExtendsValidate) != "undefined" && isExtendsValidate) {
	extendsValidateResult = extendsValidate();
}
return isSubmit && extendsValidateResult;
}

/**
* 验证方法
* @param obj 目标表单域
* @return true/false
*/
function validate(obj, regKeyStr){
var objValue= obj.val();//输入框的值
/*var forbidReg = new RegExp("^[\"|'|<|>]|[\"|'|<|>]+|[\"|'|<|>]$");//特殊字符" ' < >
if(forbidReg.test(objValue)) {
	change_error_style(obj,"add");
	change_tip(obj,null,null,"add","不能含有\"，'，&lt;，&gt;等特殊字符");
	$(obj).attr("reg",($(obj).attr("reg")?($(obj).attr("reg")+",specialchar1"):"specialchar1"));
	return false;
} else {
	change_error_style(obj,"remove");
	change_tip(obj,null,null,"remove",null);
}*/


if($.trim(regKeyStr) == "") {
	return true;
}
var regKeys = regKeyStr.split(",");//输入框定义的验证类别，多个类别用逗号“,”隔开
var canEmpty = $.inArray("notempty", regKeys) == -1 ? true : false;
if(canEmpty && $.trim(objValue).length == 0) {
	change_error_style(obj,"remove");
	change_tip(obj,null,null,"remove",null);
	return true;
}
var length = regKeys.length;
var reg;
var valid = true;//验证是否通过
for(var i = 0; i < length; i++) {
	var regKey = $.trim(regKeys[i]);
	if(regKey.indexOf(":") != -1) {//包含“:”说明是带参数的方法
		var functionValue = regKey.split(":");
		valid = methods[functionValue[0]].call(this, objValue, functionValue[1]);
		if(!valid) {
			change_error_style(obj,"add");
			change_tip(obj,functionValue[0], functionValue[1],"add",null);
			break;
		}
	} else {
		var regEnum = regexEnum[regKey];
		if(regEnum != undefined) {//检查开发者编写的正则类名是否合法，不合法验证失败
			reg = new RegExp(regEnum);
			if(!reg.test(objValue)) {//验证不通过
				valid = false;
				change_error_style(obj,"add");
				change_tip(obj, regKey, null, "add", null);
				break;
			}
		} else {//开发人员制定的regKey没有定义，验证不通过
			valid = false;
			change_error_style(obj, "add");
			change_tip(obj,null,null,"add","未找到[" + regKey + "]对应的信息!");
			break;
		}
	}
}
if(valid) {
	change_error_style(obj,"remove");
	change_tip(obj,null,null,"remove",null);
}
return valid;
}

/**
* 远程验证
* @param obj 目标表单域
* @param url_str 远程验证目标地址的url
* @return true/false
*/
function ajax_validate(obj, url_str){
var objValue = obj.val();
if($.trim(objValue) == "") {
	return true;
}

var name = obj.attr("name");
var feed_back = $.ajax({url: url_str, type:"post", data:{name:objValue}, cache: false, async: false}).responseText;
feed_back = feed_back.replace(/(^\s*)|(\s*$)/g, "");
if(feed_back == 'success'){
	change_error_style(obj,"remove");
	change_tip(obj,null,null,"remove",null);
	return true;
}else{
	change_error_style(obj,"add");
	change_tip(obj,null,null,"add",feed_back);
	return false;
}
}

/**
* 变换提示信息
* @param obj 目标表单域
* @param msgKey 提示信息的key
* @param parameter 提示信息的参数
* @param action_type 动作：add/remove
* @param msg 提示信息：msgKey为null时制定
* @return
*/
function change_tip(obj,msgKey,parameter,action_type,msg){
var options = eval('(' + obj.attr("message") + ')');

if(obj.attr("tip") == undefined){//初始化判断TIP是否为空
	obj.attr("is_tip_null","yes");
}
if(action_type == "add"){
	if(obj.attr("is_tip_null") == "yes"){
	} else if(obj.attr("tip_bak") == undefined) {
		obj.attr("tip_bak",obj.attr("tip"));
	}
	if(msgKey != null) {
		if(parameter != null) {
			var opts = $.extend(messages, options);
			obj.attr("tip",messages[msgKey](parameter));
		} else {
			var messageStr = messages[msgKey];
			if(options != null && options != undefined && options[msgKey] != undefined){
				messageStr = options[msgKey];
				/**
				 * 如果添加了notempty校验，则需提示首尾不允许有空格
				 */
				/*
				if(options.notempty != undefined){
					if(options.notempty.indexOf("且首尾不允许有空格") == -1) {
						messageStr += "且首尾不允许有空格";//对notempty追加无空格提示
					}
				}*/
			}
			obj.attr("tip",messageStr);
		}
	} else {
		obj.attr("tip",msg);
	}
} else {
	if(obj.attr("is_tip_null") == "yes"){
		obj.removeAttr("tip");
		obj.removeAttr("tip_bak");
	} else {
		obj.attr("tip",obj.attr("tip_bak"));
		obj.removeAttr("tip_bak");
	}
	$("#vtip").remove();
}
}

/**
* 改变目标表单域的样式
* @param obj 目标表单域
* @param action_type 动作：add/remove
* @return
*/
function change_error_style(obj,action_type){
if(action_type == "add"){
	obj.addClass("input_validation-failed");
}else{
	obj.removeClass("input_validation-failed");
}
}
/**
* 扩展验证方法
*/
var methods = {
minLength: function(value, param) {
	return $.trim(value).length >= param;
},
maxLength: function(value, param) {
	return $.trim(value).length <= param;
},
equalsTo: function(value, param){
	return value == $("#" + param).val();
},
scopeOf: function(value, param) {
	var params=param.split("-");
	if(value>=parseInt(params[0])&&value<=parseInt(params[1]))
	{
		return true;
	}
	return false;
}
}
/**
* 回调方法
*/
$.fn.validate_callback = function(msg,action_type,options){
this.each(function(){
	if(action_type == "failed"){
		change_error_style($(this),"add");
		change_tip($(this),null,null,"add",msg);
	}else{
		change_error_style($(this),"remove");
		change_tip($(this),null,null,"remove",null);
	}
});
};