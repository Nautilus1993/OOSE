package com.team16.oose_project.http;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VerificationHttpResponseBuffer {
    private BufferedReader bufferedReader;
    private StringBuffer stringBuffer;

    public VerificationHttpResponseBuffer(HttpEntity httpEntity) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
        stringBuffer = new StringBuffer("");
    }

    public void readLine() throws IOException {
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }
        bufferedReader.close();
    }

    public String getBufferContent() {
        return stringBuffer.toString();
    }
}

