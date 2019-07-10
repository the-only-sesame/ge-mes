$(function() {
	$(".order-add").click(function(e) {
		// ////////////////////////////////////////////////////
		// 弹出框
		$("#dialog-order-form").dialog({
			model : true,// 背景不可点击
			title : "新建订单",// 模态框标题
			open : function(event, ui) {
				$(".ui-dialog").css("width", "700px");// 增加模态框的宽高
				$(".ui-dialog-titlebar-close", $(this).parent()).hide();// 关闭默认叉叉
				optionStr = "";
				$("#orderForm")[0].reset();// 清空模态框--jquery 将指定对象封装成了dom对象
			},
			buttons : {
				"添加" : function(e) {
					// 阻止一下默认事件
					e.preventDefault();
					// 发送新增order的数据和接收添加后的回收信息
					/*
					 * updateOrder(true, function(data) { //增加成功了 //提示增加用户成功信息
					 * showMessage("新增订单", data.msg, true);
					 * $("#dialog-order-form").dialog( "close");
					 * loadOrderList();//根据参数查看 }, function(data) { //增加失败了 //
					 * alert("添加失败了"); //信息显示 showMessage("新增订单", data.msg,
					 * false); // $("#dialog-order-form").dialog("close"); });
					 */
				},
				"取消" : function() {
					$("#dialog-order-form").dialog("close");
				}
			}
		});
		// ////////////////////////////////////////////////////
	});

	// ////////////////////////////////////////////////////////////
	// 新增和修改order的通用方法-dml
	// isCreate是否是新增订单(true,false)，如果不是，执行修改
	// successCallbak function(data) failCallbak function(data)
	function updateOrder(isCreate, successCallbak, failCallbak) {
		$.ajax({
			url : isCreate ? "/order/insert.json" : "/order/update.json",
			data : isCreate ? $("#orderForm").serializeArray() : $(
					"#orderUpdateForm").serializeArray(),
			type : 'POST',
			success : function(result) {
				// 数据执行成功返回的消息
				if (result.ret) {
					loadOrderList(); // 带参数回调
					// 带参数回调
					if (successCallbak) {
						successCallbak(result);
					}
				} else {
					// 执行失败后返回的内容
					if (failCallbak) {
						failCallbak(result);
					}
				}
			}
		});
	}
	// ////////////////////////////////////////////////////////////
	// 日期显示
	$('.datepicker').datepicker({
		dateFormat : 'yy-mm-dd',
		showOtherMonths : true,
		selectOtherMonths : false
	});

});