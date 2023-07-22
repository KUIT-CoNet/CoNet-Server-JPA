package com.kuit.conet.dao;

import com.kuit.conet.common.exception.BaseException;
import com.kuit.conet.domain.MemberPossibleTime;
import com.kuit.conet.domain.Plan;
import com.kuit.conet.domain.PlanMemberTime;
import com.kuit.conet.dto.response.plan.MemberPossibleTimeResponse;
import com.kuit.conet.dto.response.plan.UserPossibleTimeResponse;
import com.kuit.conet.dto.response.plan.UserTimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveSetCommands;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@Transactional
public class PlanDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PlanDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long savePlan(Plan plan) {
        String sql = "insert into plan (team_id, plan_name, plan_start_period, plan_end_period) " +
                "values (:team_id, :plan_name, :plan_start_period, :plan_end_period)";
        Map<String, Object> param = Map.of("team_id", plan.getTeamId(),
                "plan_name", plan.getPlanName(),
                "plan_start_period", plan.getPlanStartPeriod(),
                "plan_end_period", plan.getPlanEndPeriod());

        jdbcTemplate.update(sql, param);

        String returnSql = "select last_insert_id()";
        Map<String, Object> returnParam = Map.of();

        return jdbcTemplate.queryForObject(returnSql, returnParam, Long.class);
    }

    public void saveTime(PlanMemberTime planMemberTime) {
        String sql = "insert into plan_member_time (plan_id, user_id, possible_date, possible_time) " +
                "values (:plan_id, :user_id, :possible_date, :possible_time)";
        Map<String, Object> param = Map.of("plan_id", planMemberTime.getPlanId(),
                "user_id", planMemberTime.getUserId(),
                "possible_date", planMemberTime.getPossibleDate(),
                "possible_time", planMemberTime.getPossibleTime());

        jdbcTemplate.update(sql, param);
    }

    public Boolean isExistingUserTime(Long planId, Long userId) {
        String isExistingSql = "select exists(select * from plan_member_time where plan_id=:plan_id and user_id=:user_id)";
        Map<String, Object> isExistingParam = Map.of("plan_id", planId,
                "user_id", userId);

        return jdbcTemplate.queryForObject(isExistingSql, isExistingParam, Boolean.class);
    }

    public UserTimeResponse getUserTime(Long planId, Long userId) {
        String sql = "select * from plan_member_time where plan_id=:plan_id and user_id=:user_id";
        Map<String, Object> param = Map.of("plan_id", planId,
                "user_id", userId);

        RowMapper<UserPossibleTimeResponse> mapper = ((rs, rowNum) -> {
            UserPossibleTimeResponse possibleTime = new UserPossibleTimeResponse();
            possibleTime.setDate(rs.getDate("possible_date"));
            possibleTime.setTime(rs.getString("possible_time"));
            return possibleTime;
        });

        List<UserPossibleTimeResponse> response = jdbcTemplate.query(sql, param, mapper);

        return new UserTimeResponse(planId, userId, response);
    }

    public List<MemberPossibleTime> getMemberTime(Long planId, Date planStartPeriod) {
        String sql = "select user_id, possible_time from plan_member_time where plan_id=:plan_id and possible_date=:possible_date";
        Map<String, Object> param = Map.of("plan_id", planId,
                "possible_date", planStartPeriod);

        RowMapper<MemberPossibleTime> mapper = ((rs, rowNum) -> {
            MemberPossibleTime possibleTime = new MemberPossibleTime();
            possibleTime.setUserId(rs.getLong("user_id"));
            possibleTime.setPossibleTime(rs.getString("possible_time"));
            return possibleTime;
        });

        return jdbcTemplate.query(sql, param, mapper);
    }

    public Plan getWaitingPlan(Long planId) {
        String sql = "select * from plan where plan_id=:plan_id and status=1";
        Map<String, Object> param = Map.of("plan_id", planId);

        RowMapper<Plan> mapper = ((rs, rowNum) -> {
            Plan plan = new Plan();
            plan.setPlanId(rs.getLong("plan_id"));
            plan.setTeamId(rs.getLong("team_id"));
            plan.setPlanName(rs.getString("plan_name"));
            plan.setPlanStartPeriod(rs.getDate("plan_start_period"));
            plan.setPlanEndPeriod(rs.getDate("plan_end_period"));
            plan.setFixedDate(rs.getDate("fixed_date"));
            plan.setFixedTime(rs.getTime("fixed_time"));
            plan.setStatus(rs.getBoolean("status"));
            return plan;
        });

        return jdbcTemplate.queryForObject(sql, param, mapper);
    }

    public Long getTeamId(Long planId) {
        String sql = "select team_id from plan where plan_id=:plan_id";
        Map<String, Object> param = Map.of("plan_id", planId);

        return jdbcTemplate.queryForObject(sql, param, Long.class);
    }
}
