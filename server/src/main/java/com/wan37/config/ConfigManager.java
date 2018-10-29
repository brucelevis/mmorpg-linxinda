package com.wan37.config;

import com.wan37.util.excel.ExcelUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConfigManager {

    private static final String EXCEL_PATH = "./docs";

    /**
     * key: class name
     */
    private static Map<String, Map<Integer, T>> cfgMap = null;

    public List<T> loads(T t) {
        if (cfgMap == null) {
            init();
        }

        return new ArrayList<>(getConfigs(t).values());
    }

    public T load(T t, Integer id) {
        return getConfigs(t).get(id);
    }

    private Map<Integer, T> getConfigs(T t) {
        return cfgMap.get(t.getClass().getSimpleName());
    }

    private void init() {
        File file = new File(EXCEL_PATH);
        if (!file.isDirectory()) {
            throw new RuntimeException("错误的导表路径");
        }

        String[] filelist = file.list();
        assert filelist != null;

        for (int i = 0; i < filelist.length; i++) {
            File readfile = new File(EXCEL_PATH + "\\" + filelist[i]);


            if (!readfile.isDirectory()) {
                System.out.println("path=" + readfile.getPath());
                System.out.println("absolutepath="
                        + readfile.getAbsolutePath());
                System.out.println("name=" + readfile.getName());
            }
        }
    }

    private void readFile(File file) {
        String fileName = file.getName();

        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            Class<?> clazz = Class.forName(fileName);

            List<?> list = ExcelUtils.readExcelToEntity(clazz, in, file.getName());
            for (? : list) {
                System.out.println(excelUser.getName() + ":" + excelUser.getSex() + ":" + excelUser.getAge() + ":" + excelUser.getAddress());
            }
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
    }
}