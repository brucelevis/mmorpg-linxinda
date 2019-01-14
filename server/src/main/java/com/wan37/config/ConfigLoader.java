package com.wan37.config;

import java.util.List;
import java.util.Optional;

/**
 * 配置加载器
 *
 * @author linda
 */
public interface ConfigLoader {

    /**
     * 读取该配置全部数据
     *
     * @param clazz 配置表接口类
     * @return List<T>
     */
    <T> List<T> loads(Class<T> clazz);

    /**
     * 读取特定id的配置数据
     *
     * @param clazz 配置表接口类
     * @param id    配置id
     * @return Optional<T>
     */
    <T> Optional<T> load(Class<T> clazz, Integer id);

    /**
     * 读取特定id的配置数据的名字，若没有则返回字符串"NULL"
     *
     * @param clazz 配置表接口类
     * @param id    配置id
     * @return String
     */
    <T> String loadName(Class<T> clazz, Integer id);
}
