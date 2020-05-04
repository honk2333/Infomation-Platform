<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<script src="js/jquery.validate.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
.error{
    color: red;
}
</style>

<script type="text/javascript">

	// $.validate.addMethod(
	//     "isExist",
	// 	function (value,element,params){
	// 		alert(value);
	// 		alert(element);
	// 		alert(params);
	//     }
	// );
    $.validator.addMethod(
        //规则的名称
        "checkUsername",
        //校验的函数
        function(value,element,params){

            //定义一个标志
            var flag = false;

            //value:输入的内容
            //element:被校验的元素对象
            //params：规则对应的参数值
            //目的：对输入的username进行ajax校验
            $.ajax({
                "async":false,
                "url":"${pageContext.request.contextPath}/user?method=checkUsername",
                "data":{"username":value},
                "type":"POST",
                "dataType":"json",
                "success":function(data){
                    flag = data.isExist;//如果存在为真
                }
            });
            //返回false代表该校验器不通过
			//如果存在flag为真，!flag就为假，返回的为假，
			// 如果和"checkUsername":true 不同，就不满足条件
            return !flag;
        }

    );


    $(function () {
        $("#form1").validate({
            rules:{
                "username":{
                    "required":true,
					 "checkUsername":true//判断之后，如果是true就为真，
                },
                 "password":{
                     "required":true,
                     "rangelength":[6,12]
                 },
                "checkName":{
                    "equalTo":"#password"
                },
                "email":{
                     "required":true,
                     "email":true
				}
            },
            messages:{
                "username":{
                    "required":"用户名不能为空",
                    "checkUsername":"已存在"
                },
                 "password":{
                     "required":"密码不能为空",
                     "rangelength":"密码长度应在6-12之间"
                 },
				"checkName":{
                    "equalTo":"两次密码不一致"
				},
                "email":{
                    "required":"邮箱不能为空",
                    "email":"邮箱格式不正确"
				}
            }
        });
    });
</script>


</head>
<body>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form id="form1" action="${pageContext.request.contextPath}/user?method=register" method="post"
					  class="form-horizontal" style="margin-top: 5px;">

					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username" name="username"
								placeholder="请输入用户名">
						</div>
					</div>

					<div class="form-group">
						<label for="password" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="password" name="password"
								placeholder="请输入密码">
						</div>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="confirmpwd" name="checkName"
								placeholder="请输入确认密码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="inputEmail3" name="email"
								placeholder="Email">
						</div>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="usercaption" name="name"
								placeholder="请输入姓名">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline">
								<input type="radio" name="sex" id="inlineRadio1" value="male">男
							</label>

							<label class="radio-inline">
							<input type="radio" name="sex" id="inlineRadio2" value="female">女
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control" name="birthday">
						</div>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="checkCode">

						</div>
						<div class="col-sm-2">
							<img src="./image/captcha.jhtml" />
						</div>

					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input type="submit" width="100" value="注册" name="submit"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
						</div>
					</div>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>




