package com.wan37.config;

import java.util.List;
import java.util.Optional;

/**
 * @author linda
 */
public interface GeneralCfgLoader<T> {

    /**
     * 读取该配置全部数据
     *
     * @return List<T>
     */
    List<T> loads();

    /**
     * 读取特定id的配置数据
     *
     * @param id 配置id
     * @return Optional<T>
     */
    Optional<T> load(Integer id);
}
