<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>云借阅-用户注册</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webbase.css"/>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages-login-manage.css"/>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
  <style>
    .error-msg {
      color: red;
      font-size: 12px;
      margin-left: 5px;
      display: none;
    }
    .input-prepend {
      margin-bottom: 15px;
    }
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
              <input type="text" placeholder="请输入姓名" class="span2 input-xfat" name="name" id="name">
              <span id="nameMsg" class="error-msg"></span>
            </div>

            <!-- 邮箱 -->
            <div class="input-prepend">
              <span class="add-on loginname">邮箱</span>
              <input type="text" placeholder="请输入邮箱" class="span2 input-xfat" name="email" id="email">
              <span id="emailMsg" class="error-msg"></span>
            </div>

            <!-- 密码 -->
            <div class="input-prepend">
              <span class="add-on loginpwd">密码</span>
              <input type="password" placeholder="请输入密码" class="span2 input-xfat" name="password" id="password">
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
<script type="text/javascript">
  var isEmailValid = false;

  $(function() {
    $("#name").blur(function() {
      var name = $(this).val().trim();
      if (name == "") {
        showError("#nameMsg", "姓名不能为空");
      } else {
        showSuccess("#nameMsg");
      }
    });
    $("#email").blur(function() {
      var email = $(this).val().trim();
      isEmailValid = false;
      if (email == "") {
        showError("#emailMsg", "邮箱不能为空");
        return;
      }

      var url = "${pageContext.request.contextPath}/user/checkEmail";
      $.post(url, {email: email}, function(response) {
        if (response.success == true) {
          showSuccess("#emailMsg");
          isEmailValid = true;
        } else {
          showError("#emailMsg", response.message);
          isEmailValid = false;
        }
      }, "json");
    });

    $("input").focus(function(){
      $(this).parent().find(".error-msg").hide();
    });
  });

  function showError(selector, msg) {
    $(selector).text(msg).css("color", "red").show();
  }

  function showSuccess(selector) {
    $(selector).hide();
  }

  function submitRegister() {
    var name = $("#name").val().trim();
    var password = $("#password").val().trim();
    var email = $("#email").val().trim();

    if(name == "") {
      showError("#nameMsg", "姓名不能为空");
      return;
    }
    if(email == "") {
      showError("#emailMsg", "邮箱不能为空");
      return;
    }
    if (password == "") {
      alert("请输入密码！");
      return;
    }

    if (isEmailValid) {
      $("#registerForm").submit();
    } else {
      if($("#emailMsg").is(":hidden")) {
        $("#email").trigger("blur");
        setTimeout(function(){
          if(!isEmailValid) alert("邮箱重复！");
        }, 200);
      } else {
        alert("请修改邮箱！");
      }
    }
  }
</script>
</html>