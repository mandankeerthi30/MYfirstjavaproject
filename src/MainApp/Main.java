package MainApp;

	import com.klu.qea.evaluator.QuizEvaluator;
import com.klu.qea.exception.IncompleteQuizException;

import java.util.Scanner;

	public class Main {
	    public static void main(String[] args) throws Exception {

	        Scanner sc = new Scanner(System.in);

	        String qPath = "questions.csv";
	        String rPath = "responses.csv";
	        String report = "report.csv";
String choice;
	        while (true) {
	        	System.out.println("===== QUIZ EVALUATION SYSTEM =====");
	            System.out.println("Choose scoring: 1) Normal  2) Negative (0.25)");
	            choice = sc.nextLine();

	            if ("1".equals(choice) || "2".equals(choice)) {
	                break; // valid input
	            }
	            System.out.println("Invalid input! Please select 1 or 2 only.");
	        }

	        boolean negative = choice.equals("2");

	        QuizEvaluator evaluator = new QuizEvaluator(qPath, rPath, report, negative);

	        while (true) {
	        	System.out.println(
	        			  "\n1. Load Questions\n2. Load Responses\n3. Evaluate\n4. Export Report\n5. Difficulty Analysis\n6. Exit"
	        			);

	            String op = sc.nextLine();

	            switch (op) {
	                case "1": evaluator.loadQuestions(); break;
	                case "2": evaluator.loadResponses(); break;
	                case "3":
	                    try {
	                        evaluator.evaluateAll();
	                    } catch (IncompleteQuizException e) {
	                        System.out.println(e.getMessage());
	                    }
	                    break;

	                case "4": evaluator.exportResults(); break;
	                case "5":
	                    evaluator.calculateDifficulty();
	                    break;

	                case "6":
	                    return;

	                default: System.out.println("Invalid option");
	            }
	        }
	    }
	}