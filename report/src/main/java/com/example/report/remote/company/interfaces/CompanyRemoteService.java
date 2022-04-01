package com.example.report.remote.company.interfaces;

import com.example.report.remote.company.pojo.response.CompanyResponse;
import com.example.report.remote.exception.RemoteServiceException;
import lombok.NonNull;

import java.util.List;

/**
 * @author Patryk Borchowiec
 */
public interface CompanyRemoteService {
    List<CompanyResponse> getAll() throws RemoteServiceException;
}
