package come.andrew.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.andrew.SRV.DALManager;
import com.andrew.SRV.QuestionManagement;
import com.andrew.model.Question;
import com.google.gson.Gson;

/**
 * Servlet implementation class GameHolderServlet
 */
@WebServlet("/gameholder")
public class GameHolderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameHolderServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		Question aquestion = new Question();
		int id = DALManager.findCurrentGame(); // gets current question number
		int questionNum = DALManager.getCurrentQuestion(id); // gets last question number
		if (questionNum>QuestionManagement.LastQ()){  // if question number exceeds the last q
			DALManager.endGame(); // end game
		}
				aquestion = DALManager.getQuestionByID(questionNum);
				Gson agson = new Gson();
				String jsonObj = agson.toJson(aquestion);
				PrintWriter out = response.getWriter();
				out.print(jsonObj);
				out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("adding points");
		DALManager.addPoints(); // adds points if answered correctly
	}

}
