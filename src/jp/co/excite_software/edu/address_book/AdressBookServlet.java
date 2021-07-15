package jp.co.excite_software.edu.address_book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdressBookServlet
 */
@WebServlet("/AdressBookServlet")
public class AdressBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdressBookServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//サーブレットを実行した時に一番最初に呼ばれる処理
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//アドレス一覧画面用データ取得
		AddressBook arb = new AddressBook();
		List<Address> addressList = arb.getAll();
		request.setAttribute("addressList", addressList);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/ADR001.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//URL
		//ボタンに応じてURLを設定
		String URL = null;
		//UUID
		String hidUuid = request.getParameter("hidUuid");

		//参照ボタン
		String btnRefer = request.getParameter("btnRefer");
		if (btnRefer != null) {
			String referUuid = request.getParameter("referUuid");
			AddressBook arb = new AddressBook();
			Address referAddress = arb.get(referUuid);
			request.setAttribute("referAddress", referAddress);
			URL = "WEB-INF/jsp/ADR003.jsp";
		}

		//削除ボタン
		String btnDelete = request.getParameter("btnDelete");
		if (btnDelete != null) {
			//削除処理
			deleteAddress(hidUuid);
			//アドレス一覧情報取得
			AddressBook arb = new AddressBook();
			List<Address> addressList = arb.getAll();
			request.setAttribute("addressList", addressList);
			URL = "WEB-INF/jsp/ADR001.jsp";
		}

		//一覧画面から新規登録ボタンが押下された時
		String btnAdd = request.getParameter("btnAdd");
		if (btnAdd != null) {
			URL = "WEB-INF/jsp/ADR002.jsp";
		}

		//新規登録画面から登録ボタンが押下された時
		//登録処理
		String btnRegister = request.getParameter("btnRegister");

		if (btnRegister != null) {
			String name = request.getParameter("txtName");
			String kana = request.getParameter("txtKana");
			List<String> mailAddressList = new ArrayList<String>();
			List<String> phoneNumberList = new ArrayList<String>();
			for (int i = 1; i < 4; i++) {
				String mailAddress = request.getParameter("txtMailAddress" + String.valueOf(i));
				mailAddressList.add(mailAddress);
				String phoneNumber = request.getParameter("txtPhoneNumber" + String.valueOf(i));
				phoneNumberList.add(phoneNumber);
			}
			String address = request.getParameter("txtAddress");
			String memo = request.getParameter("txtMemo");
			//登録処理
			registerAddress(name, kana, mailAddressList, phoneNumberList, address, memo);
			URL = "WEB-INF/jsp/ADR001.jsp";
			//アドレス一覧情報取得
			AddressBook arb = new AddressBook();
			List<Address> addressList = arb.getAll();
			request.setAttribute("addressList", addressList);
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(URL);
		dispatcher.forward(request, response);
	}

	private void registerAddress(String name, String kana, List<String> mailAddressList, List<String> phoneNumberList,
			String address, String memo) {
		Address registerAddress = new Address(name, kana, mailAddressList, phoneNumberList, address, memo);
		AddressBook arb = new AddressBook();
		arb.add(registerAddress);
	}

	private void updateAddress(String uuid, String name, String kana, List<String> mailAddressList,
			List<String> phoneNumberList, String address, String memo) {

	}

	private void deleteAddress(String uuid) {
		AddressBook arb = new AddressBook();
		arb.delete(uuid);
	}
}
