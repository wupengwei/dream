/**
 * @author 吕靖
 * @date 2014-07-30
 * 扩展ztree基于权限树组件实现下拉树菜单
 */
(function($){
	
	var _setting = {
		comboxObj : null,
		combox : {
			search : false,
			filter : null,
			target : null,
			trigger : null,
			text : "",
			value : "",
			width: 150
		},
		callback: {
			afterSelectNode : null,
			afterClear:null,
			afterDropdown:null,
			afterDropup:null
		}
	},
	_data = {
		set : function(setting, text, value) {
			$(setting.combox.target).val(text);
			$(setting.combox.target).next(":hidden").val(value);
		},
		clear : function(setting) {
			$(setting.combox.target).val("");
			$(setting.combox.target).next(":hidden").val("");
		}
	},
	_event = {
		bindTree: function(setting) {
			var eventParam = {
				treeId: setting.treeId
			},
			treeObj = setting.treeObj;
			if(setting.combox) {
				targetObj = $(setting.combox.target);
				
			}
			
			orgin_event.bindTree.apply(this, [setting]);
			
			if(targetObj) {
				targetObj.bind("focus.combox", $.extend({}, eventParam, {target:"combox"}), event.proxy);
				$("body").on("mousedown.body"+setting.treeId, $.extend({}, eventParam, {target:"combox"}), event.proxy);
				treeObj.unbind("click").bind("click.combox", $.extend({}, eventParam, {target:"combox"}), event.proxy);
			}
			if(setting.combox && setting.combox.trigger) {
				$(setting.combox.trigger).bind("trigger.combox", 
						$.extend({}, eventParam, {target:"combox"}), event.proxy);
				$(setting.combox.trigger).bind("click.combox", function() {
					$(setting.combox.trigger).trigger("trigger.combox");
				});
				
			}
			
		},
		unbindTree: function(setting) {
			var treeObj = setting.treeObj,
			comboxObj = $(setting.combox.target);
			
			orgin_event.unbindTree.apply(this, [setting]);
			
			if(comboxObj) {
				comboxObj.unbind("focus.combox");
				
				$("body").off("mousedown.body"+setting.treeId);
			}
			
			treeObj.unbind(consts.event.CLICK);
		},
		proxy: function(e) {
			var setting = data.getSetting(e.data.treeId);
			if (!tools.uCanDo(setting, e)) return true;
			var results = event.doProxy(e),
			r = true, x = false;
			for (var i=0, l=results.length; i<l; i++) {
				var proxyResult = results[i];
				if (proxyResult.nodeEventCallback) {
					x = true;
					r = proxyResult.nodeEventCallback.apply(proxyResult, [e, proxyResult.node, proxyResult.comboxTarget]) && r;
				}
				if (proxyResult.treeEventCallback) {
					x = true;
					r = proxyResult.treeEventCallback.apply(proxyResult, [e, proxyResult.node]) && r;
				}
				if(proxyResult.comboxEventCallback) {
					x = true;
					r = proxyResult.comboxEventCallback.apply(proxyResult, [e, proxyResult.comboxTarget]) && r;
				}
			}
			return r;
		
		}
	},
	
	_eventProxy = function(event) {
		var target = event.target,
		setting = data.getSetting(event.data.treeId),
		nodeEventType = "", treeEventType = "",comboxEventType = "", tId = "",
		nodeEventCallback = null, treeEventCallback = null, comboxEventCallback = null,
		node = null, comboxTarget = _view.target;
		
		if (tools.eqs(event.type, "focus") && event.data.target && tools.eqs(event.data.target, "combox")) {
			comboxEventType = "focus";
		} else if(tools.eqs(event.type, "mousedown") && event.data.target && tools.eqs(event.data.target, "combox")) {
			
			comboxEventType = "mousedown";
		} else if(tools.eqs(event.type, "click") && event.data.target && tools.eqs(event.data.target, "combox")) {
			
			tmp = tools.getMDom(setting, target, [{tagName:"a", attrName:"treeNode"+consts.id.A}]);
			if (tmp) {
				tId = tools.getNodeMainDom(tmp).id;
				nodeEventType = "clickNode";
			}
		} else if(tools.eqs(event.type, "trigger") && event.data.target && tools.eqs(event.data.target, "combox")) {
			comboxEventType = "trigger";
		}
		
		if (tId.length>0) {
			node = data.getNodeCache(setting, tId);
			switch (nodeEventType) {
				case "clickNode" :
					nodeEventCallback = _handler.onClickNode;
					break;
			}
		}
		
		switch(comboxEventType) {
			case "focus" : 
				comboxEventCallback = _handler.onZTreeSlideDownCombox;
				break;
			case "mousedown" :
				comboxEventCallback = _handler.onBodyDown;
				break;
			case "trigger" :
				comboxEventCallback = _handler.onZTreeSlideDownCombox;
				break;
		}
		
		var proxyResult = {
				stop: false,
				node: node,
				comboxTarget: comboxTarget,
				nodeEventType: nodeEventType,
				nodeEventCallback: nodeEventCallback,
				treeEventType: treeEventType,
				treeEventCallback: treeEventCallback,
				comboxEventType: comboxEventType,
				comboxEventCallback : comboxEventCallback
			};
		
		return proxyResult
	},
	
	_handler = {
		onZTreeSlideDownCombox : function(event, target) {
			var setting = data.getSetting(event.data.treeId);
			var target = setting.combox.target;
			var combox = setting.comboxObj;
			combox.css({"left":$(target).offset().left,
			 	 "top":$(target).offset().top + $(target).outerHeight()});
			
			view.showCombox(setting);
			
			$(target).trigger("blur");
			
			if(setting.callback.afterDropdown) {
				tools.apply(setting.callback.afterDropdown);
			}
			return true;
		},
		onBodyDown : function(event, target) {
			var setting = data.getSetting(event.data.treeId);
			
			if($(event.target).parents("[ztreeType='combox']").length <= 0) {
				
				view.hideCombox(setting);
				if(setting.callback.afterDropup) {
					tools.apply(setting.callback.afterDropup);
				}
			}
			
			return true;
		},
		onClickNode: function (event, node, target) {
			if(consts.checkStatus && node.status == consts.checkStatus.EXCEPT) {
				return false;
			}
			var setting = data.getSetting(event.data.treeId);
			
			_data.set(setting, node.name, node.id);
			_view.hideCombox(setting);
			$(target).trigger("blur");
			
			if(setting.callback.afterDropup) {
				tools.apply(setting.callback.afterDropup);
			}
			
			if(setting.callback.afterSelectNode) {
				return tools.apply(setting.callback.afterSelectNode, [event, event.data.treeId, node]);
			}
			
			return true;
		}
	},
	
	_view = {
		buildComboxHtml : function(setting, tree) {
			var target = setting.combox.target;
			var treeObj = setting.treeObj;
			var toolbarHtml = $("<div class='toolbar'><div class='inner pull-right'><a href='javascript:void(0)'>关闭</a></div></div>");
			var treeHtml = $("<div class='content'></div>").append($(tree));
			var combox = $("<div style='display:none;z-index:9999' class='ztree_combox' ztreeType='combox'></div>")
						 .append(treeHtml)
					 	 //.append(toolbarHtml)
					 	 .css({"position":"absolute",
					 		 "background":"#ffffff",
					 		 "border":"1px solid #CCCCCC",
					 		 "left":240+"px",
					 	 "top":0,
					 	 "width":200 + "px"});
			//this.setCombox(combox);
			
			$(".toolbar a", combox).bind("click", function(){
				view.hideCombox(setting);
				if(setting.callback.afterClear)
					tools.apply(setting.callback.afterClear, [setting.treeId])
			});
			
			
			
			
			if(setting.combox.search) {
				var searchHtml = $("<div class='select2-search'><input id='search' class='select2-search__field' type='text'></div>");
				combox.prepend(searchHtml);
				
				$("#search", searchHtml).bind("keypress", function(e) {
					var _this = $(this);
					if (e.keyCode == 13) {
						if($.trim(_this.val()) == "") {
							view.clearLight();
							return;
						}
						
						var filterNodes = data.getNodesByFilter(setting, data.getNodes(setting), function(node){
							$("#" + node.tId + "_span").css("color","#333")
							
							if(setting.combox.filter) {
								return node.name.indexOf(_this.val()) != -1 && setting.combox.filter;
							} else {
								return node.name.indexOf(_this.val()) != -1;
							}
						});
						//为所有的搜索到的标红
						
						for (i=0;i<filterNodes.length;i++) {
							$("#" + filterNodes[i].tId + "_span").css("color","#c20100")
							view.expandParentNodes(setting, filterNodes[i]);
							view.showLightByNodes(setting, filterNodes);
						}
						
						_this.focus();
					}
				});
			}
			
			
			$(window).resize(function() {
				combox.css({"left":$(target).offset().left,
				 	 "top":$(target).offset().top + $(target).outerHeight()});
			});
			return combox;
		},
		buildTextHtml : function(target, setting) {
			target = $(target);
			target.val(setting.combox.text);
			
			var $hidden = $("<input type='hidden'>");
			$hidden.val(setting.combox.value);
			target.after($hidden);
			
			$hidden.attr("name", target.attr("name"));
			target.removeAttr("name");
			
			//this.setTarget($(target));
			
		},
		showCombox : function(setting) {
			//_view.combox.show();
			setting.comboxObj.show();
		},
		hideCombox : function(setting) {
			if(setting && setting.comboxObj) setting.comboxObj.hide();
		},
		slideDown : function(setting) {
			setting.comboxObj.slideDown("fast");
		},
		destroy : function(setting) {
			tools.apply(orgin_view.destroy, [setting]);
			setting.comboxObj.remove();
		},
		makeDOMNodeNameBefore: function(html, setting, node) {
			var title = data.getNodeTitle(setting, node),
			url = view.makeNodeUrl(setting, node),
			fontcss = view.makeNodeFontCss(setting, node),
			fontStyle = [];
			for (var f in fontcss) {
				fontStyle.push(f, ":", fontcss[f], ";");
			}
			html.push("<a id='", node.tId, consts.id.A, "' class='", consts.className.LEVEL, node.level, " " ,(node.disabled ? "disabled" : ""),"' treeNode", consts.id.A," onclick=\"", (node.click || ''),
				"\" ", ((url != null && url.length > 0) ? "href='" + url + "'" : ""), " target='",view.makeNodeTarget(node),"' style='", fontStyle.join(''),
				"'");
			if (tools.apply(setting.view.showTitle, [setting.treeId, node], setting.view.showTitle) && title) {html.push("title='", title.replace(/'/g,"&#39;").replace(/</g,'&lt;').replace(/>/g,'&gt;'),"'");}
			html.push(">");
		},
		showLightByNode: function(setting, node) {
			var spanObj = $$(node, consts.id.SPAN, setting);
			$("span.light", ".ztree").removeClass("light");
			spanObj.addClass("light");
		},
		showLightByNodes: function(setting, nodes) {
			$("span.light", ".ztree").removeClass("light");
			
			for(var i in nodes) {
				var spanObj = $$(nodes[i], consts.id.SPAN, setting);
				spanObj.addClass("light");
			}
		},
		clearLight: function() {
			$("span.light", ".ztree").removeClass("light");
		},
		expandParentNodes: function(setting, node) {
			if(!node) return;
			view.expandCollapseParentNode(setting, node.getParentNode(), true, false);
			
			$$(node, setting).focus().blur();
		}
	};
	
	$.fn.zTree.combox = function(tree, zSetting, zNodes) {
		var ztreeTool = $.fn.zTree.init.apply(this,[tree, zSetting, zNodes])
		
		var setting = ztreeTool.setting;
		//setting.treeId = tree.attr("id");
		//setting.treeObj = tree;
		
		var $combox = _view.buildComboxHtml(setting, tree);
		setting.comboxObj = tree.parents("[ztreetype='combox']");
		
		$combox.appendTo(tree.offsetParent());
		
		_view.buildTextHtml(setting.combox.target, setting);
		
		$.extend(true, ztreeTool, {
				clear : function() {
					_data.clear(setting);
				}
			}
		);
		
		return ztreeTool;
	};
	
	var _z = {
			view: _view,
			data: _data,
			event: _event
			
		};
		
		var orgin_zt = $.extend(true, orgin_zt, $.fn.zTree._z),
		orgin_event = orgin_zt.event;
		orgin_view = orgin_zt.view;
	
		$.extend(true, $.fn.zTree._z, _z);
		var zt = $.fn.zTree,
		view = zt._z.view,
		consts = zt.consts,
		data = zt._z.data,
		event = zt._z.event,
		tools = zt._z.tools,
		$$ = tools.$;
		
		data.exSetting(_setting);
		data.addInitProxy(_eventProxy);
})(jQuery);
