<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 定义状态常量（消除魔法值） --%>
<c:set var="STATUS_AVAILABLE" value="${0}"></c:set>
<c:set var="STATUS_BORROWED" value="${1}"></c:set>
<c:set var="STATUS_RETURNING" value="${2}"></c:set>
<html>
<head>
    <meta charset="utf-8">
    <title>新书推荐</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pagination.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/pagination.js"></script>
    <script src="${pageContext.request.contextPath}/js/my.js"></script>
    <style>
        /* 仅保留必要样式 */
        .error-msg { color: #dc3545; padding: 10px; background: #f8d7da; border-radius: 4px; margin-bottom: 15px; }
        .empty-tip { padding: 20px; text-align: center; color: #999; }
        .upload-time { color: #dc3545; font-weight: 500; }
    </style>
</head>
<body class="hold-transition skin-red sidebar-mini">
<%-- 错误提示（提升用户体验） --%>
<c:if test="${not empty errorMsg}">
    <div class="error-msg">${errorMsg}</div>
</c:if>

<!-- 数据展示头部 -->
<div class="box-header with-border">
    <h3 class="box-title">新书推荐</h3>
</div>

<!-- 数据展示内容区 -->
<div class="box-body">
    <!-- 数据表格 -->
    <table id="dataList" class="table table-bordered table-striped table-hover dataTable text-center">
        <thead>
        <tr>
            <th class="sorting_asc">图书名称</th>
            <th class="sorting">图书作者</th>
            <th class="sorting">出版社</th>
            <th class="sorting">标准ISBN</th>
            <th class="sorting">上架时间</th> <%-- 保留新书核心字段，标红突出 --%>
            <th class="sorting">书籍状态</th>
            <th class="sorting">借阅人</th>
            <th class="sorting">借阅时间</th>
            <th class="sorting">预计归还时间</th>
            <th class="text-center">操作</th>
        </tr>
        </thead>
        <tbody>
        <%-- 空数据提示（提升体验） --%>
        <c:choose>
            <c:when test="${empty pageResult.rows}">
                <tr><td colspan="10" class="empty-tip">暂无最新上架图书</td></tr>
            </c:when>
            <c:otherwise>
                <c:forEach items="${pageResult.rows}" var="book">
                    <tr>
                        <td>${book.name}</td>
                        <td>${book.author}</td>
                        <td>${book.press}</td>
                        <td>${book.isbn}</td>
                        <td class="upload-time">${book.uploadTime}</td> <%-- 标红突出新书特征 --%>
                        <td>
                                <%-- 状态标签化（替代纯文本，提升可读性） --%>
                            <c:choose>
                                <c:when test="${book.status == STATUS_AVAILABLE}">
                                    <span class="label label-success">可借阅</span>
                                </c:when>
                                <c:when test="${book.status == STATUS_BORROWED}">
                                    <span class="label label-warning">借阅中</span>
                                </c:when>
                                <c:when test="${book.status == STATUS_RETURNING}">
                                    <span class="label label-info">归还中</span>
                                </c:when>
                            </c:choose>
                        </td>
                            <%-- 空值处理（避免显示null） --%>
                        <td>${book.borrower == null ? "" : book.borrower}</td>
                        <td>${book.borrowTime == null ? "" : book.borrowTime}</td>
                        <td>${book.returnTime == null ? "" : book.returnTime}</td>
                        <td class="text-center">
                            <c:if test="${book.status == STATUS_AVAILABLE}">
                                <button type="button" class="btn bg-olive btn-xs" data-toggle="modal"
                                        data-target="#borrowModal" onclick="findBookById(${book.id},'borrow')">
                                    借阅
                                </button>
                            </c:if>
                            <c:if test="${book.status == STATUS_BORROWED || book.status == STATUS_RETURNING}">
                                <button type="button" class="btn bg-olive btn-xs" disabled="true">借阅</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>
    <!-- 数据表格 /-->
</div>
<!-- 数据展示内容区/ -->

<%--引入存放模态窗口的页面--%>
<jsp:include page="/WEB-INF/views/book_modal.jsp"></jsp:include>
</body>
</html>