package com.wan37.logic.mail.database;

import com.wan37.logic.player.database.PlayerDb;

import javax.persistence.*;

@Entity
@Table
public class MailDb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String msg;

    /**
     * 可选属性optional=false,表示player不能为空。删除邮件，不影响玩家
     * 设置在MailDb表中的关联字段(外键)
     */
    @ManyToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "playerUid")
    private PlayerDb player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public PlayerDb getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDb player) {
        this.player = player;
    }
}
