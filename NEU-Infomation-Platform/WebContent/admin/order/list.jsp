<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="${pageContext.request.contextPath}/js/public.js"></script>
		
		<!-- 弹出层插件 -->
		<link href="${pageContext.request.contextPath}/css/popup_layer.css" type="text/css" rel="stylesheet"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/popup_layer.js"></script>		
		<!-- 调用插件弹出层的方法 -->
		<script type="text/javascript">
			$(function(){
				//弹出层插件调用
				new PopupLayer({
					trigger:".clickedElement",
					popupBlk:"#showDiv",
					closeBtn:"#closeBtn",
					useOverlay:true
				});
			});

			function findOrderItemByOid(oid) {
                //清理上一次显示的内容覆盖
                $("#showDivTab").html("");
                $("#shodDivOid").html("");
                $("#loading").css("display","block");

                $.post(
                    "${pageContext.request.contextPath}/admin?method=findOrderItemByOid",
                    {"oid":oid},
                    function (date) {
                        //隐藏加载图片
                        $("#loading").css("display","none");

                        var content="<tr id='showTableTitle'><th width='20%'>图片</th> <th width='25%'>商品</th>";
                        content+="<th width='20%'>价格</th><th width='15%'>数量</th> <th width='20%'>小计</th>";
                        content+="</tr> ";
                        for(var i=0;i<date.length;i++){
                            content+="<tr style='text-align: center;'><td>"
                            content+="<img src='${pageContext.request.contextPath }/"+date[i].product.pimage+"' width='70'";
                            content+="height='60'> </td>";
                            content+="<td><a target='_blank'>"+date[i].product.pname+"</a></td>";
                            content+="<td>￥"+date[i].product.shop_price+"</td>";
                            content+="<td>"+date[i].count+"</td>";
                            content+="<td><span class='subtotal'>￥"+date[i].subtotal+"</span></td></tr>";
                        }

                        $("#showDivTab").html(content);

                        //订单编号,因为也要显示订单编号
                        $("#shodDivOid").html(oid);
                    },
                    "json"
                );
            }
		</script>
		
	</HEAD>
	<body>
	
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>发布列表</strong>
						</TD>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="10%">
										序号
									</td>
									<td align="center" width="10%">
										编号
									</td>
									<td align="center" width="10%">
										金额
									</td>
									<td align="center" width="10%">
										收货人
									</td>
									<td align="center" width="10%">
										状态
									</td>
									<td align="center" width="50%">
										详情
									</td>
								</tr>

								<c:forEach items="${ordersList}" var="order" varStatus="sta">
									<tr onmouseover="this.style.backgroundColor = 'white'"
										onmouseout="this.style.backgroundColor = '#F5FAFE';">
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="18%">
											${sta.count}
										</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="17%">
											${order.oid}
										</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="17%">
											${order.total}
										</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="17%">
											${order.name}
										</td>
										<td style="CURSOR: hand; HEIGHT: 22px" align="center"
											width="17%">
											${order.state==1?"已付款":"未付款"}
										</td>
										<td align="center" style="HEIGHT: 22px">
											<input type="button" value="订单详情" class="clickedElement" onclick="findOrderItemByOid('${order.oid}')"/>
										</td>

									</tr>
								</c:forEach>

								
							</table>
						</td>
					</tr>
					
				</TBODY>
			</table>
		</form>
		
		<!-- 弹出层 HaoHao added -->
        <div id="showDiv" class="blk" style="display:none;">
            <div class="main">
                <h2>订单编号：<span id="shodDivOid" style="font-size: 13px;color: #999"></span></h2>
                <a href="javascript:void(0);" id="closeBtn" class="closeBtn">关闭</a>
                <div id="loading" style="padding-top:30px;text-align: center;">
                	<img alt="" src="${pageContext.request.contextPath }/images/loading.gif">
                </div>
				<div style="padding:20px;">
					<table id="showDivTab" style="width:100%">

					</table>
				</div>
            </div>
            
        </div>
		
		
	</body>
</HTML>

