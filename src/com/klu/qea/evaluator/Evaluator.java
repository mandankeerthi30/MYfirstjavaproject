package com.klu.qea.evaluator;

public interface  Evaluator {
	void loadQuestions() throws Exception;
    void loadResponses() throws Exception;
    void evaluateAll();
    void exportResults() throws Exception;

}

