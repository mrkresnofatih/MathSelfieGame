package com.mrkresnofatih.mathselfieapp.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ProblemGetResponseModel implements IJsonSerializable {
    private String problemSetId;
    private String problemId;
    private int firstNumber;
    private int secondNumber;
    private String operation;
    private Map<String, Integer> options;
    private String correctAnswer;
    private String givenAnswer;

    public ProblemGetResponseModel() {
    }

    public ProblemGetResponseModel(ProblemEntity problemEntity) {
        this.problemSetId = problemEntity.getProblemSetId();
        this.problemId = problemEntity.getProblemId();
        this.correctAnswer = problemEntity.getCorrectAnswer();
        this.givenAnswer = problemEntity.getGivenAnswer();
        this.firstNumber = problemEntity.getFirstNumber();
        this.secondNumber = problemEntity.getSecondNumber();
        this.operation = problemEntity.getOperation();
        this.options = problemEntity.getOptions();
    }

    public String getProblemSetId() {
        return problemSetId;
    }

    public void setProblemSetId(String problemSetId) {
        this.problemSetId = problemSetId;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Map<String, Integer> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Integer> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    @Override
    public String toJsonSerialized() {
        try {
            var mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException exception) {
            return "JsonProcessingException";
        }
    }
}
