package onlineTest;

public class MultipleChoice extends Question{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String[] answer;
	public MultipleChoice(int questionNumber, String text, double points, String[] answer) {
		super(questionNumber, text, points);
		this.answer = answer;
	}
	
	protected double gradeAnswer(Question question) {
		double points = 0.0;
		MultipleChoice q = (MultipleChoice)question;
		if (this.getAnswer().compareTo(q.getAnswer()) == 0) {
			points += q.points;
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
