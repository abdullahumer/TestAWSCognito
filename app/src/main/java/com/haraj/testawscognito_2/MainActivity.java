package com.haraj.testawscognito_2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdResult;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenRequest;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenResult;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AWSMobileClient.getInstance().initialize(this).execute();

        getS3Client();

        getS3Client1();
    }

    private void getS3Client1() {

        new Thread(new Runnable(){
            @Override
            public void run() {
        String token = "eyJraWQiOiJldS13ZXN0LTExIiwidHlwIjoiSldTIiwiYWxnIjoiUlM1MTIifQ.eyJzdWIiOiJldS13ZXN0LTE6MjkyM2IxYWQtNGNiYS00ZTFmLWEyY2YtZGIyNDVmM2Q3NWJiIiwiYXVkIjoiZXUtd2VzdC0xOjI4OWZkNGEwLTIyMzYtNGZmNC05YzJiLTYxYzkzZTYwYmYwYSIsImFtciI6WyJhdXRoZW50aWNhdGVkIiwiZ3JhcGhxbC5oYXJhaiIsImdyYXBocWwuaGFyYWo6ZXUtd2VzdC0xOjI4OWZkNGEwLTIyMzYtNGZmNC05YzJiLTYxYzkzZTYwYmYwYTo1NjI4NDAiXSwiaXNzIjoiaHR0cHM6Ly9jb2duaXRvLWlkZW50aXR5LmFtYXpvbmF3cy5jb20iLCJleHAiOjE1NDUxNDgzMTYsImlhdCI6MTU0NTA2MTkxNn0.V-iZeAQdsdMb9LkzYNucka5PEYRMBKKTGm5CzZIJYg8Z5ehcq562JbXGJWr7Yea-w2APsbpVxgP8EjHxSLjsMggk2FdVd-m8YhNFwBYL91oph-wFiAIxLVginD3t3_EhmkPduXZgM1mwH1_yNsGqpBY4nr15cgjqLvfyb4t-QJADFFyjd2qpIUoNzU2EQ5ypEKmbVdgOeLCIe6a-L09yzO-M1xdC0Onc8fs5ELOISR8FA5YFJYIgyqfSz9wDmz929rmCV9EjFdNC3Jd_hSC_Ofp6NYjiW1HRTU0a2C3Z3FCNJFzKppQSUt78MWrJblhHJSEboeMoKhzxmkA0VPgNjg";

        final String identityId = "eu-west-1:2923b1ad-4cba-4e1f-a2cf-db245f3d75bb";

        String identityPoolId = "eu-west-1:289fd4a0-2236-4ff4-9c2b-61c93e60bf0a";


        Map<String,String> logins = new HashMap<>();
        logins.put("cognito-identity.amazonaws.com", token);

        CognitoCachingCredentialsProvider cachingCredentialsProvider = new CognitoCachingCredentialsProvider(getApplicationContext(), identityPoolId, Regions.EU_WEST_1);
        cachingCredentialsProvider.setLogins(logins);

        GetCredentialsForIdentityRequest getCredentialsRequest =
                new GetCredentialsForIdentityRequest()
                        .withIdentityId(identityId)
                        .withLogins(logins);

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),"eu-west-1:289fd4a0-2236-4ff4-9c2b-61c93e60bf0a", Regions.EU_WEST_1);
        credentialsProvider.setLogins(logins);

        AmazonCognitoIdentityClient cognitoIdentityClient = new AmazonCognitoIdentityClient(cachingCredentialsProvider);
//        AmazonCognitoIdentityClient cognitoIdentityClient = new AmazonCognitoIdentityClient(awsCredentials);
        try {
            GetCredentialsForIdentityResult getCredentialsResult = cognitoIdentityClient.getCredentialsForIdentity(getCredentialsRequest);


            Credentials credentials = getCredentialsResult.getCredentials();
            AWSSessionCredentials sessionCredentials = new BasicSessionCredentials(
                    credentials.getAccessKeyId(),
                    credentials.getSecretKey(),
                    credentials.getSessionToken()
            );

            AmazonS3Client s3Client = new AmazonS3Client(sessionCredentials);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

            }
        }).start();
    }

    private void getS3Client() {
        new Thread(new Runnable(){
            @Override
            public void run() {
                String identityPoolId = "eu-west-1:289fd4a0-2236-4ff4-9c2b-61c93e60bf0a"; //eu-west-1:289fd4a0-2236-4ff4-9c2b-61c93e60bf0a

                AmazonS3Client testClient = null;
                try {

                    Map<String, String> loginsMap = new HashMap<>();
                    loginsMap.put("cognito-identity.amazonaws.com", "eyJraWQiOiJldS13ZXN0LTExIiwidHlwIjoiSldTIiwiYWxnIjoiUlM1MTIifQ.eyJzdWIiOiJldS13ZXN0LTE6MjkyM2IxYWQtNGNiYS00ZTFmLWEyY2YtZGIyNDVmM2Q3NWJiIiwiYXVkIjoiZXUtd2VzdC0xOjI4OWZkNGEwLTIyMzYtNGZmNC05YzJiLTYxYzkzZTYwYmYwYSIsImFtciI6WyJhdXRoZW50aWNhdGVkIiwiZ3JhcGhxbC5oYXJhaiIsImdyYXBocWwuaGFyYWo6ZXUtd2VzdC0xOjI4OWZkNGEwLTIyMzYtNGZmNC05YzJiLTYxYzkzZTYwYmYwYTo1NjI4NDAiXSwiaXNzIjoiaHR0cHM6Ly9jb2duaXRvLWlkZW50aXR5LmFtYXpvbmF3cy5jb20iLCJleHAiOjE1NDUxNDgzMTYsImlhdCI6MTU0NTA2MTkxNn0.V-iZeAQdsdMb9LkzYNucka5PEYRMBKKTGm5CzZIJYg8Z5ehcq562JbXGJWr7Yea-w2APsbpVxgP8EjHxSLjsMggk2FdVd-m8YhNFwBYL91oph-wFiAIxLVginD3t3_EhmkPduXZgM1mwH1_yNsGqpBY4nr15cgjqLvfyb4t-QJADFFyjd2qpIUoNzU2EQ5ypEKmbVdgOeLCIe6a-L09yzO-M1xdC0Onc8fs5ELOISR8FA5YFJYIgyqfSz9wDmz929rmCV9EjFdNC3Jd_hSC_Ofp6NYjiW1HRTU0a2C3Z3FCNJFzKppQSUt78MWrJblhHJSEboeMoKhzxmkA0VPgNjg");

                    DeveloperAuthenticatedCredentials credentials = new DeveloperAuthenticatedCredentials(identityPoolId, Regions.EU_WEST_1);
                    credentials.setLogins(loginsMap);

                    testClient = new AmazonS3Client(credentials);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (testClient != null) {
                    // test client is created
                    uploadImageWithClient(testClient);
                }

                String token = "eyJraWQiOiJldS13ZXN0LTExIiwidHlwIjoiSldTIiwiYWxnIjoiUlM1MTIifQ.eyJzdWIiOiJldS13ZXN0LTE6MjkyM2IxYWQtNGNiYS00ZTFmLWEyY2YtZGIyNDVmM2Q3NWJiIiwiYXVkIjoiZXUtd2VzdC0xOjI4OWZkNGEwLTIyMzYtNGZmNC05YzJiLTYxYzkzZTYwYmYwYSIsImFtciI6WyJhdXRoZW50aWNhdGVkIiwiZ3JhcGhxbC5oYXJhaiIsImdyYXBocWwuaGFyYWo6ZXUtd2VzdC0xOjI4OWZkNGEwLTIyMzYtNGZmNC05YzJiLTYxYzkzZTYwYmYwYTo1NjI4NDAiXSwiaXNzIjoiaHR0cHM6Ly9jb2duaXRvLWlkZW50aXR5LmFtYXpvbmF3cy5jb20iLCJleHAiOjE1NDUxNDgzMTYsImlhdCI6MTU0NTA2MTkxNn0.V-iZeAQdsdMb9LkzYNucka5PEYRMBKKTGm5CzZIJYg8Z5ehcq562JbXGJWr7Yea-w2APsbpVxgP8EjHxSLjsMggk2FdVd-m8YhNFwBYL91oph-wFiAIxLVginD3t3_EhmkPduXZgM1mwH1_yNsGqpBY4nr15cgjqLvfyb4t-QJADFFyjd2qpIUoNzU2EQ5ypEKmbVdgOeLCIe6a-L09yzO-M1xdC0Onc8fs5ELOISR8FA5YFJYIgyqfSz9wDmz929rmCV9EjFdNC3Jd_hSC_Ofp6NYjiW1HRTU0a2C3Z3FCNJFzKppQSUt78MWrJblhHJSEboeMoKhzxmkA0VPgNjg";

                final String identityId = "eu-west-1:2923b1ad-4cba-4e1f-a2cf-db245f3d75bb";

                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getApplicationContext(),
                        identityPoolId, // Identity pool ID
                        Regions.EU_WEST_1 // Region
                );

                Map<String, String> logins = new HashMap<>();
//                logins.put("login.haraj.graphql", token);
                logins.put("cognito-identity.amazonaws.com", token);

                GetCredentialsForIdentityRequest getCredentialsRequest =
                        new GetCredentialsForIdentityRequest()
                                .withIdentityId(identityId);

                credentialsProvider.setLogins(logins);

                Region region = Region.getRegion(Regions.EU_WEST_1);
                AmazonCognitoIdentityClient cognitoIdentityClient = new AmazonCognitoIdentityClient(credentialsProvider);
                cognitoIdentityClient.setRegion(region);

                try {
                    AWSSessionCredentials sessionCredentials = credentialsProvider.getCredentials();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    GetCredentialsForIdentityResult getCredentialsResult = cognitoIdentityClient.getCredentialsForIdentity(getCredentialsRequest);
                    Credentials credentials1 = getCredentialsResult.getCredentials();

                    AWSSessionCredentials sessionCredentials = new BasicSessionCredentials(
                            credentials1.getAccessKeyId(),
                            credentials1.getSecretKey(),
                            credentials1.getSessionToken()
                    );

                    AmazonS3Client s3Client = new AmazonS3Client(sessionCredentials);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    GetIdRequest getIdRequest = new GetIdRequest();
                    getIdRequest.setIdentityPoolId(identityPoolId);
                    getIdRequest.setLogins(logins);
                    GetIdResult result = cognitoIdentityClient.getId(getIdRequest);

                    Log.d("test", result.toString());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    GetOpenIdTokenRequest request = new GetOpenIdTokenRequest();
                    request.setIdentityId(identityId);
                    request.setLogins(logins);

                    GetOpenIdTokenResult result = cognitoIdentityClient.getOpenIdToken(request);
                    Log.d("test", result.toString());

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void uploadImageWithClient(AmazonS3Client testClient) {

        /*
        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(testClient)
                        .build();

        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "/" + fileName);

        TransferObserver uploadObserver =
                transferUtility.upload("Test_AU" + fileName, file);

        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed download.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                int percentDone = (int)percentDonef;
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

// If your upload does not trigger the onStateChanged method inside your
// TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
        }

        */
    }


}
