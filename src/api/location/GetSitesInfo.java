package api.location;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import api.ret.obj.ApiRet;
import api.ret.obj.ErrMsg;
import api.ret.obj.RetCode;
import api.ret.obj.SiteInfoList;
import api.ret.obj.UserInfo;
import bll.BizUtil;
import entity.Site;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetSiteInfo
 */
@WebServlet("/api/location/getSitesInfo")
public class GetSitesInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSitesInfo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String siteIdsStr = request.getParameter("siteIds");
		String[] siteIdStrArray = siteIdsStr.split(",");
		
		long[] siteIds = new long[siteIdStrArray.length];
		ApiRet ret = new ApiRet();
		
		for (int i = 0; i < siteIdStrArray.length; i++) {
			try {
				siteIds[i] = Long.parseLong(siteIdStrArray[i]);
			} catch (NumberFormatException e) {
				System.out.println("Error while parse " + siteIdStrArray[i] + " to long");
				ret.setCode(RetCode.BAD_REQUEST);
				ret.setData(new ErrMsg());
				JSONObject jsonObject = JSONObject.fromObject(ret);
				response.getWriter().append(jsonObject.toString());
				return;
			}
		}
		
		SiteInfoList siteInfoList = BizUtil.getSitesInfo(siteIds);
		
		if (siteInfoList != null) {
			ret.setCode(RetCode.SUCCESS);
			ret.setData(siteInfoList);
		} else {
			ret.setCode(RetCode.NOT_FOUND);
			ret.setData(new ErrMsg());
		}
		
		JSONObject jsonObject = JSONObject.fromObject(ret);
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.getWriter().append(jsonObject.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}