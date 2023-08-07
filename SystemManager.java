package onlineTest;

import java.io.*;
import java.util.*;


public class SystemManager implements Manager, Serializable {
	private static final long serialVersionUID = 1L;
	ArrayList<Exam> exams;
	ArrayList<Student> students;
	Map<Student, ArrayList<ExamResponse>> system;
	protected String[] letterGrades;
	protected double[] cutoffs;
	
	public SystemManager () {
		exams = new ArrayList<Exam>();
		students = new ArrayList<Student>();
		system = new HashMap<>(); 
		letterGrades = null;
		cutoffs = null;
	}

	@Override
	public boolean addExam(int examId, String title) {
		for (Exam currExam : exams) {
			if (currExam.examID == examId) {
				return false;
			}
		}
		exams.add(new Exam(examId, title));
		return true;
	}

	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		for (Exam currExam : exams) {
			if (currExam.examID == examId) {
				Question question = new TrueFalse(questionNumber, text, points, answer);
				currExam.addQuestion(question);
			}
		}

	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		for (Exam currExam : exams) {
			if (currExam.examID == examId) {
				Question question = new MultipleChoice(questionNumber, text, points, answer);
				currExam.addQuestion(question);
			}
		}

	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		for (Exam currExam : exams) {
			if (currExam.examID == examId) {
				Question question = new FillInBlanks(questionNumber, text, points, answer);
				currExam.addQuestion(question);
			}
		}

	}

	@Override
	public String getKey(int examId) {
		Exam exam = null;
		String key = "";
		for (Exam currExam : exams) {
			if (currExam.examID == examId) {
				exam = currExam;
			}
		}
		if (exam != null) {
			for (int i = 0; i < exam.questions.size(); i++) {
				key += "Question Text: " + exam.questions.get(i).text + "\nPoints: " + exam.questions.get(i).points + "\nCorrect Answer: " + exam.questions.get(i).getAnswer() + "\n";
			}
			return key;
		} else {
			return "Exam not found";
		}
	}

	@Override
	public boolean addStudent(String name) {	
		Set<Student> studentSet = system.keySet();
		for (Student student : studentSet) {
			if (student.name.compareTo(name) == 0) {
				return false;
			}
		}
		Student student = new Student(name);
		ArrayList<ExamResponse> exams = new ArrayList<ExamResponse>();
		system.put(student, exams);
		return true;
		
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		Student currStudent = findStudent(studentName);
		if (currStudent != null) {
			ExamResponse response = findExamResponse(examId, currStudent);
			if(response != null) {
				Question question = new TrueFalse(questionNumber, null, 0, answer);
				response.addAnswer(question);
			} else {
				ExamResponse newResponse = new ExamResponse();
				newResponse.setID(examId);
				Question question = new TrueFalse(questionNumber, null, 0, answer);
				newResponse.addAnswer(question);
				system.get(currStudent).add(newResponse);			
			}
		}
	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		Student currStudent = findStudent(studentName);
		if (currStudent != null) {
			ExamResponse response = findExamResponse(examId, currStudent);
			if(response != null) {
				Question question = new MultipleChoice(questionNumber, null, 0, answer);
				response.addAnswer(question);
			} else {
				ExamResponse newResponse = new ExamResponse();
				newResponse.setID(examId);
				Question question = new MultipleChoice(questionNumber, null, 0, answer);
				newResponse.addAnswer(question);
				system.get(currStudent).add(newResponse);			
			}
		}

	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		Student currStudent = findStudent(studentName);
		if (currStudent != null) {
			ExamResponse response = findExamResponse(examId, currStudent);
			if(response != null) {
				Question question = new FillInBlanks(questionNumber, null, 0, answer);
				response.addAnswer(question);
			} else {
				ExamResponse newResponse = new ExamResponse();
				newResponse.setID(examId);
				Question question = new FillInBlanks(questionNumber, null, 0, answer);
				newResponse.addAnswer(question);
				system.get(currStudent).add(newResponse);			
			}
		}

	}

	@Override
	public double getExamScore(String studentName, int examId) {
		double points = 0.0;
		Student student = findStudent(studentName);
		ExamResponse examResponse = findExamResponse(examId, student);
		Exam exam = findExam(examId);
		int numOfQuestions = 0;
		if (examResponse != null) {
			numOfQuestions = examResponse.questions.size();
		}
		for (int i = 0; i < numOfQuestions; i++) {
			points += examResponse.questions.get(i).gradeAnswer(exam.questions.get(i));
		}
		return points;
	}
	
	public double getTotalScore(Exam exam) {
		double score = 0.0;
		for(int i = 0; i < exam.questions.size(); i++) {
			score += exam.questions.get(i).points;
		}
		return score;
	}

	@Override
	public String getGradingReport(String studentName, int examId) {
		String report = "";
		int numOfQuestions = 0;
		Student student = findStudent(studentName);
		ExamResponse examResponse = findExamResponse(examId, student);
		Exam exam = findExam(examId);
		if (examResponse != null) {
			numOfQuestions = examResponse.questions.size();
		}
		for (int i = 0; i < numOfQuestions; i++) {
			double points = examResponse.questions.get(i).gradeAnswer(exam.questions.get(i));
			report += "Question #" + (i + 1) + " " + points + " points out of " + exam.questions.get(i).points + "\n";
		}
		report += "Final Score: " + getExamScore(studentName, examId) + " out of " + getTotalScore(exam);
		return report;
	}
	
	public Student findStudent(String studentName) {
		Student student = null;
		Set<Student> studentSet = system.keySet();
		for (Student stud : studentSet) {
			if (stud.name.compareTo(studentName) == 0) {
				student = stud;
			}
		}
		return student;
	}
	
	public ExamResponse findExamResponse(int examId, Student student) {
		ExamResponse examResponse = null;
		for (ExamResponse exResp : system.get(student)) {
			if (exResp.examID == examId) {
				examResponse = exResp;
			}
		}
		return examResponse;
	}
	
	public Exam findExam(int examId) {
		Exam exam = null;
		for (Exam ex : exams) {
			if (ex.examID == examId) {
				exam = ex;
			}
		}
		return exam;
	}

	@Override
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		this.letterGrades = letterGrades;
		this.cutoffs = cutoffs;

	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		double avg = 0.0;
		int numOfExams = 0;
		Student student = findStudent(studentName);
		for (ExamResponse exResp : system.get(student)) {
			numOfExams++;
			avg += (getExamScore(student.name, exResp.examID) / getTotalScore(findExam(exResp.examID))) * 100;
		}
		avg /= numOfExams;
		return avg;
	}

	@Override
	public String getCourseLetterGrade(String studentName) {
		double avg = getCourseNumericGrade(studentName);
		int index = -1;
		for (int i = 0; i < cutoffs.length; i++) {
			if (avg >= cutoffs[i]) {
				index = i;
				break;
			}
		}
		return letterGrades[index];
	}

	@Override
	public String getCourseGrades() {
		String result = "";
		ArrayList<Student> sortList = sortList();
		for (Student student : sortList) {
			result += student.name + " " + getCourseNumericGrade(student.name) + " " + getCourseLetterGrade(student.name) + "\n";
		}
		return result;
	}
	
	@SuppressWarnings("null")
	public ArrayList<Student> sortList() {
		ArrayList<Student> sortList = new ArrayList<>();
		boolean added = false;
		Set<Student> studentSet = system.keySet();
		for (Student student : studentSet) {
			if(sortList == null) {
				sortList.add(student);
			} else {
				for (int i = 0; i < sortList.size(); i++) {
					if (getCourseNumericGrade(student.name) > getCourseNumericGrade(sortList.get(i).name)) {
						sortList.add(i, student);
						added = true;
						break;
					}
				}
				if (added == false) {
					sortList.add(student);
				}
			}
		}
		return sortList;
	}
	
	public ArrayList<Double> specExam(int examId){
		ArrayList<Double> scores = new ArrayList<>();
		Set<Student> studSet = system.keySet();
		for (Student student : studSet) {
			scores.add(getExamScore(student.name, examId));
		}
		scores.sort(null);
		return scores;
	}

	@Override
	public double getMaxScore(int examId) {
		ArrayList<Double> students = specExam(examId);
		return students.get(students.size() - 1);
		
	}

	@Override
	public double getMinScore(int examId) {
		ArrayList<Double> students = specExam(examId);
		return students.get(0);
	}

	@Override
	public double getAverageScore(int examId) {
		ArrayList<Double> students = specExam(examId);
		double avg = 0.0;
		int total = 0;
		for (Double num : students) {
			total++;
			avg += num;
		}
		return avg / total;
	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		try {
			File file = new File(fileName);
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(manager);
			output.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	@Override
	public Manager restoreManager (String fileName) {
		Manager database = null;
		File file = new File(fileName);
		if (!file.exists()) {
			database = new SystemManager();
		} else {
			try {
				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
				database = (Manager) input.readObject();
				input.close();
			} catch (Exception e) {
				System.err.println(e);
				
			}
		}
		return database;
	}

}
