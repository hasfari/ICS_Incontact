package com.incontact.ws.pojo;


/**
 * @author HAsfari added on Nov 10, 2014
 *
 *
 * TokenResponse.java
 */

public class TokenResponse {
	private String accessToken;
	private String tokenType;
	private int expiresIn;
	private String refreshToken;
	private String scope;
	private String resourceServerBaseUri;
	private String refreshTokenServerUri;
	private int agentId;
	private int teamId;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getResourceServerBaseUri() {
		return resourceServerBaseUri;
	}

	public void setResourceServerBaseUri(String resourceServerBaseUri) {
		this.resourceServerBaseUri = resourceServerBaseUri;
	}

	public String getRefreshTokenServerUri() {
		return refreshTokenServerUri;
	}

	public void setRefreshTokenServerUri(String refreshTokenServerUri) {
		this.refreshTokenServerUri = refreshTokenServerUri;
	}

	public int getAgentId() {
		return agentId;
	}

	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

}