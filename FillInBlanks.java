package onlineTest;

import java.util.Arrays;

public class FillInBlanks extends Question{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[] answer;
	public FillInBlanks(int questionNumber, String text, double points, String[] answer) {
		super(questionNumber, text, points);
		Arrays.sort(answer);
		this.answer = answer;
	}
	
	protected double gradeAnswer(Question question) {
		double points = 0.0;
		FillInBlanks q = (FillInBlanks)question;
		double addPoints = q.points/(q.answer.length);
		for (int i = 0; i < answer.length; i++) {
			for (int x = 0; x < q.answer.length; x++) {
				if (this.answer[i].equals(q.answer[x])) {
					points += addPoints;
				}
			}
		}
		return points;
	}
	
	protected String getAnswer() {
		String result = "[";
		for (int i = 0; i < answer.length - 1; i++) {
			result += answer[i] + ", ";
		}
		result += answer[answer.length - 1] + "]";
		return result;
	}
}
