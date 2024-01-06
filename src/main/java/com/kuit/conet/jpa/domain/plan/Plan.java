package com.kuit.conet.jpa.domain.plan;

import com.kuit.conet.dto.plan.PlanMemberDTO;
import com.kuit.conet.jpa.domain.team.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class Plan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일 양방향 연관 관계 / 연관 관계의 주인
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "plan_name")
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "plan_start_period")
    private Date startPeriod;

    @Temporal(TemporalType.DATE)
    @Column(name = "plan_end_period")
    private Date endPeriod;

    @Temporal(TemporalType.DATE)
    private Date fixedDate;

    @Temporal(TemporalType.TIME)
    private Time fixedTime;

    @ColumnDefault("'WAITING'")
    @Enumerated(EnumType.STRING)
    private PlanStatus status;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)// 다대다(다대일, 일대다) 단방향 연관 관계 / 연관 관계 주인의 반대편
    private List<PlanMember> planMembers;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)// 다대다(다대일, 일대다) 단방향 연관 관계 / 연관 관계 주인의 반대편
    private List<PlanMemberTime> planMemberTimes;

    public static Plan createPlan(Team team, String name, Date startPeriod, Date endPeriod) {
        Plan plan = new Plan();

        plan.team = team;
        plan.name = name;
        plan.startPeriod = startPeriod;
        plan.endPeriod = endPeriod;
        plan.planMembers = new ArrayList<>();
        plan.planMemberTimes = new ArrayList<>();

        team.addPlan(plan);

        return plan;
    }

    public void fixPlan(Date fixedDate, Time fixedTime) {
        this.fixedDate = fixedDate;
        this.fixedTime = fixedTime;
        this.status = PlanStatus.FIXED;
    }

    public void addPlanMember(PlanMember planMember) {
        this.planMembers.add(planMember);
    }

    public int getPlanMembersCount() {
        return planMembers.size();
    }

}
