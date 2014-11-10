package com.incontact.ws;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import com.incontact.ws.pojo.IncontactAthctn;
import com.incontact.ws.pojo.TokenResponse;

/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * IIncontactPerformanceWS.java
 */

public interface IIncontactPerformanceWS {

	public abstract void setIncontactAthctn(IncontactAthctn incontactAthctn);

	public abstract TokenResponse getToken() throws UnsupportedEncodingException, MalformedURLException, ProtocolException, IOException;

}