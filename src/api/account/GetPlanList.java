package api.account;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import api.ret.obj.ErrMsg;
import api.ret.obj.RetCode;
import api.ret.obj.SiteIdList;
import bll.BizUtil;
import bll.HttpUtil;

/**
 * Servlet implementation class GetPlanList
 */
public class GetPlanList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPlanList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uidStr = request.getParameter("uid");
		
		if (uidStr == null || uidStr.isEmpty()) {
			HttpUtil.errorRespond(response, RetCode.BAD_REQUEST, 
					ErrMsg.UID_NULL);
			return;
		}
		
		long uid = -1;
		try {
			uid = Long.parseLong(uidStr);
		} catch (NumberFormatException e) {
			System.out.println("Error while parse " + uidStr + " to long.");
			HttpUtil.errorRespond(response, RetCode.BAD_REQUEST, ErrMsg.NUMBER_FORMAT_ERROR);
			return;
		}
		
		SiteIdList siteIdList = BizUtil.getPlanList(uid);
		
		HttpUtil.normalRespond(response, RetCode.SUCCESS, siteIdList);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
