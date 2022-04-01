package com.example.product.remote.company.interfaces;

import com.example.product.remote.company.pojo.response.CompanyResponse;
import com.example.product.remote.exception.RemoteServiceException;
import lombok.NonNull;

/**
 * @author Patryk Borchowiec
 */
public interface CompanyRemoteService {
    CompanyResponse getById(final @NonNull Long id) throws RemoteServiceException;
}
