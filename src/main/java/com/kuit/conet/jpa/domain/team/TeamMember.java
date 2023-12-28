package com.kuit.conet.jpa.domain.team;

import com.kuit.conet.jpa.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@NoArgsConstructor
public class TeamMember {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id") // 다대다(다대일, 일대다) 양방향 연관 관계 / 연관 관계의 주인
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 다대다(다대일, 일대다) 양방향 연관 관계 / 연관 관계의 주인
    private Member member;

    @Column(columnDefinition = "integer default 0")
    private Integer bookMark;


    //==생성 메서드==//
    public static TeamMember createTeamMember(Team team, Member teamCreator) {
        TeamMember teamMember = new TeamMember();
        teamMember.member=teamCreator;
        teamMember.team=team;

        return teamMember;
    }
}
