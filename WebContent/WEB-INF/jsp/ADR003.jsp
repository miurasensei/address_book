<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jp.co.excite_software.edu.address_book.Address" %>
<!-- 一覧に表示するモデルをサーブレットから引き継ぐ -->
<%Address referAddress = (Address)request.getAttribute("referAddress"); %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>参照・削除</title>
		<link rel = "stylesheet" href = "style003.css">
		<script type="text/javascript"></script>
		<script src="https://code.jquery.com/jquery-3.3.1.js"></script>
	</head>
	<body>
		<form method = "post">
			<div class = "container">
				<table>
					<input type="hidden" name="hidUuid" id="hidUuid" value="<%=referAddress.getUuid() %>">
					<tr><th>氏名</th><td><%=referAddress.getName() %></td></tr>
					<tr><th>かな</th><td><%=referAddress.getKana() %></td></tr>
					<tr><th>メールアドレス</th><td><%=referAddress.getMailAddressList().get(0) %></td></tr>
					<tr><th></th><td><%=referAddress.getMailAddressList().get(1) %></td></tr>
					<tr><th></th><td><%=referAddress.getMailAddressList().get(2) %></td></tr>
					<tr><th>電話番号</th><td><%=referAddress.getPhoneNumberList().get(0) %></td></tr>
					<tr><th></th><td><%=referAddress.getPhoneNumberList().get(1) %></td></tr>
					<tr><th></th><td><%=referAddress.getPhoneNumberList().get(2) %></td></tr>
					<tr><th>住所</th><td><%=referAddress.getAddress() %></td></tr>
					<tr><th>メモ</th><td><%=referAddress.getMemo() %></td></tr>
				</table>
			</div>
			<div class = "container_btn">
				<button type=submit formaction="/address_book/AdressBookServlet" name="btnEdit">修正</button>

				<!--method="get"にして戻る  -->
				<button type=submit formaction="/address_book/AdressBookServlet" name="btnBack" value="btnBack" formmethod = "get">戻る</button>

				<button type=submit formaction="/address_book/AdressBookServlet" name="btnDelete" value="btnDelete" onclick="return confirm('本当に削除してよろしいですか？')">削除</button>
			</div>
		</form>
	</body>
</html>