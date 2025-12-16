package com.klu.qea.evaluator;

import com.klu.qea.exception.IncompleteQuizException;

public interface Evaluator {
    void loadQuestions() throws Exception;
    void loadResponses() throws Exception;
    void evaluateAll() throws IncompleteQuizException;
    void exportResults() throws Exception;
}