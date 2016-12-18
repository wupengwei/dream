function selectPlugin(options){
	var defaults = {
		container:'.select-container',
		renderL:'.table-left',
		renderR:'.table-right',
		tableLOptions:{language: {
            lengthMenu: "显示 _MENU_ 条 每页",
            zeroRecords: "暂无数据",
            info: "显示 第_PAGE_页 共_PAGES_页",
            infoEmpty: "暂无数据",
			sEmptyTable:"暂无数据",
			paginate:{previous:'<',next:'>'}
        }},
		tableROptions:{paging: false,info:false, filter:false, language: {
            lengthMenu: "显示 _MENU_ 条 每页",
            zeroRecords: "暂无数据",
            info: "显示 第_PAGE_页 共_PAGES_页",
            infoEmpty: "暂无数据",
			sEmptyTable:"暂无数据"
        }
		},
		lClick:null,
		rClick:null
	};
	var btn = '<div class="move">\
	<div class="mr"><input class="move-right" type="button" name="" /></div>\
	<div class="ml"><input class="move-left" type="button" name="" /></div>\
	</div>';
	
	options = $.extend(defaults, options||{});
	$(options.renderL, options.container).parent().after(btn);

	var $tablel = $(options.renderL, options.container).DataTable(options.tableLOptions);
	var $tabler = $(options.renderR, options.container).DataTable(options.tableROptions);
	
	$(options.renderL + ' tbody,' + options.renderR + ' tbody', options.container).on( 'click', 'tr', function(){
		$(this).toggleClass('selected');
    } );

	$('.move-right', options.container).click( function () {
		var selectedRows = $tablel.rows('.selected');
		var data = selectedRows.data();
		if (options.rClick && !options.rClick(data)){
			return;
		}

		selectedRows.remove().draw( true );
		$.each(data, function(i,n){
			$tabler.row.add(n).draw(true);
		});
    } );

	$('.move-left', options.container).click(function(){
		var selectedLeft = $tabler.rows('.selected');
		var dataLeft = selectedLeft.data();
		if (options.lClick && !options.lClick(dataLeft)){
			return;
		}

		selectedLeft.remove().draw(true);
		$.each(dataLeft, function(i,n){
			$tablel.row.add(n).draw(true);
		});
	});
	
	return {
		tableL:$tablel,
		tableR:$tabler
	};
}