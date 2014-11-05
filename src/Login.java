import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, java.io.IOException {
		String username = req.getParameter("name");
		String password = req.getParameter("password");
		
		DatabaseHelper helper = new DatabaseHelper();
		boolean verified = helper.checkLogin(username, password);		
		String responseJson;
		if (verified) {
			MemberData memberData = new MemberData(username, helper);
			responseJson = memberData.getDataOfMemberAsJson(username);
		} else {
			responseJson = "{ \"message\": \"Incorrect user name and password combination. Check once\",\"status\": -1}";
		}	
		helper.close();
		
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.print(responseJson);
		out.flush();
	}
}
