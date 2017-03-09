package oauthServer.config;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.UnixCrypt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.foxconn.service.AccountService;

import oauthServer.service.OAuthService;
import static oauthServer.service.OAuthService.*;
import static oauthServer.util.OAuthConstants.*;

@Configuration
@ComponentScan(basePackages={"oauthServer.web","com.foxconn.service"})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter{
	
	@Autowired
	private OAuthService oauthService;
	
	private static Logger logger=LogManager.getLogger();

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		super.configureDefaultServletHandling(configurer);
		configurer.enable();
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// TODO Auto-generated method stub
		super.configureViewResolvers(registry);
		registry.viewResolver(new InternalResourceViewResolver());
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		
		HandlerInterceptor tokenInteceptor=new HandlerInterceptor() {
			
			@Override
			public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler)
					throws Exception {
				// TODO Auto-generated method stub
				
				// the import is  the access_token submit is not same token in reids.
				// it plus  with server_id:client_id:user_id
//				resp.setStatus(resp.SC_EXPECTATION_FAILED);
//				resp.setHeader(ERROR_DESCRIPTION, "test failure, not true!");
//				resp.getWriter().write("need token");
//				return false;
				
				String access_token=req.getHeader("access_token");

				logger.info("access_token:"+access_token);
				
				if(access_token!=null){
					int index=access_token.indexOf('#');
					String key_part=access_token.substring(0, index);
					String token_part=access_token.substring(index+1);
					
					Map<String, String> accessToken=oauthService.getAccessToken(key_part);

					//check if access token is expires
					if(accessToken!=null){
						
						
							// if yes, access_token is expired, please use refresh_token to get a new token
							long expiresAt=Long.parseLong(accessToken.get(REDIS_FIELD_EXPIRES_AT));
							Date expires_date=new Date(expiresAt);
							if(new Date().after(expires_date)){
								resp.getWriter().write("tokn expired! Please use refresh token to get new one");
								return false;
							}else{
								//  if not,  check authCode
								if(accessToken.get(REDIS_FIELD_TOKEN).equals(token_part)){
									//success , go deeper
									return true;
								}else{
									// not equal , FATAL!
									// may be algorithm changed, please test with re_authorize,
									//if still get this error , please contact server-admin —_>— 
									resp.getWriter().write( "FATAL! token content valid fail!");
									return false;
								}
							}

					}else{
						//token not exist! may be refresh token expires
						resp.getWriter().write( "token not found,may both token and refresh token expired!");
						return false;
					}
				}
				resp.getWriter().write( "neek token!");
				return false;
				
				
				
			}
			
			@Override
			public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
					ModelAndView modelAndView) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
					throws Exception {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		registry.addInterceptor(tokenInteceptor).addPathPatterns("/**");
		
		super.addInterceptors(registry);
	}


}
