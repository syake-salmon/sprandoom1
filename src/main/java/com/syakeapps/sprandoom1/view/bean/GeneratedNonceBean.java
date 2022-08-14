package com.syakeapps.sprandoom1.view.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.google.common.net.HttpHeaders;

import lombok.Getter;

@RequestScoped
@Named("NONCE")
public class GeneratedNonceBean {
    private static final String NONCE_PREFIX = "nonce-";
    private static final int NONCE_LENGTH = 24;

    @Getter(onMethod_ = { @lombok.Generated })
    private String nonce;

    @PostConstruct
    public void postConstruct() {
        String headerVal = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap()
                .get(HttpHeaders.CONTENT_SECURITY_POLICY);

        if (headerVal != null && !headerVal.isEmpty()) {
            int nonceStartAt = headerVal.indexOf(NONCE_PREFIX) + NONCE_PREFIX.length();
            nonce = headerVal.substring(nonceStartAt, nonceStartAt + NONCE_LENGTH);
        }

        if (nonce == null || nonce.isEmpty()) {
            nonce = String.valueOf(System.currentTimeMillis());
        }
    }
}
