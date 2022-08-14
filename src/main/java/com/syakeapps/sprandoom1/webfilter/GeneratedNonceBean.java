package com.syakeapps.sprandoom1.webfilter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import lombok.Getter;

@RequestScoped
@Named("NONCE")
public class GeneratedNonceBean {
    @Getter(onMethod_ = { @lombok.Generated })
    private String nonce;

    @PostConstruct
    public void postConstruct() {
        nonce = String.valueOf(System.currentTimeMillis());
    }
}
