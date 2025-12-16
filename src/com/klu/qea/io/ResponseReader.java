package com.klu.qea.io;


	import java.io.*;
	import java.util.*;

	public class ResponseReader {
	    String path;

	    public ResponseReader(String path) { this.path = path; }

	    public Map<String, Map<String, String>> readResponses() throws Exception {
	        Map<String, Map<String, String>> responses = new HashMap<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
	            String line;
	            boolean first = true;
	            while ((line = br.readLine()) != null) {
	                if (first) { first = false; continue; }
	                if (line.isBlank()) continue;

	                String[] parts = line.split(",");
	                if (parts.length < 3) continue;

	                String studentId = parts[0].trim();
	                String qid = parts[1].trim();
	                String resp = parts[2].trim();

	                responses.putIfAbsent(studentId, new HashMap<>());
	                responses.get(studentId).put(qid, resp);
	            }
	        }
	        return responses;
	    }
	

}

