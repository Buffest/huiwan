package api.location;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import api.ret.obj.ErrMsg;
import api.ret.obj.PictureForMinisiteList;
import api.ret.obj.RetCode;
import bll.BizUtil;
import bll.HttpUtil;

/**
 * Servlet implementation class GetPictureForMinisiteByMinisiteId
 */
public class GetPictureForMinisiteByMinisiteId extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPictureForMinisiteByMinisiteId() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String minisiteIdStr = request.getParameter("minisiteId");
		
		if (minisiteIdStr == null || minisiteIdStr.isEmpty()) {
			HttpUtil.errorRespond(response, RetCode.BAD_REQUEST, 
					ErrMsg.MINISITE_ID_NULL);
			return;
		}
		
		long minisiteId = -1;
		try {
			minisiteId = Long.parseLong(minisiteIdStr);
		} catch (NumberFormatException e) {
			System.out.println("Error while parse " + minisiteIdStr + " to long.");
			HttpUtil.errorRespond(response, RetCode.BAD_REQUEST, ErrMsg.NUMBER_FORMAT_ERROR);
			return;
		}

		PictureForMinisiteList pictures = BizUtil.GetPictureForMinisiteByMinisiteId(minisiteId);
		HttpUtil.normalRespond(response, RetCode.SUCCESS, pictures);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
