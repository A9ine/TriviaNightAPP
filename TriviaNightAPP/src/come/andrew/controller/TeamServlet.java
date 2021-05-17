package come.andrew.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andrew.SRV.DALManager;
import com.andrew.SRV.QuestionManagement;
import com.andrew.SRV.TeamManagement;
import com.andrew.TriviaAPP.ResponseUtil;
import com.andrew.model.Game;
import com.andrew.model.Question;
import com.andrew.model.Team;
import com.google.gson.Gson;

/**
 * Servlet implementation class TeamServlet
 */
@WebServlet("/loadTeam")
public class TeamServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeamServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		ResponseUtil rsp = new ResponseUtil();
		DALManager manager = new DALManager();
		ArrayList<ArrayList<Integer>> rank = TeamManagement.displayRanking(); 
		ArrayList<String> teams=TeamManagement.returnNames(rank.get(0));
		ArrayList<ArrayList<Object>> ranking = new ArrayList<ArrayList<Object>>(); // arraylist of object arraylitss to store both String and int
		ArrayList<Object> team = new ArrayList<Object>();
		ArrayList<Object> points = new ArrayList<Object>();
		for (String i:teams) {
			team.add(i);
		}
		for (int i:rank.get(1)) {
			points.add(i);
		}
		ranking.add(team);
		ranking.add(points);
	
		rsp.setCode(200); // HTTP status code - 200 = success
		rsp.setData(ranking); // Data in list put into responseUtil
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
		DALManager.addPoints(); // adds points
		//doGet(request, response);
	}

}
