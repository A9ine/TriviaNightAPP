package come.andrew.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andrew.SRV.DALManager;
import com.andrew.SRV.GameManagement;
import com.andrew.TriviaAPP.ResponseUtil;
import com.andrew.model.Game;
import com.andrew.model.Question;
import com.andrew.model.Team;
import com.google.gson.Gson;

/**
 * Servlet implementation class GameServlet
 */
@WebServlet("/Gamedo")
public class GameServlet extends HttpServlet {
	static Team aTeam = new Team();
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		DALManager mal = new DALManager();
		Game game = mal.loadGameInfo(mal.loadGame()); // loads game
		ResponseUtil rsp = new ResponseUtil();
		rsp.setCode(200);
		rsp.setData(game);
		rsp.setMessage("sucess.");
		
		Gson agson = new Gson();
		String jsonObj = agson.toJson(rsp);
		PrintWriter out = response.getWriter();
		out.print(jsonObj);
		out.flush();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String team = request.getParameter("team");
		int game = Integer.parseInt(request.getParameter("code"));
		DALManager mal = new DALManager();
		if(mal.codecheck(game)) { // if gamecode is linked to an active game, the team is entered into the game  
			DALManager.saveTeamInfo(mal.TeamEnter(team, game), team);
			response.sendRedirect("ContestantPage.html");
		}
		else {
			response.sendRedirect("GameCodeError.html"); // else redirected to error page
		} 
	}

}
