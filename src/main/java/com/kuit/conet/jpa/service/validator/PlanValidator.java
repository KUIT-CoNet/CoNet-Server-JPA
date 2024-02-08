package com.kuit.conet.jpa.service.validator;

import com.kuit.conet.common.exception.PlanException;
import com.kuit.conet.jpa.domain.plan.Plan;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.*;
import static com.kuit.conet.jpa.domain.plan.PlanStatus.*;

@Slf4j
public class PlanValidator {
    private final static int MIN_TIME_NUMBER = 0;
    private final static int MAX_TIME_NUMBER = 23;

    public static void validatePlanIsAlreadyFixed(Plan plan) {
        if(plan.getStatus() == FIXED){
            log.error(ALREADY_FIXED_PLAN.getMessage());
            throw new PlanException(ALREADY_FIXED_PLAN);
        }
    }

    public static void validatePlanIsWaiting(Plan plan) {
        if (plan.getStatus() != WAITING) {
            log.error(NOT_WAITING_PLAN.getMessage());
            throw new PlanException(NOT_WAITING_PLAN);
        }
    }

    public static void validatePlanIsFixed(Plan plan) {
        if (plan.getStatus() != FIXED) {
            log.error(NOT_FIXED_PLAN.getMessage());
            throw new PlanException(NOT_FIXED_PLAN);
        }
    }

    public static void validateDateInPeriod(Date date, Plan plan) {
        if (date.before(plan.getStartPeriod()) || date.after(plan.getEndPeriod())) {
            log.error(DATE_NOT_IN_PERIOD.getMessage());
            throw new PlanException(DATE_NOT_IN_PERIOD);
        }
    }

    public static void validateTime(int time) {
        if (time < MIN_TIME_NUMBER || time > MAX_TIME_NUMBER) {
            log.error(TIME_OUT_OF_BOUND.getMessage());
            throw new PlanException(TIME_OUT_OF_BOUND);
        }
    }

}
