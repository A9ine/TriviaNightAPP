package com.andrew.model;

import com.andrew.SRV.DALManager;

public class Question {
	private double questionID;
	private String question;
	private String answerchoiceA;
	private String answerchoiceB;
	private String answerchoiceC;
	private String answerchoiceD;
	private char correctAnswer;
	private String explaination;
	private int timelimit;
	private int showExplaination;
	private int questionOrder;
	public int getQuestionOrder() {
		return questionOrder;
	}
	public void setQuestionOrder(int questionOrder) {
		this.questionOrder = questionOrder;
	}
	
	public int getShowExplaination() {
		return showExplaination;
	}
	public void setShowExplaination(int showExplaination) {
		this.showExplaination = showExplaination;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswerchoiceA() {
		return answerchoiceA;
	}
	public void setAnswerchoiceA(String answerchoiceA) {
		this.answerchoiceA = answerchoiceA;
	}
	public String getAnswerchoiceB() {
		return answerchoiceB;
	}
	public void setAnswerchoiceB(String answerchoiceB) {
		this.answerchoiceB = answerchoiceB;
	}
	public String getAnswerchoiceC() {
		return answerchoiceC;
	}
	public void setAnswerchoiceC(String answerchoiceC) {
		this.answerchoiceC = answerchoiceC;
	}
	public String getAnswerchoiceD() {
		return answerchoiceD;
	}
	public void setAnswerchoiceD(String answerchoiceD) {
		this.answerchoiceD = answerchoiceD;
	}
	public char getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(char correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public String getExplaination() {
		return explaination;
	}
	public void setExplaination(String explaination) {
		this.explaination = explaination;
	}
	public int getTimelimit() {
		return timelimit;
	}
	public void setTimelimit(int timelimit) {
		this.timelimit = timelimit;
	}
	public double getQuestionID() {
		return questionID;
	}
	public void setQuestionID(double questionID) {
		this.questionID = questionID;
	}
	public void showAnswer() {
		Question question = new Question();
		int id = DALManager.findCurrentGame();
		question =DALManager.getQuestionByID(DALManager.getCurrentQuestion(id));
		question.setQuestionID(1);
		DALManager.saveQuestion(question, "edit");
	}
}
