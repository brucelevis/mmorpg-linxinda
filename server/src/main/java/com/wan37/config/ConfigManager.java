package com.wan37.config;

import com.wan37.util.excel.ExcelUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConfigManager {

    private static final String EXCEL_PATH = "./server/docs";

    /**
     * key: class name
     */
    private static Map<String, List<T>> cfgMap = null;

    @SuppressWarnings("unchecked")
    public <V> List<V> loads(Class<V> clazz) {
        if (cfgMap == null) {
            init();
        }

        return (List<V>) cfgMap.get(clazz.getName());
    }

    @SuppressWarnings("unchecked")
    private void init() {
        File directory = new File(EXCEL_PATH);
        if (!directory.isDirectory()) {
            throw new RuntimeException("错误的导表路径");
        }

        String[] filelist = directory.list();
        assert filelist != null;

        cfgMap = new HashMap<>();
        for (String path : filelist) {
            File file = new File(EXCEL_PATH + "\\" + path);

            String originalFileName = file.getName();
            String fileName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
            String className = fileName.substring(fileName.indexOf("_") + 1);

            cfgMap.put(className, (List) readFile(file, className));
        }
    }

    private List<?> readFile(File file, String className) {
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
        return null;
    }
}