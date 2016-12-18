	
/**
 * dataTables的包装处理
 * @author 马德成
 * @param options
 * @returns
 */
function dataTablesWrap(options){
	
	//参数
	var defaults={
		render:"#ajaxListData",	//要将dataTable添加到该元素下,jquery获取元素方式
		basePath:"",//基本的地址一般写${base}
		url:"ajaxListData.do",//请求的默认url
		displayLength:10, //默认每页显示条数
		data:{}, // 请求参数
		seq:{show:true, index:0 }, //显示序号:show:是否显示; index:显示所在的列索引,从0开始
		info: true, //是否显示页码,条数,等详细信息
		lengthChange:false,
		order:[],//默认排序字段
		sort:true, //默认部排序
		paging:true,
		columns:[], //数据表的列
		displayStart:0
	};
	
	var options = $.extend(defaults, options);
	var rowNum = {fn:$.noop};
	var argsData = options.columns; 
	
	if(options.seq && options.seq.show) {
		rowNum.fn = function( nRow, aData, iDisplayIndex ) {
			var tableSetings=$(options.render).dataTable().fnSettings();  
	     	var page_start=tableSetings._iDisplayStart;//当前页开始

	     	$('td:eq(' + options.seq.index + ')', nRow).html( iDisplayIndex+1+page_start ); 
	     };
	     
	     argsData.splice(options.seq.index, 0, {"sortable": false, "render":function (data, type, full, meta){return 1;}});
	}
	
	return $(options.render).dataTable({
		fnDrawCallback:function(table){
			//当翻页的时候
	    	if(options.onChangePage) {
	    		//把数据传进给回调函数
				options.onChangePage(table.json.data);
			}
	    	
	    	if (table.json.data && table.json.data.length) {
				$(options.render + "_paginate").show();
				$(options.render + "_info").show();
			} else {
				$(options.render + "_paginate").hide();
				$(options.render + "_info").hide();
			}
	    },
		order:options.order,
		processing: true,
		paging:options.paging,
	    serverSide: true,
	    ajax:{
	 	   type:"POST",
	 	   url:options.url,
	 	   data:options.data
	    },
	    info: options.info,
	    bSort:options.sort,
	    searching: false,
	    lengthChange: false,
	    displayLength:options.displayLength,
	    displayStart:options.displayStart,
	    language: {
	         url: options.basePath + "/script/dataTables/Chinese.json"
	     },
	    fnRowCallback: rowNum.fn,
	    columns:argsData,
	    pagingType: "full_numbers"
	});
}