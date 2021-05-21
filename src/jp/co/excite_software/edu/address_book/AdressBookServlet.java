package jp.co.excite_software.edu.address_book;

import java.io.IOException;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//アドレス一覧情報取得
		AddressBook arb = new AddressBook();
		List<Address> addressList = arb.getAll();
		String a = addressList.get(0).getName();

		//リクエストスコープに保存
		request.setAttribute("addressList",addressList);
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/ADR001.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	private void registerAddress(String name,String kana,List<String>mailAddressList,List<String>phoneNumberList,String address,String memo) {

	}

	private void updateAddress(String uuid,String name,String kana,List<String> mailAddressList,List<String> phoneNumberList,String address,String memo) {

	}

	private void deleteAddress(String uuid) {

	}
}
