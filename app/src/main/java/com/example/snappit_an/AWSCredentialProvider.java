package com.example.snappit_an;

import com.amazonaws.auth.AWSCredentials;

public class AWSCredentialProvider implements AWSCredentials {
    @Override
    public String getAWSAccessKeyId() {
        return "";
    }

    @Override
    public String getAWSSecretKey() {
        return "";
    }
}
