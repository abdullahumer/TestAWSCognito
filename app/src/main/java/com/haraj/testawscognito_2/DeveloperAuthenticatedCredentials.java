package com.haraj.testawscognito_2;

import com.amazonaws.auth.AWSIdentityProvider;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.auth.IdentityChangedListener;
import com.amazonaws.regions.Regions;

import java.util.Map;

public class DeveloperAuthenticatedCredentials extends CognitoCredentialsProvider {

    public DeveloperAuthenticatedCredentials(String identityPoolId, Regions region) {
        super(identityPoolId, region);
    }


    @Override
    public void registerIdentityChangedListener(IdentityChangedListener listener) {
        super.registerIdentityChangedListener(listener);
    }

    @Override
    public String getToken() {
        return "eyJraWQiOiJldS13ZXN0LTExIiwidHlwIjoiSldTIiwiYWxnIjoiUlM1MTIifQ.eyJzdWIiOiJldS13ZXN0LTE6MjkyM2IxYWQtNGNiYS00ZTFmLWEyY2YtZGIyNDVmM2Q3NWJiIiwiYXVkIjoiZXUtd2VzdC0xOjI4OWZkNGEwLTIyMzYtNGZmNC05YzJiLTYxYzkzZTYwYmYwYSIsImFtciI6WyJhdXRoZW50aWNhdGVkIiwiZ3JhcGhxbC5oYXJhaiIsImdyYXBocWwuaGFyYWo6ZXUtd2VzdC0xOjI4OWZkNGEwLTIyMzYtNGZmNC05YzJiLTYxYzkzZTYwYmYwYTo1NjI4NDAiXSwiaXNzIjoiaHR0cHM6Ly9jb2duaXRvLWlkZW50aXR5LmFtYXpvbmF3cy5jb20iLCJleHAiOjE1NDUxNDgzMTYsImlhdCI6MTU0NTA2MTkxNn0.V-iZeAQdsdMb9LkzYNucka5PEYRMBKKTGm5CzZIJYg8Z5ehcq562JbXGJWr7Yea-w2APsbpVxgP8EjHxSLjsMggk2FdVd-m8YhNFwBYL91oph-wFiAIxLVginD3t3_EhmkPduXZgM1mwH1_yNsGqpBY4nr15cgjqLvfyb4t-QJADFFyjd2qpIUoNzU2EQ5ypEKmbVdgOeLCIe6a-L09yzO-M1xdC0Onc8fs5ELOISR8FA5YFJYIgyqfSz9wDmz929rmCV9EjFdNC3Jd_hSC_Ofp6NYjiW1HRTU0a2C3Z3FCNJFzKppQSUt78MWrJblhHJSEboeMoKhzxmkA0VPgNjg";
    }

    @Override
    public String getIdentityId() {
        return "eu-west-1:2923b1ad-4cba-4e1f-a2cf-db245f3d75bb";
    }

    @Override
    public String getIdentityPoolId() {
        return "eu-west-1:289fd4a0-2236-4ff4-9c2b-61c93e60bf0a";
    }


    @Override
    public void setLogins(Map<String, String> logins) {
        super.setLogins(logins);
    }
}
