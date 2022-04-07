package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(final RouteLocatorBuilder builder) {
        final String innerEdgeAddress = "http://localhost:8021";

        return builder.routes()
                .route("product_api", r -> r.path("/api/v1/products")
                        .filters(f -> f.rewritePath(
                                "/api/v1/products",
                                "/_by_name/product/api/v1/products"
                        ))
                        .uri(innerEdgeAddress))
                .route("product_rewrite_api", r -> r.path("/api/v1/products/**")
                        .filters(f -> f.rewritePath(
                                "/api/v1/products/(?<segment>.*)",
                                "/_by_name/product/api/v1/products/${segment}"
                        ))
                        .uri(innerEdgeAddress))
                .route("company_api", r -> r.path("/api/v1/companies")
                        .filters(f -> f.rewritePath(
                                "/api/v1/companies",
                                "/_by_name/company/api/v1/companies"
                        ))
                        .uri(innerEdgeAddress))
                .route("company_rewrite_api", r -> r.path("/api/v1/companies/**")
                        .filters(f -> f.rewritePath(
                                "/api/v1/companies/(?<segment>.*)",
                                "/_by_name/company/api/v1/companies/${segment}"
                        ))
                        .uri(innerEdgeAddress))
                .route("create_report_api", r -> r.method(HttpMethod.POST).and().path("/api/v1/reports")
                        .filters(f -> f.rewritePath(
                                "/api/v1/reports",
                                "/_by_name/gateway/reports"
                        ))
                        .uri(innerEdgeAddress))
                .route("report_api", r -> r.method(HttpMethod.POST).negate().and().path("/api/v1/reports")
                        .filters(f -> f.rewritePath(
                                "/api/v1/reports",
                                "/_by_name/report/api/v1/reports"
                        ))
                        .uri(innerEdgeAddress))
                .route("report_rewrite_api", r -> r.path("/api/v1/reports/*/**")
                        .filters(f -> f.rewritePath(
                                "/api/v1/reports/(?<segment>.*)",
                                "/_by_name/report/api/v1/reports/${segment}"
                        ))
                        .uri(innerEdgeAddress))
                .build();
    }
}
