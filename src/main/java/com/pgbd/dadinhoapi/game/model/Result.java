package com.pgbd.dadinhoapi.game.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pgbd.dadinhoapi.model.Item;

import java.util.Map;

public class Result {

    Status status;
    String errorDetail;
    Map<Item, Integer> finalBasket;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public Map<Item, Integer> getFinalBasket() {
        return finalBasket;
    }

    public void setFinalBasket(Map<Item, Integer> finalBasket) {
        this.finalBasket = finalBasket;
    }

    @JsonIgnore
    public boolean isValid() {
        return status == null || status.equals(Status.CORRECT);
    }
}
