package com.klu.qea.io;
import com.klu.qea.model.MCQ;
import com.klu.qea.model.Question;
import java.io.*;
import java.util.*;

public class QuestionReader {
    private final String path;

    public QuestionReader(String path) { this.path = path; }

    public List<Question> readQuestions() throws Exception {
        List<Question> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            boolean first = true;
            while ((line = br.readLine()) != null) {
                if (first) { first = false; continue; }
                if (line.isBlank()) continue;

                String[] parts = line.split(",");
                for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
                if (parts.length < 8) continue;

                String id = parts[0];
                String text = parts[1];
                String[] options = {parts[2], parts[3], parts[4], parts[5]};
                String answer = parts[6];
                int marks = Integer.parseInt(parts[7]);

                list.add(new MCQ(id, text, options, answer, marks));
            }
        }
        return list;
    }
}

