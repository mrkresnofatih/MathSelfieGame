package com.mrkresnofatih.mathselfieapp.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProblemSetGetRequestModel implements IJsonSerializable {
    private String problemSetId;

    public ProblemSetGetRequestModel() {
    }

    public ProblemSetGetRequestModel(String problemSetId) {
        this.problemSetId = problemSetId;
    }

    public String getProblemSetId() {
        return problemSetId;
    }

    public void setProblemSetId(String problemSetId) {
        this.problemSetId = problemSetId;
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
