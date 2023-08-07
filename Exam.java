package onlineTest;

import java.io.*;
import java.util.ArrayList;

public class Exam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int examID;
	protected String examName;
	ArrayList<Question> questions;
	public Exam(int examID, String examName) {
		this.examID = examID;
		this.examName = examName;
		questions = new ArrayList<Question>();
	}
	
	public void addQuestion(Question question) {
		int count = 0;
		if (questions.size() == 0) {
			questions.add(question);
		} else {
			while(count < questions.size()) {
				if(question.questionNumber == questions.get(count).questionNumber) {
					questions.remove(count);
					questions.add(count, question);
					return;
				} else if(question.questionNumber < questions.get(count).questionNumber) {
					questions.add(count, question);
					return;
				}
				count++;
			}
			questions.add(question);
		}
	}
	
	public Question getQuestion(int i) {
		return questions.get(i);
	}

}
