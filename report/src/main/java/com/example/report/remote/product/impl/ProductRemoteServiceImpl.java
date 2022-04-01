package com.example.report.remote.product.impl;

import com.example.report.remote.exception.RemoteServiceException;
import com.example.report.remote.product.interfaces.ProductRemoteService;
import com.example.report.remote.product.response.ProductResponse;
import com.example.report.remote.product.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
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
public class ProductRemoteServiceImpl implements ProductRemoteService {
    final String BASE_URL = "http://localhost:8021/_by_name/product/api/v1/products";
    final OkHttpClient client;
    final ObjectMapper objectMapper;

    public ProductRemoteServiceImpl() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<ProductResponse> getAll() {
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
