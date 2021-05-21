package jp.co.excite_software.edu.address_book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddressBook {
	static private String UUID;
	static private String NAME;
	static private String KANA;
	static private String MEMO;
	static private String REGISTERD_DATETIME;
	static private String UPDATE_DATETIME;
	static private String MA_UUID;
	static private String MA_BOOK;
	static private String MA_SORT_ORDER;
	static private String MA_MAIL_ADDRESS;
	static private String MA_REGISTERED_DATETIME;
	static private String MA_UPDATE_DATETIME;
	static private String PN_UUID;
	static private String PN_BOOK_UUID;
	static private String PN_SORT_ORDER;
	static private String PN_PHONE_NUMBER;
	static private String PN_REGISTERED_DATETIME;
	static private String PN_UPDATE_DATETIME;
	static private String dbUrl = "jdbc:mysql://localhost:3306/learning?useUnicode=true&characterEncoding=utf8";
	static private String dbUser = "root";
	static private String dbPassword = "excite";


	List<Address> addressList = new ArrayList<Address>();

	String sql = "select * from ADDRESS_BOOK as a left outer join MAIL_ADDRESS as m on a.uuid = m.book_uuid left outer join PHONE_NUMBER p on a.uuid = p.book_uuid";

	public List<Address> getAll(){
		try(
				Connection conn = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
				PreparedStatement pStmt = conn.prepareStatement(sql);
				ResultSet rs = pStmt.executeQuery();
				){
			//select文の結果を格納
			while(rs.next()) {
				String uuid = rs.getString("a.uuid");
				String name = rs.getString("a.name");
				String kana = rs.getString("a.kana");
				List<String> mailAddressList = new ArrayList<String>();
				mailAddressList.set(0, rs.getString("m.mail_address"));
				List<String> phoneNumberList = new ArrayList<String>();
				phoneNumberList.set(0, rs.getString("p.phone_number"));
				String streetAddress = rs.getString("a.address");
				String memo = rs.getString("a.memo");

				Address address = new Address(uuid,name,kana,mailAddressList,phoneNumberList,streetAddress,memo);
				addressList.add(address);
			}

			return addressList;
		}catch(SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}



}
