<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>订单管理</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>
<div class="page-header">
    <h1>
        订单管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护订单
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-12">
        <div class="col-xs-12">
            <div class="table-header">
                订单列表&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 order-add"></i>
                </a>
            </div>
            <div>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="dataTables_length" id="dynamic-table_length"><label>
                                展示
                                <select id="pageSize" name="dynamic-table_length" aria-controls="dynamic-table" class="form-control input-sm">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select> 条记录 </label>
                            </div>
                        </div>
                    </div>
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer" role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
									<input type="hidden" id="id" name="id" class="id" />
									<th tabindex="0" class="batchStart-th" aria-controls="dynamic-table" rowspan="1"
										colspan="1">批量选择</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">产品自编号</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">产品名称</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">图号</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">材料名称</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">材料来源</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">来料预期</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">合同交期</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">状态</th>
									<th tabindex="0" aria-controls="dynamic-table" rowspan="1"
										colspan="1">备注</th>
									<th class="sorting_disabled" rowspan="1" colspan="1"
										aria-label="">操作</th>
								</tr>
                        </thead>
                        <tbody id="orderList">
                        	
                        </tbody>
                    </table>
                    <div class="row" id="orderPage">
                    	
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="orderBatchForm.jsp" %>
<script type="text/javascript" src="orderBatch.js"></script>
</body>
</html>