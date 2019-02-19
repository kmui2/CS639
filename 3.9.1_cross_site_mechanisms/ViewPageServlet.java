import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Display either a form or the current number of registered clicks for a
 * session.
 * 
 * @author Joseph Eichenhofer
 */
public class ViewPageServlet extends HttpServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * Serve one of two "pages" showing either a login form (if no session is
	 * registered with the request) or the number of "clicks" that are associated
	 * with the session (if one exists).
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		PrintWriter content = res.getWriter();

		HttpSession session = req.getSession(false);

		// set encoding/type
		res.setContentType("text/html; charset=utf-8");
		// set good status code
		res.setStatus(HttpServletResponse.SC_OK);

		if (session == null) {
			// no session, show "login" form
			content.print("<h1>");
			content.print("Welcome! Type a username to login.");
			content.println("</h1>");

			// write out form for setting username
			content.println("<form action=\"login\" method=\"GET\">");
			content.println("username: <input type=\"text\" name=\"username\" /><br />");
			content.println("<input type=\"submit\" value=\"Submit\" />");
			content.println("</form>");
		} else {
			// session exists, show button clicker
			String username = (String) session.getAttribute("username");
			Integer clickCount = (Integer) session.getAttribute("clicks");

			content.print("<h1>");
			content.print("Welcome, ");
			content.print(username);
			content.print("!");
			content.println("</h1>");

			content.print("<p>");
			content.print("You've clicked ");
			content.print(clickCount);
			content.print(" times!");
			content.print("</p>");

			content.print("<a href=\"action\">CLICK</a>");
		}
	}
}