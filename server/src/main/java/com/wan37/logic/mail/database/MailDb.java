package com.wan37.logic.mail.database;

import com.wan37.logic.mail.database.convert.MailItemDbConverterImpl;
import com.wan37.logic.mail.database.convert.MailRewardDbConverterImpl;
import com.wan37.logic.player.database.PlayerDb;

import javax.persistence.*;

@Entity
@Table(name = "tb_mail")
public class MailDb {

    @Id
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 发件人名字
     */
    private String fromName;

    /**
     * 过期时间
     */
    private long expireTime;

    /**
     * 内容
     */
    private String content;

    /**
     * 已读
     */
    private boolean hadReceived;

    /**
     * 系统奖励
     */
    @Convert(converter = MailRewardDbConverterImpl.class)
    @Column(columnDefinition = "text")
    private MailRewardDb mailRewardDb;

    /**
     * 邮件物品
     */
    @Convert(converter = MailItemDbConverterImpl.class)
    @Column(columnDefinition = "text")
    private MailItemDb mailItemDb;

    /**
     * 注意：多端不需要CascadeType，解决更新导致one端数据被更新
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "playerUid")
    private PlayerDb player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MailRewardDb getMailRewardDb() {
        return mailRewardDb;
    }

    public void setMailRewardDb(MailRewardDb mailRewardDb) {
        this.mailRewardDb = mailRewardDb;
    }

    public MailItemDb getMailItemDb() {
        return mailItemDb;
    }

    public void setMailItemDb(MailItemDb mailItemDb) {
        this.mailItemDb = mailItemDb;
    }

    public boolean isHadReceived() {
        return hadReceived;
    }

    public void setHadReceived(boolean hadRead) {
        this.hadReceived = hadRead;
    }

    public PlayerDb getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDb player) {
        this.player = player;
    }
}
