package com.example.report.remote.product.interfaces;

import com.example.report.remote.exception.RemoteServiceException;
import com.example.report.remote.product.response.ProductResponse;

import java.util.List;

/**
 * @author Patryk Borchowiec
 */
public interface ProductRemoteService {
    List<ProductResponse> getAll() throws RemoteServiceException;
}
