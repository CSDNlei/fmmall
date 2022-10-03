package com.fengmi.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fengmi.famall.vo.ResStauts;
import com.fengmi.famall.vo.ResultVo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckTokenInceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
//         放行options请求
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }
        String token = request.getHeader("token");

        if (token == null) {
//              提示请先登录
            ResultVo resultVo = new ResultVo(ResStauts.LOGIN_FAIL_NOT, "请先登录！", null);
            doResponse(response, resultVo);
        } else {
            try {
//             验证token
                JwtParser parser = Jwts.parser();
//            解析token  必须和生成的token密码一致
                parser.setSigningKey("leilei66");
//            如果token正确（密码正确 有效期内）则正常执行 否则抛出异常
                parser.parseClaimsJws(token);
                return true;

            } catch (ExpiredJwtException e) {
                ResultVo resultVo = new ResultVo(ResStauts.LOGIN_FAIL_OVERDOE, "登录过期，请重新登录！", null);
                doResponse(response, resultVo);
            } catch (UnsupportedJwtException e) {
                ResultVo resultVo = new ResultVo(ResStauts.LOGIN_FAIL_WRONGFUL, "Token不合法！，请自重！", null);
                doResponse(response, resultVo);
            } catch (Exception e) {
                ResultVo resultVo = new ResultVo(ResStauts.LOGIN_FAIL_NOT, "请重新登录！", null);
                doResponse(response, resultVo);
            }

        }

        return false;

    }

    private void doResponse(HttpServletResponse response, ResultVo resultVo) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVo);
        writer.print(s);
        writer.flush();
        writer.close();
    }
}
