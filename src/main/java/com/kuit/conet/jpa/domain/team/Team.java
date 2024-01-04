package com.kuit.conet.jpa.domain.team;

import com.kuit.conet.jpa.domain.member.Member;
import com.kuit.conet.jpa.domain.plan.Plan;
import com.kuit.conet.service.StorageService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.web.multipart.MultipartFile;

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

    private LocalDateTime codeGeneratedTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "team") // 다대일 양방향 연관 관계 / 연관 관계 주인의 반대편
    private List<Plan> plans = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true) // 다대다(다대일, 일대다) 양방향 연관 관계 / 연관 관계 주인의 반대편
    private List<TeamMember> teamMembers = new ArrayList<>();

    public void addPlan(Plan plan) { plans.add(plan); }

    //==생성 메서드==//
    public static Team createTeam(String teamName, String inviteCode, LocalDateTime codeGeneratedTime, Member teamCreator, String imgUrl){
        Team team = new Team();
        team.name=teamName;
        team.inviteCode=inviteCode;
        team.codeGeneratedTime=codeGeneratedTime;
        team.imgUrl=imgUrl;

        TeamMember teamMember=TeamMember.createTeamMember(team,teamCreator);
        teamMember.setStatus(TeamMemberStatus.CREATOR);

        team.addTeamMember(teamMember);

        return team;
    }

    //== 연관관계 편의 메서드 ==//
    public void addTeamMember(TeamMember teamMember) {
        teamMembers.add(teamMember);
        teamMember.setTeam(this);
    }

    public void deleteMember(TeamMember teamMember) {
        teamMembers.remove(teamMember);
    }
}
