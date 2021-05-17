package com.andrew.SRV;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.andrew.db.DBUtil;
import com.andrew.model.Question;

public class QuestionManagement {
	
	public static void createQuestion(double qID, int id, String q, String a, String b, String c, String d, int time, char answer, String explaination) {
		// creates a question object with the information provided and is sent to be added in the database
		Question question = new Question();
		question.setQuestionID(qID);
		question.setQuestionOrder(id);
		question.setQuestion(q);
		question.setAnswerchoiceA(a);
		question.setAnswerchoiceB(b);
		question.setAnswerchoiceC(c);
		question.setAnswerchoiceD(d);
		question.setTimelimit(time);
		question.setCorrectAnswer(answer);
		question.setExplaination(explaination);
		System.out.println(b);
		DALManager.saveQuestion(question,"add");
		}

	public static void updateUser(double qID, int id, String q, String a, String b, String c, String d, int time, char answer, String explaination) {
		// updates a question object with the changes provided and it is sent to the updated in the database
		Question question = new Question();
		question.setQuestionID(qID);
		question.setQuestionOrder(id);
		question.setQuestion(q);
		question.setAnswerchoiceA(a);
		question.setAnswerchoiceB(b);
		question.setAnswerchoiceC(c);
		question.setAnswerchoiceD(d);
		question.setTimelimit(time);
		question.setCorrectAnswer(answer);
		question.setExplaination(explaination);
		System.out.print("HELPME");
		DALManager.saveQuestion(question, "edit");
	}

	public static void deleteQuestion(double id) {
		// deletes selected question
		DALManager manager = new DALManager();
		List<Question> list = manager.loadQuestions();	
		int i=0;
		while(list.get(i).getQuestionID()!=id) {
			i++;
		}
		DALManager.saveQuestion(list.get(i), "delete");
	}
	
	public static boolean validateGame() {
		// ensure that the orders of the question is correct and that there are not gaps or repeated values of the order
		List<Integer> order = new ArrayList();
		DateTimeFormatter yr = DateTimeFormatter.ofPattern("yyyy");
		LocalDateTime now = LocalDateTime.now();
		String current = yr.format(now);
		Connection connection = DBUtil.getConnection(); // establishes connection with database
		if(connection!=null) {
			try {
				String sql = "SELECT question_ID FROM question WHERE year="+current;
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rlt = ps.executeQuery(); 
				while (rlt.next()) {
					int i=rlt.getInt("question_ID");
					System.out.println(i);
					order.add(i);
				}
				Collections.sort(order);
				for (int j=0;j<order.size()-1;j++) {
					if (order.get(j)==order.get(j+1)||order.get(j)!=order.get(j+1)-1) {
						return false;
					}
				}
				return true;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					DBUtil.closeConnection();
				}
			}	
		}
		return false;
	}
	
	public static double getID(int order) {
		DateTimeFormatter yr = DateTimeFormatter.ofPattern("yyyy");
		LocalDateTime now = LocalDateTime.now();
		String current = yr.format(now);
		Connection connection = DBUtil.getConnection(); // establishes connection with database
		System.out.println(current);
		System.out.println(order);
		if(connection!=null) {
			try {
				String sql = "SELECT ID FROM question WHERE year="+current+" and question_id="+order;
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rlt = ps.executeQuery(); 
				return rlt.getDouble("ID");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					DBUtil.closeConnection();
				}
			}	
		}
		return 0;
	}
	
	public static int LastQ() {
		// loads questions from past years
		DateTimeFormatter yr = DateTimeFormatter.ofPattern("yyyy");
		LocalDateTime now = LocalDateTime.now();
		String current = yr.format(now);
		Connection connection = DBUtil.getDbConnection();; // establishes connection with database
		if(connection!=null) {
			try {
				String sql = "SELECT MAX(question_ID) FROM question WHERE year="+current;
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rlt = ps.executeQuery(); 
				return rlt.getInt("MAX(question_ID)");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					};
				}
			}	
		}
		return 1;
	}
	
	public static ArrayList<String> returnNames(ArrayList<Integer>list){
		// returns a list of names of the teams given their team ID
		ArrayList<String>Ranking = new ArrayList<String>();
		for (int i:list) {
			Connection connection = DBUtil.getConnection(); // establishes connection with database
			if(connection!=null) {
				try {
					String sql = "SELECT Team_Name FROM TEAM WHERE Team_ID="+i;
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rlt = ps.executeQuery(); 
					Ranking.add(rlt.getString("Team_Name"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if(connection!=null) {
						DBUtil.closeConnection();
					}
				}	
			}
		}
		return Ranking;
	}
}
