package com.andrew.SRV;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.andrew.db.DBUtil;
import com.andrew.model.Game;
import com.andrew.model.Question;
import com.andrew.model.Team;

public class DALManager {
	static Team aTeam = new Team();
	public static void saveQuestion(Question question, String process) {
		// deletes, adds, or edits the question base on the String process
		Connection connection = DBUtil.getDbConnection();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		DateTimeFormatter yr = DateTimeFormatter.ofPattern("yyyy");
		LocalDateTime now = LocalDateTime.now(); 
		if (process.equalsIgnoreCase("add")) {
			if (connection != null) {
				try {
					Statement statement = connection.createStatement();
					String sql = "insert into question(question_id, question_description, question_choiceA, question_choiceB, question_choiceC, question_choiceD, correct_answer, time_limit, explaination, last_update_time, year, section_number, showAnswer, ID)values('"+question.getQuestionOrder()+"','"+question.getQuestion()+"','"+question.getAnswerchoiceA()+"','"+question.getAnswerchoiceB()+"','"+question.getAnswerchoiceC()+"','"+question.getAnswerchoiceD()+"','"+question.getCorrectAnswer()+"','"+question.getTimelimit()+"','"+question.getExplaination()+"','"+dtf.format(now)+"','"+yr.format(now)+"','"+question.getTimelimit()+"','"+0+"','"+question.getQuestionID()+"')";
					int rlt = statement.executeUpdate(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		}
		else if (process.equalsIgnoreCase("delete")) {
			System.out.println(question.getQuestionID());
			if (connection != null) {
				try {
					String sql = "delete from question where ID=?";
					PreparedStatement ps = connection.prepareStatement(sql);
					ps.setDouble(1, question.getQuestionID());
					int rlt = ps.executeUpdate();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
		}
		else if (process.equalsIgnoreCase("edit")) {
			try {
				String sql = "UPDATE question SET question_description=?, question_choiceA=?, question_choiceB=?, question_choiceC=?, question_choiceD=?, correct_answer='"+question.getCorrectAnswer()+"', time_limit=?, explaination=?, year=?, Last_update_time=?, showAnswer="+0+", question_id=?"+" WHERE ID=?";
				PreparedStatement ps = connection.prepareStatement(sql);
				System.out.println("updating");
				ps.setString(1, question.getQuestion());
				ps.setString(2, question.getAnswerchoiceA());
				ps.setString(3, question.getAnswerchoiceB());
				ps.setString(4, question.getAnswerchoiceC());
				ps.setString(5, question.getAnswerchoiceD());
				ps.setInt(6, question.getTimelimit());
				ps.setString(7, question.getExplaination());
				ps.setString(8, yr.format(now));
				ps.setString(9, dtf.format(now));
				ps.setInt(10, question.getQuestionOrder());
				ps.setDouble(11, question.getQuestionID());
				int rlt = ps.executeUpdate();
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					try {
						connection.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static Question getQuestionByID(int num) {
		// gets the quesiton the quiz is currently on
		Question aquestion = new Question();
		DateTimeFormatter yr = DateTimeFormatter.ofPattern("yyyy");
		LocalDateTime now = LocalDateTime.now();
		String current = yr.format(now);
		Connection connection = DBUtil.getDbConnection();
		System.out.println("int num="+num);
		if(connection!=null) {
			try {
				String sql = "SELECT * FROM question WHERE question_id="+num+" and year="+current;
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rlt = ps.executeQuery();
				while(rlt.next()) {
					String desc = rlt.getString("question_description");
					aquestion.setQuestionID(num);
					aquestion.setQuestion(desc);
					aquestion.setAnswerchoiceA(rlt.getString("question_choiceA"));
					aquestion.setAnswerchoiceB(rlt.getString("question_choiceB"));
					aquestion.setAnswerchoiceC(rlt.getString("question_choiceC"));
					aquestion.setAnswerchoiceD(rlt.getString("question_choiceD"));
					aquestion.setCorrectAnswer(rlt.getString("correct_answer").charAt(0));
					aquestion.setExplaination(rlt.getString("explaination"));
					aquestion.setShowExplaination(rlt.getInt("showAnswer"));
					aquestion.setTimelimit(rlt.getInt("time_limit"));
					break;
				}
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
					}
				}
			}	
		}
		return aquestion;
	}
	
	public static List<Question> loadQuestions() {	// returns list of an arraylist of the object Question
		List<Question> list = new ArrayList<>(); 
		DateTimeFormatter yr = DateTimeFormatter.ofPattern("yyyy");
		LocalDateTime now = LocalDateTime.now();
		String current = yr.format(now);
		Connection connection = DBUtil.getConnection(); // establishes connection with database
		if(connection!=null) { // if connection is successful
			try {
				String sql = "SELECT * FROM question WHERE year="+current; // Select everything from the 'question' table for this year
				PreparedStatement ps;
				ps = connection.prepareStatement(sql);
				ResultSet rlt = ps.executeQuery(); 
				while(rlt.next()) {  // whilst there are more questions
					Question aquestion = new Question();
					aquestion.setQuestionID(rlt.getDouble("ID"));
					aquestion.setQuestionOrder(rlt.getInt("question_id"));
					aquestion.setQuestion(rlt.getString("question_description"));
					aquestion.setAnswerchoiceA(rlt.getString("question_choiceA"));
					aquestion.setAnswerchoiceB(rlt.getString("question_choiceB"));
					aquestion.setAnswerchoiceC(rlt.getString("question_choiceC"));
					aquestion.setAnswerchoiceD(rlt.getString("question_choiceD"));
					aquestion.setCorrectAnswer(rlt.getString("correct_answer").charAt(0));
					aquestion.setTimelimit(rlt.getInt("time_limit"));
					aquestion.setExplaination(rlt.getString("explaination"));
					list.add(aquestion);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					DBUtil.closeConnection(); // lastly, close connection with database
				}
			}	
		}
		return list; 
	}
	
	public static List<Question> loadQuestionsFromPast() {	// returns list of an arraylist of the object Question
		List<Question> list = new ArrayList<>(); 
		Connection connection = DBUtil.getConnection(); // establishes connection with database
		if(connection!=null) { // if connection is successful
			try {
				String sql = "SELECT * FROM question"; // Select everything from the 'question' table
				PreparedStatement ps;
				ps = connection.prepareStatement(sql);
				ResultSet rlt = ps.executeQuery(); 
				while(rlt.next()) {  // whilst there are more questions
					Question aquestion = new Question();
					aquestion.setQuestionID(rlt.getInt("year"));
					aquestion.setQuestion(rlt.getString("question_description"));
					aquestion.setAnswerchoiceA(rlt.getString("question_choiceA"));
					aquestion.setAnswerchoiceB(rlt.getString("question_choiceB"));
					aquestion.setAnswerchoiceC(rlt.getString("question_choiceC"));
					aquestion.setAnswerchoiceD(rlt.getString("question_choiceD"));
					aquestion.setCorrectAnswer(rlt.getString("correct_answer").charAt(0));
					aquestion.setTimelimit(rlt.getInt("time_limit"));
					aquestion.setExplaination(rlt.getString("explaination"));
					list.add(aquestion);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					DBUtil.closeConnection(); // lastly, close connection with database
				}
			}	
		}
		return list; 
	}
	
	public static int loadGame() {
		Connection connection = DBUtil.getConnection();
		Game game = new Game(); // creates new Game object
		List<Game> list = new ArrayList<>(); 
		int id = GameManagement.generateGameCode(); // generates game code
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"); // gets current date and time
		DateTimeFormatter yr = DateTimeFormatter.ofPattern("yyyy"); // gets current year
		LocalDateTime now = LocalDateTime.now();
		String name = "TriviaNight"+yr.format(now);
		if(connection!=null) {
			try { // adds game data into database
					String sql = "insert into game(Game_ID, Game_Name, Start_Time, Game_Process)values('"+id+"','"+name+"','"+dtf.format(now)+"','"+0+"')";
					Statement statement = connection.createStatement();
					int rlt = statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					DBUtil.closeConnection();
				}
			}	
		}
		return id;
	}
	
	public static Game loadGameInfo(int id) {
		// returns the Game object of the current game
		Game aGame = new Game();
		Connection connection = DBUtil.getConnection();
		if(connection!=null) {
			try {
				String sql = "SELECT * FROM game WHERE Game_ID="+id;
				PreparedStatement ps = connection.prepareStatement(sql);
				ResultSet rlt = ps.executeQuery(); 
				aGame.setGameID(rlt.getInt("Game_ID"));
				aGame.setGameName(rlt.getString("Game_Name"));
				aGame.setGameStartTime(rlt.getString("Start_Time"));
				aGame.setHasGameStarted(rlt.getInt("Game_Process"));
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					DBUtil.closeConnection();
				}
			}	
		}
		return aGame;
	}
	
	public static int TeamEnter(String team, int gamecode) {
		// when a team joins the quiz, adds the team into database and edits the static team object
		Connection connection = DBUtil.getConnection();
		Random rnd = new Random();
		int n = 10000 + rnd.nextInt(900000);
		int teamid = gamecode+n;
		if(connection!=null) {
			try {
					Statement statement = connection.createStatement();
					Statement statement2 = connection.createStatement();
					String sql = "insert into Team(Team_ID, Team_Name)values('"+teamid+"','"+team+"')"; // adds team into
					String sql2 = "insert into Game_Team_bridge(Game_ID, Team_ID, Team_points)values('"+gamecode+"','"+teamid+"','"+0+"')";
					int rlt = statement.executeUpdate(sql);
					int rlt2 = statement.executeUpdate(sql2);
					aTeam.setTeam_ID(teamid);
					System.out.println("printed");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					DBUtil.closeConnection();
				}
			}	
		}
		return teamid;
	}
	
	public static boolean codecheck(int code) {
		// checks if the code is valid
		Connection connection = DBUtil.getConnection();
		if(connection!=null) {
			try {
				String sql = "SELECT * FROM Game";
				PreparedStatement ps;
				ps = connection.prepareStatement(sql);
				ResultSet rlt = ps.executeQuery(); 
				while(rlt.next()) {
					if (code == rlt.getInt("Game_ID")) {
						return true;
					}
				}
				return false;
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
	
	public static void getNextQuestion(int id) {
		// sets the game to the next question
		Connection connection = DBUtil.getDbConnection();
		if(connection!=null) {
			try {
					String sql = "UPDATE Game SET Game_Process=Game_Process+1 WHERE Game_ID="+id;
					PreparedStatement ps = connection.prepareStatement(sql);
					int rlt = ps.executeUpdate();
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
	}
	
	public static void getPrevQuestion(int id) {
		int q = getCurrentQuestion(findCurrentGame());
		Connection connection = DBUtil.getDbConnection();
		if(connection!=null) {
			try {
					String sql = "UPDATE Game SET Game_Process=Game_Process-1 WHERE Game_ID="+id;
					String sql2 =  "UPDATE question SET showAnswer=0 WHERE question_id="+q+"-1";
					PreparedStatement ps = connection.prepareStatement(sql);
					int rlt = ps.executeUpdate();
					PreparedStatement ps2 = connection.prepareStatement(sql2);
					int rlt2 = ps2.executeUpdate();
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
	}
	
	public static int getCurrentQuestion(int id) {
		// returns the current question the quiz is on
		int currentQuestion =-1;
		Connection connection = DBUtil.getDbConnection();
		if(connection!=null) {
			try {
					String sql = "SELECT Game_Process FROM Game WHERE Game_ID="+id;
					PreparedStatement ps = connection.prepareStatement(sql);
					ResultSet rlt = ps.executeQuery();
					while(rlt.next()) { 
						currentQuestion = rlt.getInt("Game_Process");
						break;
					}
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
					}
				}
			}	
		}
		return currentQuestion;
		
		
	}
	
	public static int findCurrentGame() {
		// finds the current active game
		int game = -1;
		Connection dbConnection = DBUtil.getDbConnection();
		if(dbConnection!=null) {
			try {
					String sql = "SELECT Game_ID FROM Game WHERE Game_Process>-1";
					PreparedStatement ps = dbConnection.prepareStatement(sql);
					ResultSet rlt = ps.executeQuery();
					while(rlt.next()) {
						game = rlt.getInt("Game_ID");
						break;
					}
					
					rlt.close();
					ps.close();					
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(dbConnection!=null) {
					try {
						dbConnection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}	
		}
		return game;
	}
	
	public static void endGame() {
		// ends the current game
		Connection connection = DBUtil.getDbConnection();
		System.out.println("code aquired");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		if(connection!=null) {
			try {
					String sql = "UPDATE Game SET Game_Process=-1, End_Time='"+dtf.format(now)+"'WHERE Game_Process!=-1";
					PreparedStatement ps = connection.prepareStatement(sql);
					int rlt= ps.executeUpdate();
					String sql2 = "UPDATE question SET showAnswer=0";
					PreparedStatement ps2 = connection.prepareStatement(sql2);
					int rlt2 = ps2.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(connection!=null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}	
		}
	}
	
	public static void saveTeamInfo(int team_ID, String team_Name) {
		aTeam.setTeam_ID(team_ID);
		aTeam.setTeam_Name(team_Name);
	}
	
	public static Team getTeamInfo() {
		return aTeam;
	}
	
	public static void showAnswer() {
		// shows answer to the current question
		int q = getCurrentQuestion(findCurrentGame());
		Connection connection = DBUtil.getConnection();
		System.out.println(q);
		if(connection!=null) {
			try {
					String sql = "UPDATE question SET showAnswer=1 WHERE question_id="+q;
					PreparedStatement ps = connection.prepareStatement(sql);
					int rlt = ps.executeUpdate();
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
	
	public static void addPoints() {
		// add points to the team for answering correctly
		int currentGame = findCurrentGame();
		Team team = DALManager.getTeamInfo();
		Connection connection = DBUtil.getConnection();
		if (connection!=null) {
			try {
				System.out.println("gamecode="+currentGame+", Teamcode="+team.getTeam_ID());
				String sql = "UPDATE Game_Team_bridge SET Team_points=Team_points+100 WHERE Game_ID="+currentGame+" and Team_ID="+team.getTeam_ID();
				PreparedStatement ps = connection.prepareStatement(sql);
				int rlt = ps.executeUpdate();
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
}


