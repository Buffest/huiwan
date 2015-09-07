package api.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import api.ret.obj.ErrMsg;
import api.ret.obj.LoginInfo;
import api.ret.obj.RetCode;
import bll.BizUtil;
import bll.HttpUtil;

/**
 * Servlet implementation class UserLogin
 */
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Jedis jedis = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserLogin() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() {
    	jedis = new Jedis("localhost");
    }
    
    @Override
    public void destroy() {
    	if (jedis != null) {
    		jedis.close();
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String telephone = request.getParameter("telephone");
		String password = request.getParameter("password");
		
		if (telephone == null || telephone.isEmpty()) {
			HttpUtil.errorRespond(response, RetCode.BAD_REQUEST, 
					ErrMsg.TELEPHONE_NULL);
			return;
		}
		
		if (password == null || password.isEmpty()) {
			HttpUtil.errorRespond(response, RetCode.BAD_REQUEST, 
					ErrMsg.PASSWORD_NULL);
			return;
		}
		
		if (!BizUtil.checkUser(telephone)) {
			HttpUtil.errorRespond(response, RetCode.NOT_FOUND, 
					ErrMsg.USER_NOT_EXIST);
			return;
		}
		
		LoginInfo info = null;
		try {
			info = BizUtil.userLogin(telephone, password);
		} catch(Exception e) {
			e.printStackTrace();
			HttpUtil.errorRespond(response, RetCode.BAD_REQUEST, ErrMsg.LOGIN_ERROR);
			return;
		}
		
		if (info.getUid() < 0) {
			HttpUtil.errorRespond(response, RetCode.NOT_FOUND, ErrMsg.PASSWORD_NOT_CORRET);
		} else {
			HttpUtil.normalRespond(response, RetCode.SUCCESS, info);
			if(!jedis.isConnected()) {
				jedis.connect();
			}
			jedis.set(Long.toString(info.getUid()), info.getToken());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
