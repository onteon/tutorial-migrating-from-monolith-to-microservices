package com.example.gateway.report.remote.interfaces;

import com.example.gateway.report.remote.message.CreateReportMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;

/**
 * @author Patryk Borchowiec
 */
public interface ReportRemoteService {
    void create(final @NonNull CreateReportMessage reportDTO) throws JsonProcessingException;
}
