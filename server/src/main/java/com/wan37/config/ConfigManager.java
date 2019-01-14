package com.wan37.config;

import com.google.common.collect.ImmutableList;
import com.wan37.util.excel.ExcelUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据配置导表管理器
 *
 * @author linda
 */
@Service
class ConfigManager implements ConfigLoader {

    private static final Logger LOG = Logger.getLogger(ConfigManager.class);

    /**
     * 导表相对路径
     */
    private static final String EXCEL_PATH = "./server/docs";

    /**
     * Map<XXXCfg.class，Map<cfgId，XXXCfg>>
     */
    private static final Map<Class, Map<Integer, ?>> CONFIG_MAP = new HashMap<>();

    private ConfigManager() {
        LOG.info("开始初始化导表...");
        init();
        LOG.info("初始化导表完成");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> Optional<T> load(Class<T> clazz, Integer id) {
        if (!CONFIG_MAP.containsKey(clazz)) {
            return Optional.empty();
        }

        Map<Integer, ?> cfgMap = CONFIG_MAP.get(clazz);
        if (!cfgMap.containsKey(id)) {
            return Optional.empty();
        }

        return Optional.of((T) cfgMap.get(id));
    }

    @Override
    public <T> String loadName(Class<T> clazz, Integer id) {
        T t = load(clazz, id).orElse(null);
        if (t == null) {
            return "NULL";
        }

        return (String) invokeMethod(t, "getName");
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> loads(Class<T> clazz) {
        if (!CONFIG_MAP.containsKey(clazz)) {
            return ImmutableList.of();
        }

        return CONFIG_MAP.get(clazz).values().stream()
                .map(c -> (T) c)
                .collect(Collectors.toList());
    }

    private void init() {
        File directory = new File(EXCEL_PATH);
        if (!directory.isDirectory()) {
            throw new RuntimeException("错误的导表路径");
        }

        String[] files = directory.list();
        assert files != null;

        for (String fullFileName : files) {
            File file = new File(EXCEL_PATH + "\\" + fullFileName);
            String className = getClassName(fullFileName);

            List<?> cfgList = readCfgFile(file, className);
            if (cfgList.isEmpty()) {
                continue;
            }

            Map<Integer, ?> cfgMap = createCfgMap(cfgList);
            CONFIG_MAP.put(getCfgType(cfgMap), cfgMap);
        }
    }

    private String getClassName(String fullFileName) {
        String simpleFileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));
        return simpleFileName.substring(simpleFileName.indexOf("_") + 1);
    }

    /**
     * 获得该类型配置表的接口类
     */
    private Class<?> getCfgType(Map<Integer, ?> cfgMap) {
        return cfgMap.values().stream()
                .findAny()
                .map(c -> c.getClass().getInterfaces()[0])
                .orElseThrow(() -> new RuntimeException("找不到配置表类"));
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, ?> createCfgMap(List<?> cfgList) {
        return cfgList.stream()
                .collect(Collectors.toMap(this::getCfgId, this::createCfg));
    }

    private Integer getCfgId(Object object) {
        return (Integer) invokeMethod(object, "getId");
    }

    @SuppressWarnings("unchecked")
    private <T> T createCfg(Object object) {
        return (T) invokeMethod(object, "create");
    }

    private Object invokeMethod(Object object, String methodName) {
        try {
            Method method = object.getClass().getMethod(methodName);
            return method.invoke(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<?> readCfgFile(File file, String className) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            Class<?> clazz = Class.forName(className);

            return ExcelUtils.readExcelToEntity(clazz, in, file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ImmutableList.of();
    }
}