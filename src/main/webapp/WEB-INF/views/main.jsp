<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title>云借阅-图书管理系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/_all-skins.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script type="text/javascript">
        function SetIFrameHeight() {
            var iframeid = document.getElementById("iframe");
            if (document.getElementById) {
                /*设置 内容展示区的高度等于页面可视区的高度*/
                iframeid.height = document.documentElement.clientHeight;
            }
        }
    </script>
</head>

<style>
    /* 右下角弹窗样式 */
    #overdue-toast {
        position: fixed;
        bottom: 20px;
        right: -350px;
        width: 320px;
        background-color: #fff;

        border-top: 3px solid #dd4b39;
        border-radius: 3px;

        box-shadow: 0 4px 10px rgba(0,0,0,0.15);
        z-index: 9999;
        transition: right 0.5s ease-in-out;
        font-family: 'Source Sans Pro', sans-serif;
    }
    .toast-header {
        padding: 15px;
        background-color: #f8f8f8;
        border-bottom: 1px solid #eee;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .toast-title {
        font-weight: bold;
        color: #dd4b39;
        font-size: 16px;
    }
    .toast-close {
        cursor: pointer;
        font-size: 20px;
        color: #999;
    }
    .toast-body {
        padding: 20px 15px;
        color: #333;
        font-size: 14px;
    }
    .toast-btn {
        display: inline-block;
        padding: 6px 12px;
        margin-top: 10px;
        background-color: #dd4b39;
        color: white;
        text-decoration: none;
        border-radius: 3px;
        font-size: 12px;
    }
    .toast-btn:hover { background-color: #c63c2c; color: #fff; }
</style>

<div id="overdue-toast">
    <div class="toast-header">
        <span class="toast-title"><i class="fa fa-warning"></i> 逾期提醒</span>
        <span class="toast-close" onclick="closeToast()">&times;</span>
    </div>
    <div class="toast-body">
        您有图书已逾期未归还！请尽快前往归还。
        <div style="text-align: right">
            <%-- 点击跳转到当前借阅页面 --%>
                <a href="javascript:void(0)" onclick="goToBorrowed()" class="toast-btn">去查看</a>
<%--            <a href="${pageContext.request.contextPath}/book/searchBorrowed" class="toast-btn">去查看</a>--%>
        </div>
    </div>
</div>

<script>
    // 1. 关闭弹窗
    function closeToast() {
        $("#overdue-toast").css("right", "-350px");
        sessionStorage.setItem("overdueNotified", "true");
    }

    // 2. 显示弹窗
    function showToast() {
        // if(sessionStorage.getItem("overdueNotified") === "true") return;

        $("#overdue-toast").css("right", "20px");
    }

    // 3. 跳转到借阅页面
    function goToBorrowed() {
        // 目标地址
        var targetUrl = "${pageContext.request.contextPath}/book/searchBorrowed?showType=my";

        var iframe = document.getElementById("iframe");

        if (iframe) {
            iframe.src = targetUrl;
            closeToast();
        } else {
            window.location.href = targetUrl;
        }
    }

    // 4. 核心检查逻辑
    function checkOverdueLogic() {
        $.post("${pageContext.request.contextPath}/book/checkOverdue", function(res) {
            if (res.success) {
                showToast();
            }
        }, "json");
    }

    // 5. 定时器
    $(function() {
        // 登录/加载页面后，延迟1秒检查一次
        setTimeout(checkOverdueLogic, 1000);
        // 每分钟检查一次
        setInterval(checkOverdueLogic, 60000);
    });
</script>

<body class="hold-transition skin-green sidebar-mini">
<div class="wrapper">
    <!-- 页面头部 -->
    <header class="main-header">
        <!-- Logo -->
        <a href="${pageContext.request.contextPath}/main?t=<%=new java.util.Date().getTime()%>" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>云借阅</b></span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>云借阅-图书管理系统</b></span>
        </a>
        <!-- 头部导航 -->
        <nav class="navbar navbar-static-top">
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="${pageContext.request.contextPath}/img/user.jpg" class="user-image"
                                 alt="User Image">
                            <span class="hidden-xs">${USER_SESSION.name}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="user-header">
                                <img src="${pageContext.request.contextPath}/img/user.jpg" class="img-circle"
                                     alt="User Image">
                                <p>
                                    ${USER_SESSION.name}
                                    <small>${USER_SESSION.email}</small>
                                </p>
                            </li>
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat" data-toggle="modal" data-target="#editSelfModal">修改信息</a>
                                </div>
                                <div class="pull-right">
                                    <a href="javascript:deleteMyself(${USER_SESSION.id})" class="btn btn-danger btn-flat">账户注销</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown user user-menu">
                        <a href="${pageContext.request.contextPath}/user/logout?t=<%=new java.util.Date().getTime()%>">
                            <span class="hidden-xs">退出</span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- 页面头部 /-->

    <!-- 导航侧栏 -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- /.search form -->
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu">
                <li id="admin-index">
                    <a href="${pageContext.request.contextPath}/main?t=<%=new java.util.Date().getTime()%>">
                        <i class="fa fa-dashboard"></i> <span>首页</span>
                    </a>
                </li>
                <!-- 用户管理（仅管理员可见） -->
                <c:if test="${USER_SESSION.role == 'ADMIN'}">
                    <li id="admin-login">
                        <a href="${pageContext.request.contextPath}/user/search?t=<%=new java.util.Date().getTime()%>" target="iframe">
                            <i class="fa fa-circle-o"></i>用户管理
                        </a>
                    </li>
                </c:if>
                <!-- 图书管理 -->
                <li class="treeview">
                    <a href="#">
                        <i class="fa fa-folder"></i>
                        <span>图书管理</span>
                        <span class="pull-right-container">
				       			<i class="fa fa-angle-left pull-right"></i>
				   		 	</span>
                    </a>
                    <ul class="treeview-menu">
                        <li>
                            <a href="${pageContext.request.contextPath}/book/search?t=<%=new java.util.Date().getTime()%>" target="iframe">
                                <i class="fa fa-circle-o"></i>图书借阅
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/book/searchBorrowed?t=<%=new java.util.Date().getTime()%>" target="iframe">
                                <i class="fa fa-circle-o"></i>当前借阅
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/record/searchRecords?t=<%=new java.util.Date().getTime()%>" target="iframe">
                                <i class="fa fa-circle-o"></i>借阅记录
                            </a>
                        </li>
                    </ul>
                </li>
                <!-- 数据统计入口，置底且仅管理员可见 -->
                <c:if test="${USER_SESSION.role == 'ADMIN'}">
                    <li class="treeview">
                        <a href="${pageContext.request.contextPath}/stats/dashboard?t=<%=new java.util.Date().getTime()%>" target="iframe">
                            <i class="fa fa-pie-chart"></i>
                            <span>数据统计</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </section>

        <!-- /.sidebar -->
    </aside>
    <!-- 导航侧栏 /-->
    <!-- 内容展示区域 -->
    <div class="content-wrapper">
        <iframe width="100%" id="iframe" name="iframe" onload="SetIFrameHeight()"
                frameborder="0" src="${pageContext.request.contextPath}/book/selectNewbooks"></iframe>
    </div>
</div>

<!-- 个人信息修改 -->
<div class="modal fade" id="editSelfModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="myModalLabel">个人信息</h3>
            </div>
            <div class="modal-body">
                <form id="editSelfForm">
                    <input type="hidden" name="id" value="${USER_SESSION.id}">
                    <input type="hidden" name="role" value="${USER_SESSION.role}">
                    <input type="hidden" name="status" value="${USER_SESSION.status}">
                    <input type="hidden" name="email" value="${USER_SESSION.email}">
                    <table class="table table-bordered table-striped" width="800px">
                        <tr>
                            <td>用户姓名</td>
                            <td>
                                <input class="form-control" name="name" value="${USER_SESSION.name}" placeholder="请输入姓名">
                            </td>
                        </tr>
                        <tr>
                            <td>登录密码</td>
                            <td>
                                <input class="form-control" type="password" name="password" placeholder="不修改则留空">
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" onclick="updateSelfInfo()">保存</button>
                <button class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    function deleteMyself(userId) {
        var r = confirm("您确定要注销当前账号吗？\n注销后您将无法登录！");
        if (r == true) {
            var url = "${pageContext.request.contextPath}/user/delUser";
            $.post(url, {id: userId}, function(response) {
                if (response.success == true) {
                    window.location.href = "${pageContext.request.contextPath}/user/logout?t=<%=new java.util.Date().getTime()%>";
                } else {
                    alert(response.message);
                }
            }, "json");
        }
    }

    function updateSelfInfo() {
        var r = confirm("是否确认保存修改？保存后需重新登录！");
        if (r == true) {
            var url = "${pageContext.request.contextPath}/user/editUser";
            var data = $("#editSelfForm").serialize();
            $.post(url, data, function(response) {
                alert(response.message);
                if(response.success) {
                    window.location.href = "${pageContext.request.contextPath}/user/logout?t=<%=new java.util.Date().getTime()%>";
                }
            }, "json");
        }
    }
</script>
</body>
</html>
