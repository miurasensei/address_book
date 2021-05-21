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
	</head>
	<body>
		<input type="submit" value="新規登録" name="btnAdd">
		<table>
			<tr>
				<th>氏名</th>
				<th>かな</th>
				<th>メールアドレス</th>
				<th>電話番号</th>
			</tr>

			<% for(int i = 0; i < addressList.size(); i++){%>
			<tr>
				<th><label for="lblName"><%=addressList.get(i).getName()%></label></th>
				<th><label for="lblKana"><%=addressList.get(i).getKana()%></label></th>
				<th><label for="lblMail"><%=addressList.get(i).getMailAddressList()%></label></th>
				<th><label for="lblPhone"><%=addressList.get(i).getPhoneNumberList()%></label></th>
			</tr>
			<%} %>

		</table>
	</body>
</html>