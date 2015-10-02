package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import api.ret.obj.ErrMsg;
import api.ret.obj.RetCode;
import bll.HttpUtil;
import redis.clients.jedis.Jedis;

public class AuthFilter implements Filter{

	Jedis jedis = null;
	
	@Override
	public void init(FilterConfig config) {
    	jedis = new Jedis("localhost");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain chain) throws IOException, ServletException {
		String uid = request.getParameter("uid");
		String token = request.getParameter("token");
		
		request.setCharacterEncoding("UTF-8");
		System.out.println(request.getParameter("content"));
				
		if (token == null || token.isEmpty() || uid == null || uid.isEmpty()) {
			HttpUtil.errorRespond((HttpServletResponse)response, RetCode.BAD_REQUEST, ErrMsg.USER_NOT_LOGIN);
			return;
		}
		
		if (!jedis.isConnected()) {
			jedis.connect();
		}
		String authToken = jedis.get(uid);
		System.out.println("token = " + token + "\nauthToken = " + authToken);
		if (authToken == null || authToken.isEmpty() || !token.equals(authToken)) {
			HttpUtil.errorRespond((HttpServletResponse)response, RetCode.BAD_REQUEST, ErrMsg.USER_NOT_LOGIN);
		} else {
			chain.doFilter(request, response);
		}
	}
	
	@Override
	public void destroy() {
		if (jedis != null) {
    		jedis.close();
    	}
	}
}
