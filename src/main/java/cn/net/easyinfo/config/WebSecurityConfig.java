package cn.net.easyinfo.config;

import cn.net.easyinfo.common.HrUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import cn.net.easyinfo.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    HrService hrService;
    @Autowired
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    @Autowired
    UrlAccessDecisionManager urlAccessDecisionManager;
    @Autowired
    AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("WebSecurityConfig: userDetailsService");
        auth.userDetailsService(hrService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/index.html", "/static/**", "/api/**", "/login_p", "/reportPic/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("WebSecurityConfig: authorizeRequests");
        http.authorizeRequests().withObjectPostProcessor(
                new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        //取得请求资源所需权限
                        o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                        //匹配用户拥有权限和请求权限
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }                   // 设置登陆页                成功页
                }
                ).and().formLogin().loginPage("/index.html").loginProcessingUrl("/login").
//                ).and().formLogin().loginPage("/login_p").loginProcessingUrl("/login").
                usernameParameter("username").passwordParameter("password").permitAll().failureHandler(
                        new AuthenticationFailureHandler() {
                            @Override
                            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                                httpServletResponse.setContentType("application/json;charset=utf-8");
                                PrintWriter out = httpServletResponse.getWriter();
                                StringBuffer sb = new StringBuffer();
                                sb.append("{\"status\":\"error\",\"msg\":\"");
                                if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                                    sb.append("用户名或密码输入错误，登录失败!");
                                } else if (e instanceof DisabledException) {
                                    sb.append("账户被禁用，登录失败，请联系管理员!...");
                                } else {
                                    sb.append("登录失败!");
                                }
                                sb.append("\"}");
                                out.write(sb.toString());
                                out.flush();
                                out.close();
                            }
                        }).successHandler(
                                new AuthenticationSuccessHandler() {
                                @Override
                                public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                                    httpServletResponse.setContentType("application/json;charset=utf-8");
                                    PrintWriter out = httpServletResponse.getWriter();
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    String s = "{\"status\":\"success\",\"msg\":" + objectMapper.writeValueAsString(HrUtils.getCurrentHr()) + "}";
                                    out.write(s);
                                    out.flush();
                                    out.close();
                                }
                            }).and().logout().permitAll().and().csrf().disable().exceptionHandling().
                            //权限不足处理程序
                            accessDeniedHandler(authenticationAccessDeniedHandler);
    }
}