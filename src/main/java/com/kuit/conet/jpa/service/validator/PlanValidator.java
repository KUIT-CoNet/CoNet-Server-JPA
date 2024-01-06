package com.kuit.conet.jpa.service.validator;

import com.kuit.conet.common.exception.PlanException;
import com.kuit.conet.jpa.domain.plan.Plan;
import com.kuit.conet.jpa.domain.plan.PlanStatus;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;

import static com.kuit.conet.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
public class PlanValidator {
    public static void validatePlanStatusIsNotFixed(Plan plan) {
        if(plan.getStatus() == PlanStatus.FIXED){
            log.error(ALREADY_FIXED_PLAN.getMessage());
            throw new PlanException(ALREADY_FIXED_PLAN);
        }
    }

    public static void validateDateInPeriod(Date date, Plan plan) {
        if (date.before(plan.getStartPeriod()) || date.after(plan.getEndPeriod())) {
            log.error(DATE_NOT_IN_PERIOD.getMessage());
            throw new PlanException(DATE_NOT_IN_PERIOD);
        }
    }
}
