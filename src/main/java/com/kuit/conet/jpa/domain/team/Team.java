package com.kuit.conet.jpa.domain.team;

import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.domain.plan.Plan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name", length = 100)
    private String name;

    @Column(name = "team_image_url", length = 500)
    private String imgUrl;

    @Column(length = 20)
    private String inviteCode;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime codeGeneratedTime;

    @CreationTimestamp
    private Date createdAt;

    @OneToMany(mappedBy = "team") // 다대일 양방향 연관 관계 / 연관 관계 주인의 반대편
    private List<Plan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL) // 다대다(다대일, 일대다) 양방향 연관 관계 / 연관 관계 주인의 반대편
    private List<TeamMember> teamMembers = new ArrayList<>();

    @Builder
    public Team(String teamName, String inviteCode, LocalDateTime codeGeneratedTime) {
        this.name = teamName;
        this.imgUrl = "";
        this.inviteCode = inviteCode;
        this.codeGeneratedTime = codeGeneratedTime;
    }

    public void updateImg(String imgUrl) {
        this.imgUrl = imgUrl;
    }



    public void addPlan(Plan plan) { plans.add(plan); }

    public List<Plan> getFixedPlans() {
        return plans.stream()
                .filter(Plan::isFixed)
                .toList();
    }

    //== 연관관계 편의 메서드 ==//
    public void addTeamMember(Team team, Member user) {
        TeamMember newTeamMember = new TeamMember(team,user);

        team.teamMembers.add(newTeamMember);
        newTeamMember.setTeam(team);
    }
}
