package onlineTest;

import java.io.*;
import java.util.ArrayList;

public class ExamResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int examID;
	ArrayList<Question> questions;
	public ExamResponse() {
		examID = 0;
		questions = new ArrayList<>();
	}
	
	public void addAnswer(Question question) {
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
	
	public void setID(int examID) {
		this.examID = examID;
	}
}
