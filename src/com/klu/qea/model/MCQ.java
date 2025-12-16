package com.klu.qea.model;

public class MCQ extends Question{
	public MCQ(String id, String text, String[] options, String correctAnswers, int marks) {
        this.questionId = id;
        this.text = text;
        this.options = options;
        this.correctAnswers = correctAnswers;
        this.marks = marks;
    }

  public int evaluate(String response) {
        if (response.equals(null) || response.isBlank()) return 0;
        return response.equalsIgnoreCase(correctAnswers) ? marks : 0;
    }
}



