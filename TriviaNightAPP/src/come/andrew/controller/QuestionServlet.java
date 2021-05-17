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
import com.andrew.SRV.QuestionManagement;
import com.andrew.TriviaAPP.ResponseUtil;
import com.andrew.model.Question;
import com.google.gson.Gson;

/**
 * Servlet implementation class QuestionServlet
 */
@WebServlet("/Questiondo")
public class QuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionServlet() {
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
		String act = request.getParameter("act");
		if ("past".equalsIgnoreCase(act)) {
			List<Question> listQuestion = manager.loadQuestionsFromPast();
			rsp.setCode(200); // HTTP status code - 200 = success
			rsp.setData(listQuestion); // Data in list put into responseUtil
			rsp.setMessage("sucess.");
		}
		else {
			List<Question> listQuestion = manager.loadQuestions(); // returns list of all questions in SQLite dationabase 
			rsp.setCode(200); // HTTP status code - 200 = success
			rsp.setData(listQuestion); // Data in list put into responseUtil
			rsp.setMessage("sucess.");
		}
		
		Gson agson = new Gson(); //creates new Gson object
		String jsonObj = agson.toJson(rsp); // changes Gson to Json
		PrintWriter out = response.getWriter();
		out.print(jsonObj);
		out.flush(); // printwriter gets sent
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String act = request.getParameter("act");
		if ("add".equalsIgnoreCase(act)) { // add new question
			int id = Integer.parseInt(request.getParameter("questionOrder"));
			double qID = Math.random() * (9999999 - 1000000) + 1000000;
			String question = request.getParameter("description");
			String choiceA = request.getParameter("choiceA");
			String choiceB = request.getParameter("choiceB");
			System.out.println("choiceB");
			String choiceC = request.getParameter("choiceC");
			String choiceD = request.getParameter("choiceD");
			int timelimit = Integer.parseInt(request.getParameter("time"));
			char correctChoice = request.getParameter("correctanswer").charAt(0);
			String explaination = request.getParameter("explaination");
			QuestionManagement.createQuestion(qID, id, question, choiceA, choiceB, choiceC, choiceD, timelimit, correctChoice, explaination);
		}else if("edit".equalsIgnoreCase(act)){ // edit existing question
			int id = Integer.parseInt(request.getParameter("questionOrder"));
			double qID = Double.parseDouble(request.getParameter("id"));
			System.out.println(qID);
			String question = request.getParameter("description");
			String choiceA = request.getParameter("choiceA");
			String choiceB = request.getParameter("choiceB");
			String choiceC = request.getParameter("choiceC");
			String choiceD = request.getParameter("choiceD");
			int timelimit = Integer.parseInt(request.getParameter("time"));
			char correctChoice = request.getParameter("correctanswer").charAt(0);
			String explaination = request.getParameter("explaination");
			QuestionManagement.updateUser(qID, id, question, choiceA, choiceB, choiceC, choiceD, timelimit, correctChoice, explaination);
		}else if("delete".equalsIgnoreCase(act)) { // delete existing question
			System.out.println(request.getParameter("questionOrder"));
			double id = Double.parseDouble(request.getParameter("questionOrder"));
			QuestionManagement.deleteQuestion(id);
		}
		response.sendRedirect("Question.html"); // refreshes page
		//doGet(request, response);
	}

}
