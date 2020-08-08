package infofy;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import infofy.Users.User;

@WebServlet("/data")
public class DataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DataServlet() {
		super();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tokenCode = request.getParameter("code");
		//String tokenCode ="ASDf";
		RequestDispatcher dispatch = getServletContext().getRequestDispatcher("/data.html");
		if (tokenCode != null) {
			User user = new User();
			user.setAccessToken(tokenCode);
			SpotifyAPI.getAPI(user);
			request.setAttribute("user", user);
			System.out.println(user.currentlyPlaying);
		} 
		
		dispatch = getServletContext().getRequestDispatcher("/data.jsp");
		dispatch.forward(request, response);
	}
}
