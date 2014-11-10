package com.incontact.ws.pojo;

import com.incontact.ws.acnt.HandleUserAccount;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * IncontactAthctn.java
 */

public class IncontactAthctn {

	private String vendorName;
	private String appName;
	private String busNo;
	private String username;
	private String password;
	private String scope;
	private String baseURL;
	private HandleUserAccount handleUserAccount;
	
	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) throws Exception {
		this.vendorName = this.handleUserAccount.decrypt(vendorName);
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) throws Exception {
		this.appName = this.handleUserAccount.decrypt(appName);
	}

	public String getBusNo() {
		return busNo;
	}

	public void setBusNo(String busNo) throws Exception {
		this.busNo = this.handleUserAccount.decrypt(busNo);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) throws Exception {
		this.username = this.handleUserAccount.decrypt(username);
	}

	public String getPassword() {
        return password;
	}

	public void setPassword(String password) throws Exception {
		this.password = this.handleUserAccount.decrypt(password);
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) throws Exception {
		this.baseURL = this.handleUserAccount.decrypt(baseURL);
	}

	public HandleUserAccount getHandleUserAccount() {
		return handleUserAccount;
	}

	public void setHandleUserAccount(HandleUserAccount handleUserAccount) {
		this.handleUserAccount = handleUserAccount;
	}
}
