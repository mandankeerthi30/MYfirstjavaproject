package com.klu.qea.evaluator;

import com.klu.qea.io.*;
import com.klu.qea.model.Question;
//import com.klu.qea.util.AnalyticsUtil;

import java.util.*;

public class QuizEvaluator implements Evaluator{

     private final String questionsPath;
    private final  String responsesPath;
     private final String reportPath;

    private boolean negativeScoring;   // NEW FLAG
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
            String sid = student.getKey();
            double total = 0;

            for (Question q : questions) {
                String resp = student.getValue().getOrDefault(q.getQuestionId(), "");
                String correct = q.getCorrectAnswer();
                int marks = q.getMarks();

                // ⭐ REPLACED STRATEGY WITH SIMPLE LOGIC
                if (resp.equalsIgnoreCase(correct)) {
                    total += marks;
                } else {
                    if (negativeScoring && !resp.isBlank()) {
                        total -= 0.25 * marks;
                    }
                }
            }
            scores.put(sid, total);
        }

        System.out.println("Evaluation done for " + scores.size() + " students.");
    }

    public void exportResults() throws Exception {
        new ReportWriter(reportPath).writeReport(scores);
        System.out.println("Report exported to " + reportPath);
    }

    public Map<String, Double> getScores() { return scores; }
}
	