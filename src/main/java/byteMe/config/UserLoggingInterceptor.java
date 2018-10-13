package byteMe.config;

import byteMe.services.AuthService;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoggingInterceptor extends HandlerInterceptorAdapter {


    private final AuthService authService;

    public UserLoggingInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        request.getSession().setAttribute("loggedIn", authService.isAuthenticated());
        return true;
    }
}
