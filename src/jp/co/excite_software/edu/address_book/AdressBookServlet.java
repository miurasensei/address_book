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
	//一番最初に呼ばれる処理
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//アドレス一覧情報取得
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

		//新規登録
		String btnAdd = request.getParameter("btnAdd");
		if (btnAdd != null) {
			URL = "WEB-INF/jsp/ADR002.jsp";
		}

		//TODO 登録処理
		String btnRegister = request.getParameter("btnRegister");
		if (btnRegister != null) {
			String name = request.getParameter("txtName");
			String kana = request.getParameter("txtKana");
			List<String> mailAddressList = new ArrayList<String>();
			String txtMailAddress1 = request.getParameter("txtMailAddress1");
			String txtMailAddress2 = request.getParameter("txtMailAddress2");
			String txtMailAddress3 = request.getParameter("txtMailAddress3");
			mailAddressList.add(txtMailAddress1);
			mailAddressList.add(txtMailAddress2);
			mailAddressList.add(txtMailAddress3);
			List<String> phoneNumberList = new ArrayList<String>();
			String txtPhoneNumber1 = request.getParameter("txtPhoneNumber1");
			String txtPhoneNumber2 = request.getParameter("txtPhoneNumber2");
			String txtPhoneNumber3 = request.getParameter("txtPhoneNumber3");
			phoneNumberList.add(txtPhoneNumber1);
			phoneNumberList.add(txtPhoneNumber2);
			phoneNumberList.add(txtPhoneNumber3);
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
		AddressBook arb = new AddressBook();
		Address registerAddress = new Address(name, kana, mailAddressList, phoneNumberList, address, memo);
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
