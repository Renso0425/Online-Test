package onlineTest;

public class TrueFalse extends Question{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean answer;
	public TrueFalse(int questionNumber, String text, double points, boolean answer) {
		super(questionNumber, text, points);
		this.answer = answer;
	}
	
	protected double gradeAnswer(Question question) {
		double points = 0.0;
		TrueFalse q = (TrueFalse)question;
		if (this.answer == q.answer) {
			points = q.points;
		}
		return points;
	}
	
	protected String getAnswer() {
		String ans = "" + answer;
		String firstChar = ans.substring(0, 1).toUpperCase();
		return firstChar + ans.substring(1);
	}
	

}
