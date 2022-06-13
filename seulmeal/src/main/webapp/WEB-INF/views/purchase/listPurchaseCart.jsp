<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<title>장바구니 목록</title>

</head>

<body>
<jsp:include page="../layer/header.jsp"></jsp:include>

	<style>
		
		h2{
			text-align: center; 
		}

		h2:after {
			content: "";
			display: block;
			width: 180px;
			border-bottom: 1px solid #bcbcbc;
			margin: 20px auto;
		}
		
		h5:after {
	        content: "";
	        display: block;
	        width: 300px;
	        border-bottom: 1px solid #bcbcbc;
	        margin: 20px auto;
		}
		
		#order{
		    bottom: 200px;
		    right: 11000px;
		    width: 200px;
		    border: 3px solid #73AD21;
		}
		
		
	</style>
	
	 <div class="container">

		<div class="page-header">
		 	<h2>장바구니</h2>
		 </div>
		 
		 <table class="table table-hover table-striped" >
	 
	        <thead>
	          <tr>
	            <th align="center">NO</th>
	            <th align="center">이미지</th>
	            <th align="center">상품명</th>
	            <th align="center">옵션</th>
	             <th align="center">수량</th>
	            <th align="center">합계</th>
	            <th align="center"></th>
	          </tr>
	        </thead>
	
			<tbody>
				<c:set var="total" value="0" />
				<c:set var="customprice" value="0" />
				<c:set var="i" value="0" />
				<c:forEach var="cpd" items="${customProductList}">
					<c:set var="i" value="${i+1}" />
					<c:set var="customprice" value="${cpd.price}" />
					<tr class="ct_list_pop">
						  <td align="left">${i}</td>
						  <td align="left" data-value="${cpd.product.productNo}" title="Click : 상품확인" >${cpd.product.thumbnail}</td>
						  <td align="left">${cpd.product.name}</td>
						  <td align="left">
						  <c:forEach var="pp" items="${cpd.plusParts}">
						  	+ ${pp.parts.name}, ${pp.gram}g, <fmt:formatNumber type="number" maxFractionDigits="0"  value="${pp.parts.price*pp.gram/10}" />원 <br/>
						  	</c:forEach>
						  <c:forEach var="mp" items="${cpd.minusParts}">
						  	- ${mp.minusName} <br/>
						  	</c:forEach> 
						  	 </td>
						  <td align="left">
						  	<button type='button' class="btn btn-outline-primary btn-sm minus" onclick="fnCalCount('minus',this);">-</button>
						  	&ensp; <span id ="count" name="count"> ${cpd.count} </span> &ensp;
						  	<button type='button' class="btn btn-outline-primary btn-sm plus" onclick="fnCalCount('plus',this);">+</button> 
						  </td>
						  <td align="left">
						  <span id="customprice" name="customprice">${cpd.price*cpd.count}</span>원</td>
						  <td align="left">
						  	<button type="button" class="btn btn-outline-primary change" onclick="window.location.href='/purchase/updateCustomProduct/' + ${cpd.customProductNo}">수정</button><br/>
						  	<button type="button" class="btn btn-outline-primary delete" onclick="window.location.href='/purchase/deleteCustomProduct/' + ${cpd.customProductNo}">삭제</button>
						  </td>
						  <c:set var="total" value="${total+cpd.price*cpd.count}" />
						  
					  </tr>  
				  </c:forEach>
	        </tbody>
	      </table>
	      
	      
	      <div class="row">
	    	<div class="col-md-6 text-center">
			<h5>결제예정 금액 :  <span id="total">${total}</span>원</h5>
			
			<h5>적립예정 포인트 : 
			  <c:choose>
				<c:when test="${user.grade eq '0'}"><span id="total">${total*0.05}</span>P<br/></c:when>
				<c:when test="${user.grade eq '1'}"><span id="total">${total*0.1}</span>P<br/></c:when>
				<c:when test="${user.grade eq '2'}"><span id="total">${total*0.3}</span>P<br/></c:when>
				<c:when test="${user.grade eq '3'}"><span id="total">${total*0.5}</span>P<br/></c:when>
			 </c:choose>
			</h5>
			</div>
			<div class="col-md-6 text-right">
				<button class="btn btn-outline-primary" id="order" style="margin-right:10px;">전체상품주문</button>
			</div>
		</div>
	</div>

		
	<script type="text/javascript">
	
	$(function (){
	    $("change").on("click",function(){
	    	$.ajax({
				url:"/purchase/api/getCustomProduct/"+customProductNo,
				method:"GET",
		        headers : {
		            "Accept" : "application/json",
		            "Content-Type" : "application/json"
		        },
		        dataType : "json",
		        success : function(data){	
		        	
		        }
	    	});
	    });
	});
	
	
	function fnCalCount(type, ths){
		var stat = $(ths).parents("td").find("span[name='count']").text();
		var num = parseInt(stat,10);
		var price=parseInt($(ths).parents("tr").find("span[name='customprice']").text());
		var oneprice = parseInt(price,10)/num;
		console.log("oneprice:"+oneprice);
		
		if(type=='minus'){
			num--;
			if(num<1){
				alert('더이상 줄일수 없습니다.');
				return;
			}
			$(ths).parents("td").find("span[name='count']").text(num);
			
			const minus = price - oneprice;
			$(ths).parents("tr").find("span[name='customprice']").text(minus);
			const minustotal = parseInt($("#total").text()) - oneprice;
			$("#total").text((minustotal));

		}else{
			num++;
			$(ths).parents("td").find("span[name='count']").text(num);
			
            const plus = price + oneprice;
            $(ths).parents("tr").find("span[name='customprice']").text(plus);
            const plustotal = parseInt($("#total").text()) + oneprice;
			$("#total").text(plustotal);
		}
	}
	
	
	
	</script>

<jsp:include page="../layer/footer.jsp"></jsp:include>	

</body>
</html>