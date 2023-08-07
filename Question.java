package onlineTest;

import java.io.*;

public abstract class Question implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int questionNumber;
	protected String text;
	protected double points;
	public Question(int questionNumber, String text, double points) {
		this.questionNumber = questionNumber;
		this.points = points;
		this.text = text;
	}

	protected abstract double gradeAnswer(Question question);
	
	protected abstract String getAnswer();
	
}
