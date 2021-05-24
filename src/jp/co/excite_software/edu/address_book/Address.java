package jp.co.excite_software.edu.address_book;

import java.util.List;

public class Address {
	private String uuid;
	private String name;
	private String kana;
	private List<String> mailAddressList;
	private List<String> phoneNumberList;
	private String address;
	private String memo;

	//TODO コンストラクタを作ればrsをリストに詰めることができる。
	public Address(String uuid, String name, String kana, List<String> mailAddressList,
			List<String> phoneNumberList, String address, String memo) {
		this.uuid = uuid;
		this.name = name;
		this.kana = kana;
		this.mailAddressList = mailAddressList;
		this.phoneNumberList = phoneNumberList;
		this.address = address;
		this.memo = memo;

	}

	public Address(String name, String kana, List<String> mailAddressList,
			List<String> phoneNumberList, String address, String memo) {
		this.name = name;
		this.kana = kana;
		this.mailAddressList = mailAddressList;
		this.phoneNumberList = phoneNumberList;
		this.address = address;
		this.memo = memo;
	}

	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public String getKana() {
		return kana;
	}

	public List<String> getMailAddressList() {
		return mailAddressList;
	}

	public List<String> getPhoneNumberList() {
		return phoneNumberList;
	}

	public String getAddress() {
		return address;
	}

	public String getMemo() {
		return memo;
	}
}
