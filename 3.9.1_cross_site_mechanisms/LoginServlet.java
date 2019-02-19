import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Java servlet for creating a session and attaching two attributes: a username
 * and a counter. Does not consider existing sessions.
 * 
 * @author Joseph Eichenhofer
 */
public class LoginServlet extends HttpServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 * 
	 * Create a session for the specified username (no checks of
	 * password/authentication). Does not consider existing sessions with same
	 * username. Initialize clicks to zero.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// check for username parameter
		String username = req.getParameter("username");
		if (username != null && !username.equals("")) {
			// request contained a username, set session attribute for username and
			// initialize click count to zero
			req.getSession(true).setAttribute("username", username);
			req.getSession().setAttribute("clicks", new Integer(0));zz
		}

		// redirect to main page
		res.sendRedirect("view");
	}
}