<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>云借阅-用户注册</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webbase.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages-login-manage.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <style>
        .input-prepend {
            margin-bottom: 15px;
        }
        /* --- 核心样式：让 placeholder 变红 --- */
        .error-placeholder::placeholder {
            color: #ff4d4f !important; /* 强制红色 */
            opacity: 1;
        }
        /* 兼容不同浏览器 */
        .error-placeholder:-ms-input-placeholder { color: #ff4d4f !important; }
        .error-placeholder::-webkit-input-placeholder { color: #ff4d4f !important; }
    </style>
</head>
<body>
<div class="loginmanage">
    <div class="py-container">
        <h4 class="manage-title">云借阅-图书管理系统</h4>
        <div class="loginform">
            <ul class="sui-nav nav-tabs tab-wraped">
                <li class="active">
                    <h3>新用户注册</h3>
                </li>
            </ul>
            <div class="tab-content tab-wraped">
                <span style="color: red">${msg}</span>

                <div id="profile" class="tab-pane active">
                    <form id="registerForm" class="sui-form" action="${pageContext.request.contextPath}/user/register" method="post">

                        <!-- 姓名 -->
                        <div class="input-prepend">
                            <span class="add-on loginname">姓名</span>
                            <!-- 这里的 data-default 存的是“原来的文字”，用于点击恢复 -->
                            <input type="text" placeholder="请输入姓名" data-default="请输入姓名" class="span2 input-xfat" name="name" id="name">
                        </div>

                        <!-- 邮箱 -->
                        <div class="input-prepend">
                            <span class="add-on loginname">邮箱</span>
                            <input type="text" placeholder="请输入邮箱" data-default="请输入邮箱" class="span2 input-xfat" name="email" id="email">
                        </div>

                        <!-- 密码 -->
                        <div class="input-prepend">
                            <span class="add-on loginpwd">密码</span>
                            <input type="password" placeholder="请输入密码" data-default="请输入密码" class="span2 input-xfat" name="password" id="password">
                        </div>

                        <input type="hidden" name="role" value="USER">
                        <input type="hidden" name="status" value="0">

                        <div class="logined">
                            <a class="sui-btn btn-block btn-xlarge btn-danger" href="javascript:void(0)" onclick="submitRegister()" id="regBtn">注&nbsp;&nbsp;册</a>
                        </div>

                        <div style="text-align: right; margin-top: 10px;">
                            <a href="${pageContext.request.contextPath}/login?t=<%=new java.util.Date().getTime()%>" style="color: #666; text-decoration: none;">
                                已有账号？<span style="color: #d9534f;">去登录</span>
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<<script type="text/javascript">
    var isEmailValid = false;

    $(function() {
        // ==========================================
        // 1. 核心需求实现：失去焦点(Blur)即刻校验
        // ==========================================

        // --- 姓名框失去焦点 ---
        $("#name").blur(function() {
            var val = $(this).val().trim();
            // 如果为空，立刻变红报错
            if (val == "") {
                showInputError("#name", "姓名不能为空");
            }
        });

        // --- 邮箱框失去焦点 ---
        $("#email").blur(function() {
            var val = $(this).val().trim();

            // 1. 先判空
            if (val == "") {
                isEmailValid = false;
                showInputError("#email", "邮箱不能为空");
                return;
            }

            // 2. 如果不为空，再去后台查重
            var url = "${pageContext.request.contextPath}/user/checkEmail";
            $.post(url, {email: val}, function(response) {
                if (response.success == true) {
                    isEmailValid = true;
                } else {
                    isEmailValid = false;
                    showInputError("#email", response.message);
                }
            }, "json");
        });

        // --- 密码框失去焦点 (可选，建议也加上) ---
        $("#password").blur(function() {
            var val = $(this).val().trim();
            if (val == "") {
                showInputError("#password", "密码不能为空");
            }
        });

        // ==========================================
        // 2. 恢复逻辑：获得焦点(Focus)变回原样
        // ==========================================
        $("input").focus(function() {
            var $this = $(this);
            // 只有当它是红色报错状态时，才执行恢复
            if ($this.hasClass("error-placeholder")) {
                $this.removeClass("error-placeholder"); // 去掉红色
                $this.attr("placeholder", $this.data("default")); // 变回原来的“请输入xxx”
                $this.val(""); // 确保值是空的
            }
        });
    });

    // --- 工具函数：让输入框变红并显示错误字 ---
    function showInputError(selector, msg) {
        var $input = $(selector);
        $input.val("");                  // 清空
        $input.attr("placeholder", msg); // 换成错误文字
        $input.addClass("error-placeholder"); // 变红
    }

    // --- 注册按钮点击逻辑 ---
    function submitRegister() {
        // 这里的逻辑主要是防止用户直接点注册，没触发blur
        // 或者 blur 校验没通过
        $("#name").trigger("blur");
        $("#email").trigger("blur");
        $("#password").trigger("blur");

        // 给一点时间让 blur 执行完 (因为 ajax 是异步的)
        setTimeout(function(){
            // 检查是否有任何红色的错误框
            if ($(".error-placeholder").length > 0) {
                return; // 有错误，不提交
            }

            // 再次确认邮箱状态
            if (isEmailValid) {
                $("#registerForm").submit();
            }
        }, 200);
    }
</script>
</html>