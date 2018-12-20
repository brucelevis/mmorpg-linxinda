package com.wan37.logic.mission.database;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tb_mission")
public class MissionDb {

    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "player_uid")
    private Set<PlayerMissionDb> missions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<PlayerMissionDb> getMissions() {
        return missions;
    }

    public void setMissions(Set<PlayerMissionDb> missions) {
        this.missions = missions;
    }
}
