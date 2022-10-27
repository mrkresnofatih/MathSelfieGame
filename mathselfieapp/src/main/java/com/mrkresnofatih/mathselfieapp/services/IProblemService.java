package com.mrkresnofatih.mathselfieapp.services;

import com.mrkresnofatih.mathselfieapp.models.*;

import java.util.List;

public interface IProblemService {
    BaseFuncResponse<ProblemGetResponseModel> SaveProblem(ProblemSaveRequestModel saveProblemRequest);
    BaseFuncResponse<ProblemGetResponseModel> UpdateProblem(ProblemUpdateRequestModel updateProblemRequest);
    BaseFuncResponse<List<ProblemGetResponseModel>> GetProblemSet(ProblemSetGetRequestModel getProblemSetRequest);
}
