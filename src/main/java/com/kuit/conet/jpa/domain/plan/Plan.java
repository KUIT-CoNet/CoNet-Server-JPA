package com.kuit.conet.jpa.domain.plan;

import com.kuit.conet.jpa.domain.team.Team;
import jakarta.persistence.*;
import lombok.Builder;
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
@NoArgsConstructor
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

    @OneToMany(mappedBy = "plan")// 다대다(다대일, 일대다) 양방향 연관 관계 / 연관 관계 주인의 반대편
    private List<PlanMember> plans = new ArrayList<>();

    private Plan(Team team, String name, Date startPeriod, Date endPeriod) {
        this.team = team;
        this.name = name;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;

        team.addPlan(this);
    }

    public static Plan createPlan(Team team, String name, Date startPeriod, Date endPeriod) {
        return new Plan(team, name, startPeriod, endPeriod);
    }
}
