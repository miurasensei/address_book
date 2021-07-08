<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.excite_software.edu.address_book.Address" %>
<%@ page import="java.util.List"%>
<%List<Address> addressList = (List<Address>)request.getAttribute("addressList");%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>アドレス一覧</title>
		<link rel = "stylesheet" href = "style001.css">
		<script type="text/javascript"></script>
		<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
	</head>
	<body>
		<div class="container">
			<form action="/address_book/AdressBookServlet" method="post">
			
			<input type="submit" value="新規登録" name="btnAdd" id="btnAdd">
			<table>
				<tr>
					<input type="hidden" name="referUuid" id="referUuid">
					<th>氏名</th>
					<th>かな</th>
					<th>メールアドレス</th>
					<th>電話番号</th>
				</tr>

				<% for(int i = 0; i < addressList.size(); i++){%>
				<tr>
					<input name="hidUuid" type="hidden" value="<%=addressList.get(i).getUuid() %>">
					<th class="border"><label for="lblName"><%=addressList.get(i).getName()%></label></th>
					<th class="border"><label for="lblKana"><%=addressList.get(i).getKana()%></label></th>
					<th class="border"><label for="lblMail"><%=addressList.get(i).getMailAddressList().get(i)%></label></th>
					<th class="border"><label for="lblPhone"><%=addressList.get(i).getPhoneNumberList().get(i)%></label></th>
					<th><button name= "btnRefer" value="btnRefer" type="submit" data-id ="<%= i%>">参照</button></th>
				</tr>
				<%} %>
			</form>
			</table>
		</div>
		<script>
			$('[name="btnRefer"]').on('click',function(){
				var id = $(this).data('id');
				var hidUuid = document.getElementsByName("hidUuid");
				document.getElementById("referUuid").value = hidUuid[id].value;
			});
		</script>
	</body>
</html>