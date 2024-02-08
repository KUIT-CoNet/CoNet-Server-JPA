/*
package com.kuit.conet.dao;

import com.kuit.conet.domain.plan.*;
import com.kuit.conet.domain.plan.PastPlan;
import com.kuit.conet.dto.request.plan.UpdatePlanRequest;
import com.kuit.conet.dto.response.plan.MemberIsInPlanResponse;
import com.kuit.conet.dto.response.plan.UserPossibleTimeResponse;
import com.kuit.conet.dto.response.plan.UserTimeResponse;
import com.kuit.conet.dto.response.team.GetTeamMemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    public void deletePlan(Long planId, Boolean isFixedPlan) {
        Map<String, Object> param = Map.of("plan_id", planId);

        if (isFixedPlan) {
            // plan_member 삭제
            String planMemberSql = "delete from plan_member where plan_id=:plan_id";
            jdbcTemplate.update(planMemberSql, param);
        }

        if (!isFixedPlan) {
            // plan_member_time 삭제
            String planMemberTimeSql = "delete from plan_member_time where plan_id=:plan_id";
            jdbcTemplate.update(planMemberTimeSql, param);
        }

        // plan 삭제
        String planSql = "delete from plan where plan_id=:plan_id";
        jdbcTemplate.update(planSql, param);
    }

    public void updateWaitingPlan(Long planId, String planName) {
        String sql = "update plan set plan_name=:plan_name where plan_id=:plan_id and status=1";
        Map<String, Object> param = Map.of("plan_id", planId,
                "plan_name", planName);

        jdbcTemplate.update(sql, param);
    }

    public List<MemberIsInPlanResponse> getMemberIsInPlanId(Long planId) {
        String sql = "select team_id from plan where plan_id=:plan_id";
        Map<String, Object> param = Map.of("plan_id", planId);

        Long teamId = jdbcTemplate.queryForObject(sql, param, Long.class);

        String teamMemberSql = "select u.name, u.user_id, u.img_url from team_member tm, user u " +
                "where tm.user_id=u.user_id " +
                "and u.status=1 and tm.team_id=:team_id order by tm.user_id";
        Map<String, Object> teamMemberParam = Map.of("team_id", teamId);

        RowMapper<MemberIsInPlanResponse> mapper = (rs, rowNum) -> {
            MemberIsInPlanResponse response = new MemberIsInPlanResponse();
            response.setUserId(rs.getLong("user_id"));
            response.setName(rs.getString("name"));
            response.setUserImgUrl(rs.getString("img_url"));
            return response;
        };

        List<MemberIsInPlanResponse> memberIsInPlanResponses = jdbcTemplate.query(teamMemberSql, teamMemberParam, mapper);

        for (MemberIsInPlanResponse response : memberIsInPlanResponses) {
            String planMemberSql = "select exists(select * from plan_member where user_id=:user_id and plan_id=:plan_id)";
            Map<String, Object> planMemberParam = Map.of("user_id", response.getUserId(),
                        "plan_id", planId);

            response.setIsInPlan(jdbcTemplate.queryForObject(planMemberSql, planMemberParam, Boolean.class));
        }

        return memberIsInPlanResponses;
    }
}*/
