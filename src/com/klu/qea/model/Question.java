package com.klu.qea.model;

public abstract class Question {
	protected String questionId;
	protected String text;
	protected String[] options;
	protected String correctAnswers;
	protected int marks;
	public Question() {}

    public String getQuestionId() {return questionId; }
    public String getText() { return text; }
    public String[] getOptions() { return options; }
    public String getCorrectAnswer() { return correctAnswers; }
    public int getMarks() { return marks; }

	public abstract int evaluate(String response);

}
