package com.klu.qea.io;
import java.io.*;
import java.util.*;

public class ReportWriter {
    private final String path;

    public ReportWriter(String path) { this.path = path; }

    public void writeReport(Map<String, Double> scores) throws Exception {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            bw.write("studentId,totalScore");
            bw.newLine();
            for (var e : scores.entrySet()) {
                bw.write(e.getKey() + "," + String.format("%.2f", e.getValue()));
                bw.newLine();
            }
        }
    }
}
