package com.syakeapps.sprandoom1.webfilter;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;

import com.google.common.net.HttpHeaders;

/**
 * CSRF対策のためのフィルタのはずだったが、BootsFacesのインラインスクリプトにNONCEを設定できないためお蔵入り
 */

@SuppressWarnings("serial")
// @WebFilter(urlPatterns = "*.faces")
public class ContentSecurityPolicyFilter extends HttpFilter {
    private static final String HEADER_VALUE = "script-src 'self' 'nonce-%s'; img-src * data:;";

    @Inject
    private GeneratedNonceBean nonceBean;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // NOP on request
        chain.doFilter(request, response);

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.setHeader(HttpHeaders.CONTENT_SECURITY_POLICY, String.format(HEADER_VALUE, nonceBean.getNonce()));
    }
}
