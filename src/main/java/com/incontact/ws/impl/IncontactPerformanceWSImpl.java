package com.incontact.ws.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONException;
import org.json.JSONObject;

import com.incontact.ws.IIncontactPerformanceWS;
import com.incontact.ws.pojo.IncontactAthctn;
import com.incontact.ws.pojo.TokenResponse;



/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * IncontactPerformanceWSImpl.java
 */

public class IncontactPerformanceWSImpl implements IIncontactPerformanceWS {
	
	private IncontactAthctn incontactAthctn;

	@Override
	public TokenResponse getToken() throws IOException {
		TokenResponse tokenResponse = new TokenResponse();

		String endpoint = incontactAthctn.getBaseURL();
		String AuthToken = incontactAthctn.getAppName() + "@" + incontactAthctn.getVendorName() + ":" + incontactAthctn.getBusNo();
		String encodedAuthToken;
		try {
			encodedAuthToken = DatatypeConverter.printBase64Binary(AuthToken.getBytes("UTF-8"));

			String postData = "{\"grant_type\":\"password\",\"username\":\"" + incontactAthctn.getUsername() + "\",\"password\":\"" + incontactAthctn.getPassword()
					+ "\",\"scope\":\"" + incontactAthctn.getScope() + "\"}";

			URL tokenURL = new URL(endpoint);

			HttpsURLConnection connection;

			connection = (HttpsURLConnection) tokenURL.openConnection();

			connection.setRequestMethod("POST");

			connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			connection.setRequestProperty("Authorization", "basic " + encodedAuthToken);
			connection.setRequestProperty("Content-Length", Integer.toString(postData.toString().length()));
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os, "UTF-8");
			outputStreamWriter.write(postData.toString());
			outputStreamWriter.flush();
			outputStreamWriter.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			connection.disconnect();

			JSONObject jsonObject = new JSONObject(response.toString());

			tokenResponse.setAccessToken(jsonObject.getString("access_token"));
			tokenResponse.setResourceServerBaseUri(jsonObject.getString("resource_server_base_uri"));
			tokenResponse.setTokenType(jsonObject.getString("token_type"));
			tokenResponse.setExpiresIn(jsonObject.getInt("expires_in"));
			tokenResponse.setRefreshToken(jsonObject.getString("refresh_token"));
			tokenResponse.setScope(jsonObject.getString("scope"));
			tokenResponse.setRefreshTokenServerUri(jsonObject.getString("refresh_token_server_uri"));
			tokenResponse.setTeamId(jsonObject.getInt("team_id"));
			tokenResponse.setAgentId(jsonObject.getInt("agent_id"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw e;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw e;
		} catch (ProtocolException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} catch (JSONException e) {
			e.printStackTrace();
			throw e;
		}
		return tokenResponse;

	}

	public String getInteractionHistory(TokenResponse tokenResponse, String agentId, String startDate, String endDate)  throws IOException {
		StringBuffer response = null;
		if (!tokenResponse.getAccessToken().isEmpty()) {
			String apiURL = "services/v3.0/agents/" + agentId + "/interaction-history?startDate="+startDate+"&endDate="+endDate;// &mediaTypeName=Phone%20Call
			String endpoint = tokenResponse.getResourceServerBaseUri() + apiURL;
			try {
				URL agentResourceURL = new URL(endpoint);
				HttpsURLConnection connection = (HttpsURLConnection) agentResourceURL.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				connection.setRequestProperty("Authorization", "bearer " + tokenResponse.getAccessToken());
				connection.setDoOutput(true);
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				connection.disconnect();
			}

			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw e;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw e;
			} catch (ProtocolException e) {
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}

		else {
			System.out.println("obtain a token");
		}
		return response.toString();
	}

	@Override
	public void setIncontactAthctn(IncontactAthctn incontactAthctn) {
		this.incontactAthctn = incontactAthctn;
	}

	public String getAgentIdStates(TokenResponse tokenResponse, String startDate, String endDate) {
		StringBuffer response = null;
		if (!tokenResponse.getAccessToken().isEmpty()) {
			String apiURL = "services/v3.0/agents/states?updatedSince=" + startDate;
			String endpoint = tokenResponse.getResourceServerBaseUri() + apiURL;
			try {
				URL agentResourceURL = new URL(endpoint);

				HttpsURLConnection connection = (HttpsURLConnection) agentResourceURL.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				connection.setRequestProperty("Authorization", "bearer " + tokenResponse.getAccessToken());

				connection.setDoOutput(true);
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				connection.disconnect();
			}

			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else {
			System.out.println("obtain a token");
		}
		return response.toString();
	}
	
	
	public String getAgentIdStatesByAgent(TokenResponse tokenResponse,  String agentId, String startDate) {
		StringBuffer response = null;
		if (!tokenResponse.getAccessToken().isEmpty()) {
			String apiURL = "services/v3.0/agents/" + agentId + "/states?updatedSince=" + startDate;
			String endpoint = tokenResponse.getResourceServerBaseUri() + apiURL;
			try {
				URL agentResourceURL = new URL(endpoint);

				HttpsURLConnection connection = (HttpsURLConnection) agentResourceURL.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				connection.setRequestProperty("Authorization", "bearer " + tokenResponse.getAccessToken());

				connection.setDoOutput(true);
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				connection.disconnect();
			}

			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else {
			System.out.println("obtain a token");
		}
		return response.toString();
	}
	
	public String getAgentStateHistoryByAgent(TokenResponse tokenResponse,  String agentId, String startDate, String endDate) {
		StringBuffer response = null;
		if (!tokenResponse.getAccessToken().isEmpty()) {
			String apiURL = "services/v3.0/agents/" + agentId + "/statehistory?startDate=" + startDate + "&endDate=" + endDate;
			String endpoint = tokenResponse.getResourceServerBaseUri() + apiURL;
			try {
				URL agentResourceURL = new URL(endpoint);

				HttpsURLConnection connection = (HttpsURLConnection) agentResourceURL.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				connection.setRequestProperty("Authorization", "bearer " + tokenResponse.getAccessToken());

				connection.setDoOutput(true);
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				connection.disconnect();
			}

			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (ProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		else {
			System.out.println("obtain a token");
		}
		return response.toString();
	}	
	public String getPerformance(TokenResponse tokenResponse, String agentId, String startDate, String endDate) throws IOException {
		StringBuffer response = null;
		if (!tokenResponse.getAccessToken().isEmpty()) {
			String apiURL = "services/v3.0/agents/" + agentId + "/performance?startDate=" + startDate + "&endDate=" + endDate;// &mediaTypeName=Phone%20Call
			String endpoint = tokenResponse.getResourceServerBaseUri() + apiURL;
			try {
				URL agentResourceURL = new URL(endpoint);
				HttpsURLConnection connection = (HttpsURLConnection) agentResourceURL.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				connection.setRequestProperty("Authorization", "bearer " + tokenResponse.getAccessToken());
				connection.setDoOutput(true);
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				connection.disconnect();
			}

			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw e;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw e;
			} catch (ProtocolException e) {
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}

		else {
			System.out.println("obtain a token");
		}
		return response.toString();
	}
	

	public String getSkillsSummary(TokenResponse tokenResponse, String startDate, String endDate) throws IOException {
		StringBuffer response = null;
		if (!tokenResponse.getAccessToken().isEmpty()) {
			String apiURL = "services/v3.0/skills/summary?startDate=" + startDate + "&endDate=" + endDate;
			String endpoint = tokenResponse.getResourceServerBaseUri() + apiURL;
			try {
				URL agentResourceURL = new URL(endpoint);
				HttpsURLConnection connection = (HttpsURLConnection) agentResourceURL.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				connection.setRequestProperty("Authorization", "bearer " + tokenResponse.getAccessToken());
				connection.setDoOutput(true);
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				connection.disconnect();
			}

			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw e;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw e;
			} catch (ProtocolException e) {
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}

		else {
			System.out.println("obtain a token");
		}
		return response.toString();
	}
	
	public String getContactsStates (TokenResponse tokenResponse, String agentId, String updateSince) throws IOException {
		StringBuffer response = null;
		if (!tokenResponse.getAccessToken().isEmpty()) {
			String apiURL = "services/v3.0/contacts/states?agentId=" + agentId + "&updateSince="+updateSince;
			String endpoint = tokenResponse.getResourceServerBaseUri() + apiURL;
			try {
				URL agentResourceURL = new URL(endpoint);
				HttpsURLConnection connection = (HttpsURLConnection) agentResourceURL.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
				connection.setRequestProperty("Authorization", "bearer " + tokenResponse.getAccessToken());
				connection.setDoOutput(true);
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				connection.disconnect();
			}
			catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw e;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw e;
			} catch (ProtocolException e) {
				e.printStackTrace();
				throw e;
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
		else {
			System.out.println("obtain a token");
		}
		return response.toString();
	}
}
