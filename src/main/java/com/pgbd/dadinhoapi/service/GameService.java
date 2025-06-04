package com.pgbd.dadinhoapi.service;

import com.pgbd.dadinhoapi.dto.UserAnswerDTO;
import com.pgbd.dadinhoapi.game.GameCommandValidator;
import com.pgbd.dadinhoapi.game.model.Result;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    @Autowired
    private ObjectProvider<GameCommandValidator> validatorProvider;

    public Result submit(UserAnswerDTO data) {
        GameCommandValidator validator = validatorProvider.getObject();
        return validator.run(data);
    }

}
