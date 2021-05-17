package come.andrew.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andrew.SRV.DALManager;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/Admindo")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet(/Admindo)
     */
    public AdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act = request.getParameter("action");
		System.out.println(act);
		if ("Next Question".equalsIgnoreCase(act)) { // if the next button is pressed
			DALManager.getNextQuestion(DALManager.findCurrentGame()); // moves to next q for the current game
			response.sendRedirect("admin.html"); // refreshes page
			
		}
		else if("End Game".equalsIgnoreCase(act)) { // if end game button is pressed
			response.sendRedirect("Leaderboard.html"); // redirects to the Leaderboard page
		}
		else if ("Show Explaination".equalsIgnoreCase(act)) { // if show explaination is pressed
			DALManager.showAnswer(); // shows answer
			response.sendRedirect("admin.html"); // refreshes page
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("user");
		String pwd = request.getParameter("password");
		int code = Integer.parseInt(request.getParameter("code"));
		if("interact".equals(username) && "god".equals(pwd)&& DALManager.codecheck(code)) { // if username and password are correct
			response.sendRedirect("admin.html"); // redirects to new page
		}else {
			response.sendRedirect("LoginPage.html"); // nothing happens
		} 
	}

}
