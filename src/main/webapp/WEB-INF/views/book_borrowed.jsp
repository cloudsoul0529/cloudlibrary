<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>当前借阅</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
</head>

<body class="hold-transition skin-red sidebar-mini">
<!-- .box-body -->
<div class="box-header with-border">
    <h3 class="box-title">当前借阅</h3>
</div>
<div class="box-body">

    <%-- 只有管理员能看到 Tab 切换标签 --%>
    <c:if test="${USER_SESSION.role =='ADMIN'}">
        <div class="nav-tabs-custom">
            <ul class="nav nav-tabs">
                <li class="${empty showType || showType == 'all' ? 'active' : ''}">
                    <a href="#" onclick="switchTab('all')">全部借阅</a>
                </li>
                    <%-- 待归还确认 Tab --%>
                <li class="${showType == 'confirm' ? 'active' : ''}">
                    <a href="#" onclick="switchTab('confirm')">
                        待归还确认
                            <%-- 【修改】：只有数量大于0才显示徽章 --%>
                        <c:if test="${confirmCount > 0}">
                            <span class="badge bg-red">${confirmCount}</span>
                        </c:if>
                    </a>
                </li>
                <li class="${showType == 'my' ? 'active' : ''}">
                    <a href="#" onclick="switchTab('my')">我的借阅</a>
                </li>
            </ul>
        </div>
    </c:if>

    <!--工具栏 数据搜索 -->
    <div class="box-tools pull-right">
        <div class="has-feedback">
            <form id="searchForm" action="${pageContext.request.contextPath}/book/searchBorrowed" method="post">
                <input type="hidden" name="showType" id="showTypeInput" value="${showType}">

                图书名称：<input name="name" value="${search.name}">&nbsp&nbsp&nbsp&nbsp
                图书作者：<input name="author" value="${search.author}">&nbsp&nbsp&nbsp&nbsp

                <%-- 借阅人搜索框：只有在“全部借阅”或“待确认”模式下显示，看自己时不需要搜人名 --%>
                <c:if test="${USER_SESSION.role =='ADMIN' && (empty showType || showType != 'my')}">
                    借阅人：<input name="borrower" value="${search.borrower}">&nbsp&nbsp&nbsp&nbsp
                </c:if>

                <%-- 只有在“待归还确认” Tab 下才显示批量按钮，注意 type="button" 防止提交表单 --%>
                <c:if test="${showType == 'confirm'}">
                    <button type="button" class="btn btn-success" onclick="batchConfirm()">批量确认归还</button>&nbsp&nbsp&nbsp&nbsp
                </c:if>

                <input class="btn btn-default" type="submit" value="查询">
            </form>
        </div>
    </div>
    <!--工具栏 数据搜索 /-->

    <!--数据列表-->
    <div class="table-box">
        <!-- 数据表格 -->
        <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
            <thead>
            <tr>
                <%-- 【新增】如果是待确认模式，显示全选框 --%>
                <c:if test="${showType == 'confirm'}">
                    <th class="text-center">
                        <input type="checkbox" id="selall" onclick="allSelect()">
                    </th>
                </c:if>

                <th class="sorting_asc">图书名称</th>
                <th class="sorting">图书作者</th>
                <th class="sorting">出版社</th>
                <th class="sorting">标准ISBN</th>
                <th class="sorting">书籍状态</th>
                <th class="sorting">借阅人</th>
                <th class="sorting">借阅时间</th>
                <th class="sorting">应归还时间</th>
                <th class="text-center">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageResult.rows}" var="book">
                <tr>
                        <%-- 【新增】如果是待确认模式，显示每一行的复选框 --%>
                    <c:if test="${showType == 'confirm'}">
                        <td class="text-center">
                            <input type="checkbox" name="ids" value="${book.id}">
                        </td>
                    </c:if>

                    <td>${book.name}</td>
                    <td>${book.author}</td>
                    <td>${book.press}</td>
                    <td>${book.isbn}</td>
                    <td>
                        <c:if test="${book.status == '1'}">
                            <span class="label label-warning">借阅中</span>
                        </c:if>
                        <c:if test="${book.status == '2'}">
                            <span class="label label-info">归还中</span>
                        </c:if>
                    </td>
                    <td>${book.borrower}</td>
                    <td>${book.borrowTime}</td>
                    <td>${book.returnTime}</td>

                        <%-- 操作栏逻辑重构 --%>
                    <td class="text-center">
                            <%-- 情况1：书籍状态是“借阅中”(1) --%>
                        <c:if test="${book.status == '1'}">
                            <%-- 只有“普通用户” 或者 管理员在“我的借阅”里，才能点归还申请 --%>
                            <c:if test="${USER_SESSION.role !='ADMIN' || showType == 'my'}">
                                <button type="button" class="btn bg-olive btn-xs" onclick="returnBook('${book.id}')">
                                    归还申请
                                </button>
                            </c:if>
                            <%-- 管理员在“全部借阅”里看别人借的书，只显示状态，不给按钮 --%>
                            <c:if test="${USER_SESSION.role =='ADMIN' && showType != 'my'}">
                                <span class="label label-default">借阅中</span>
                            </c:if>
                        </c:if>

                            <%-- 情况2：书籍状态是“归还中”(2) --%>
                        <c:if test="${book.status == '2'}">
                            <%-- 管理员显示“确认归还”按钮 --%>
                            <c:if test="${USER_SESSION.role =='ADMIN'}">
                                <button type="button" class="btn bg-blue btn-xs" onclick="returnConfirm('${book.id}')">
                                    确认归还
                                </button>
                            </c:if>
                            <%-- 普通用户显示“审核中” --%>
                            <c:if test="${USER_SESSION.role !='ADMIN'}">
                                <span class="label label-warning">审核中</span>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 数据表格 /-->
        <%--分页插件--%>
        <div id="pagination" class="pagination"></div>
    </div>
    <!-- 数据表格 /-->
</div>
<!-- /.box-body -->
</body>
<script>
    /*分页插件展示的总页数*/
    pageargs.total = Math.ceil(${pageResult.total}/pageargs.pagesize);
    /*分页插件当前的页码*/
    pageargs.cur = ${pageNum}

        function switchTab(type) {
            // 1. 修改隐藏域的值
            $("#showTypeInput").val(type);
            // 2. 如果切换到“我的借阅”，把借阅人搜索框清空，防止干扰
            if(type === 'my') {
                $("input[name='borrower']").val("");
            }
            // 3. 提交表单，重新加载页面
            $("#searchForm").submit();
        }

    // 【新增】全选/全不选
    function allSelect() {
        var isChecked = $("#selall").prop("checked");
        $("input[name='ids']").prop("checked", isChecked);
    }

    // 【新增】批量确认逻辑
    function batchConfirm() {
        // 1. 获取所有选中的id
        var ids = "";
        $("input[name='ids']:checked").each(function() {
            ids += $(this).val() + ",";
        });

        // 2. 校验
        if (ids == "") {
            alert("请至少选择一本书！");
            return;
        }

        // 3. 发送请求
        if (confirm("确定要批量确认这些图书的归还吗？")) {
            $.ajax({
                url: "${pageContext.request.contextPath}/book/batchReturnConfirm",
                type: "POST",
                data: {"ids": ids},
                success: function(res) {
                    alert(res.message);
                    if (res.success) {
                        window.location.reload();
                    }
                },
                error: function() {
                    alert("操作失败，请检查网络或联系管理员");
                }
            });
        }
    }

    /*分页插件页码变化时将跳转到的服务器端的路径*/
    pageargs.gourl = "${gourl}"
    /*保存搜索框中的搜索条件，页码变化时携带之前的搜索条件*/
    bookVO.name = "${search.name}"
    bookVO.author = "${search.author}"
    bookVO.press = "${search.press}"
    bookVO.showType = "${showType}"
    //防止翻页时丢失借阅人搜索条件
    bookVO.borrower = "${search.borrower}"
    /*分页效果*/
    pagination(pageargs);
</script>
</html>