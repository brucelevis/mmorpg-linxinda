package com.wan37.logic.guild.dao;

import com.wan37.logic.guild.database.GuildGlobalDb;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 公会数据库操作
 *
 * @author linda
 */
@Service
public interface GuildDao extends Repository<GuildGlobalDb, Long> {

    /**
     * 保存公会信息
     *
     * @param guildGlobalDb 公会数据库实例
     * @return GuildGlobalDb
     */
    GuildGlobalDb save(GuildGlobalDb guildGlobalDb);

    /**
     * 是否已存在公会名
     *
     * @param name 公会名
     * @return boolean
     */
    boolean existsByName(String name);

    /**
     * 返回公会数据库实例
     *
     * @param uid 公会唯一id
     * @return GuildGlobalDb
     */
    GuildGlobalDb findByUid(Long uid);

    /**
     * 删除公会
     *
     * @param uid 公会唯一id
     */
    @Transactional(rollbackFor = Exception.class)
    void removeByUid(Long uid);
}
