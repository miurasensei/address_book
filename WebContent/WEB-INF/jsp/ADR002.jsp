<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>新規登録</title>
	</head>
	<body>
		<form action = "/address_book/AdressBookServlet" method="post">
			<table>
				<tr><th>氏名</th><td><input type="text" name ="txtName" maxlength="50" required></td></tr>
				<tr><th>かな</th><td><input type="text" name ="txtKana" maxlength="50"></td></tr>
				<tr><th>メールアドレス</th><td><input type="email" name ="txtMailAddress1" maxlength="256"></td></tr>
				<tr><th></th><td><input type="email" name ="txtMailAddress2" maxlength="256"></td></tr>
				<tr><th></th><td><input type="email" name ="txtMailAddress3" maxlength="256"></td></tr>
				<tr><th>電話番号</th><td><input type="tel" name ="txtPhoneNumber1" maxlength="20" pattern="[\d()-]*"></td></tr>
				<tr><th></th><td><input type="tel" name ="txtPhoneNumber2" maxlength="20" pattern="[\d()-]*"></td></tr>
				<tr><th></th><td><input type="tel" name ="txtPhoneNumber3" maxlength="20" pattern="[\d()-]*"></td></tr>
				<tr><th>住所</th><td><input type="text" name ="txtAddress" maxlength="200"></td></tr>
				<tr><th>メモ</th><td><input type="text" name ="txtMemo" maxlength="2000"></td></tr>
			</table>

			<button type="submit" name="btnRegister" value = "btnRegister" onclick="return confirm('本当に登録してよろしいですか？')">登録</button>
		</form>
		<form action = "/address_book/AdressBookServlet" method="post">
			<button type="submit" id="btnBack" formmethod= "get">戻る</button>
		</form>
	</body>
</html>