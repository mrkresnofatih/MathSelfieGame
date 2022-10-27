package com.mrkresnofatih.mathselfieapp.services;

import com.mrkresnofatih.mathselfieapp.models.BaseFuncResponse;
import com.mrkresnofatih.mathselfieapp.models.GameResponseModel;

public interface IGameService {
    BaseFuncResponse<GameResponseModel> CreateGame();
}
