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
        .error-placeholder::placeholder {
            color: #ff4d4f !important;
            opacity: 1;
        }
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

                        <div class="input-prepend">
                            <span class="add-on loginname">姓名</span>
                            <input type="text" placeholder="请输入姓名" data-default="请输入姓名" class="span2 input-xfat" name="name" id="name">
                        </div>

                        <div class="input-prepend">
                            <span class="add-on loginname">邮箱</span>
                            <input type="text" placeholder="请输入邮箱" data-default="请输入邮箱" class="span2 input-xfat" name="email" id="email">
                        </div>

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
        //失去焦点即刻校验
        //姓名框失去焦点
        $("#name").blur(function() {
            var val = $(this).val().trim();
            if (val == "") {
                showInputError("#name", "姓名不能为空");
            }
        });

        //邮箱框失去焦点
        $("#email").blur(function() {
            var val = $(this).val().trim();

            if (val == "") {
                isEmailValid = false;
                showInputError("#email", "邮箱不能为空");
                return;
            }

            //如果不为空，再去后台查重
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

        //密码框失去焦点
        $("#password").blur(function() {
            var val = $(this).val().trim();
            if (val == "") {
                showInputError("#password", "密码不能为空");
            }
        });

        //获得焦点变回原样
        $("input").focus(function() {
            var $this = $(this);
            if ($this.hasClass("error-placeholder")) {
                $this.removeClass("error-placeholder");
                $this.attr("placeholder", $this.data("default"));
                $this.val("");
            }
        });
    });

    //让输入框变红并显示错误字
    function showInputError(selector, msg) {
        var $input = $(selector);
        $input.val("");
        $input.attr("placeholder", msg);
        $input.addClass("error-placeholder");
    }

    //注册按钮点击逻辑
    function submitRegister() {
        $("#name").trigger("blur");
        $("#email").trigger("blur");
        $("#password").trigger("blur");

        setTimeout(function(){
            if ($(".error-placeholder").length > 0) {
                return;
            }
            if (isEmailValid) {
                $("#registerForm").submit();
            }
        }, 200);
    }
</script>
</html>