package com.klu.qea.evaluator;

import com.klu.qea.exception.IncompleteQuizException;
import com.klu.qea.io.*;
import com.klu.qea.model.Question;
//import com.klu.qea.util.AnalyticsUtil;

import java.util.*;

public class QuizEvaluator implements Evaluator{

    private final String questionsPath;
    private final String responsesPath;
    private final String reportPath;

    private boolean negativeScoring;  

    List<Question> questions = new ArrayList<>();
     Map<String, Map<String, String>> responses = new HashMap<>();
     Map<String, Double> scores = new LinkedHashMap<>();

    public QuizEvaluator(String qPath, String rPath, String report, boolean negativeScoring) {
        this.questionsPath = qPath;
        this.responsesPath = rPath;
        this.reportPath = report;
        this.negativeScoring = negativeScoring;
    }

    public void loadQuestions() throws Exception {
        questions = new QuestionReader(questionsPath).readQuestions();
        System.out.println("Loaded " + questions.size() + " questions.");
    }

    public void loadResponses() throws Exception {
        responses = new ResponseReader(responsesPath).readResponses();
        System.out.println("Loaded responses for " + responses.size() + " students.");
    }

    public void evaluateAll() {

        for (var student : responses.entrySet()) {

            String studentId = student.getKey();
            Map<String, String> studentResponses = student.getValue();
            double total = 0;
            try {
                if (studentResponses.size() < questions.size()) {
                    throw new IncompleteQuizException(
                        "Warning: Student " + studentId + " did not attempt all questions"
                    );
                }
            } catch (IncompleteQuizException e) {
                System.out.println(e.getMessage());
            }
            for (Question q : questions) {
                String resp = studentResponses.get(q.getQuestionId());
                if (resp == null || resp.isBlank()) {
                    continue;
                }
                if (resp.equalsIgnoreCase(q.getCorrectAnswer())) {
                    total += q.getMarks();
                } 
                else if (negativeScoring) {
                    total -= 0.25 * q.getMarks();
                }
            }

            scores.put(studentId, total);
        }
        System.out.println("Evaluation completed for " + scores.size() + " students.");
    }
    public void exportResults() throws Exception {
        new ReportWriter(reportPath).writeReport(scores);
        System.out.println("Report exported to " + reportPath);
    }
    public Map<String, Double> getScores() { return scores; }
    public void calculateDifficulty() {
        System.out.println("\n===== QUESTION DIFFICULTY REPORT =====");
        List<String> easy = new ArrayList<>();
        List<String> medium = new ArrayList<>();
        List<String> difficult = new ArrayList<>();
        for (Question q : questions) {
            int totalAttempts = 0;
            int correctCount = 0;
            for (Map<String, String> studentResp : responses.values()) {
                if (studentResp.containsKey(q.getQuestionId())) {
                    totalAttempts++;
                    String resp = studentResp.get(q.getQuestionId());
                    if (resp != null && resp.equalsIgnoreCase(q.getCorrectAnswer())) {
                        correctCount++;
                    }
                }
            }
            if (totalAttempts == 0) continue; 

            double percentage = (correctCount * 100.0) / totalAttempts;

            if (percentage >= 70) {
                easy.add(q.getQuestionId());
            } else if (percentage >= 40) {
                medium.add(q.getQuestionId());
            } else {
                difficult.add(q.getQuestionId());
            }
        }

        System.out.println("Easy Questions     : " + easy);
        System.out.println("Medium Questions   : " + medium);
        System.out.println("Difficult Questions: " + difficult);
    }

    }