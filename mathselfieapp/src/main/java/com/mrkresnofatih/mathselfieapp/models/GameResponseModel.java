package com.mrkresnofatih.mathselfieapp.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class GameResponseModel implements IJsonSerializable {
    private String problemSetId;
    private List<ProblemGetResponseModel> problems;

    public GameResponseModel() {
    }

    public GameResponseModel(String problemSetId, List<ProblemGetResponseModel> problems) {
        this.problemSetId = problemSetId;
        this.problems = problems;
    }

    public String getProblemSetId() {
        return problemSetId;
    }

    public void setProblemSetId(String problemSetId) {
        this.problemSetId = problemSetId;
    }

    public List<ProblemGetResponseModel> getProblems() {
        return problems;
    }

    public void setProblems(List<ProblemGetResponseModel> problems) {
        this.problems = problems;
    }

    public void addProblem(ProblemGetResponseModel problem) {
        this.problems.add(problem);
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
