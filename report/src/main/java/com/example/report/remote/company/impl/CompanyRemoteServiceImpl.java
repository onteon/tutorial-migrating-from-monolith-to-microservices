package com.example.report.remote.company.impl;

import com.example.report.remote.company.interfaces.CompanyRemoteService;
import com.example.report.remote.company.pojo.response.CompanyResponse;
import com.example.report.remote.company.pojo.response.ErrorResponse;
import com.example.report.remote.exception.RemoteServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Patryk Borchowiec
 */
@Service
public class CompanyRemoteServiceImpl implements CompanyRemoteService {
    final String BASE_URL = "http://localhost:8021/_by_name/company/api/v1/companies";
    final OkHttpClient client;
    final ObjectMapper objectMapper;

    public CompanyRemoteServiceImpl() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<CompanyResponse> getAll() {
        final Request request = new Request.Builder()
                .get()
                .url(BASE_URL)
                .build();

        final String responseBody = executeRequest(request);
        try {
            return objectMapper.readValue(responseBody, new TypeReference<>() {});
        } catch (final JsonProcessingException e) {
            throw new RemoteServiceException("Cannot parse response, due to: " + e.getMessage(), e);
        }
    }

    private String executeRequest(Request request) throws RemoteServiceException {
        final Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RemoteServiceException("Cannot get company by id, due to:" + e.getMessage(), e);
        }

        final String body;
        try {
            body = response.body().string();
        } catch (final IOException e) {
            throw new RemoteServiceException("Cannot get response body, due to:" + e.getMessage(), e);
        }

        if (!response.isSuccessful()) {
            throw new ResponseStatusException(HttpStatus.valueOf(response.code()), getErrorMessage(body));
        }

        return body;
    }

    private String getErrorMessage(final String responseBody) {
        final ErrorResponse errorResponse;
        try {
            errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
        } catch (JsonProcessingException e) {
            return "";
        }

        if (Objects.isNull(errorResponse) || Objects.isNull(errorResponse.getMessage())) {
            return "";
        }
        return errorResponse.getMessage();
    }
}
