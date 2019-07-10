<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dialog-order-form" style="display: none;">
    <form id="orderForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">所在部门</label></td>
                <td>
                    <select id="deptSelectId" name="deptId" data-placeholder="选择部门" style="width: 200px;"></select>
                </td>
            </tr>
            <tr>
                <td><label for="orderName">名称</label></td>
                <input type="hidden" name="id" id="orderId"/>
                <td><input type="text" name="ordername" id="orderName" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="orderMail">邮箱</label></td>
                <td><input type="text" name="mail" id="orderMail" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="orderTelephone">电话</label></td>
                <td><input type="text" name="telephone" id="orderTelephone" value="" class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="orderStatus">状态</label></td>
                <td>
                    <select id="orderStatus" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                        <option value="2">删除</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="orderRemark">备注</label></td>
                <td><textarea name="remark" id="orderRemark" class="text ui-widget-content ui-corner-all" rows="3" cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>