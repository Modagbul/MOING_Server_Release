package com.moing.backend.global.config.security.filter;

import com.moing.backend.global.config.security.jwt.JwtExceptionList;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 인증되지 않은 사용자가 보호된 리소스에 액세스 할 때 호출
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = String.valueOf(request.getAttribute("exception"));
        log.info("commonce:" + exception);

        if (exception.equals(JwtExceptionList.ADDITIONAL_REQUIRED_TOKEN.getErrorCode()))
            setResponse(response, JwtExceptionList.ADDITIONAL_REQUIRED_TOKEN);

        else if (exception.equals(JwtExceptionList.UNKNOWN_ERROR.getErrorCode()))
            setResponse(response, JwtExceptionList.UNKNOWN_ERROR);

        else if (exception.equals(JwtExceptionList.MAL_FORMED_TOKEN.getErrorCode()))
            setResponse(response, JwtExceptionList.MAL_FORMED_TOKEN);

        else if (exception.equals(JwtExceptionList.ILLEGAL_TOKEN.getErrorCode()))
            setResponse(response, JwtExceptionList.ILLEGAL_TOKEN);

        else if (exception.equals(JwtExceptionList.EXPIRED_TOKEN.getErrorCode()))
            setResponse(response, JwtExceptionList.EXPIRED_TOKEN);

        else if (exception.equals(JwtExceptionList.UNSUPPORTED_TOKEN.getErrorCode()))
            setResponse(response, JwtExceptionList.UNSUPPORTED_TOKEN);

        else setResponse(response, JwtExceptionList.ACCESS_DENIED);

    }

    private void setResponse(HttpServletResponse response, JwtExceptionList exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("isSuccess", false);
        responseJson.put("timestamp", LocalDateTime.now().withNano(0).toString());
        responseJson.put("errorCode", exceptionCode.getErrorCode());
        responseJson.put("message", exceptionCode.getMessage());

        response.getWriter().print(responseJson);
    }
}