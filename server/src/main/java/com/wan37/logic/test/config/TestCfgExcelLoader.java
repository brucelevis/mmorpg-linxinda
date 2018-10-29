package com.wan37.logic.test.config;

import com.wan37.util.excel.ExcelUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Service
public class TestCfgExcelLoader {

    public List<TestCfgExcel> load() {
        File file = new File("D:\\Excel (2).xlsx");
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            List<TestCfgExcel> list = ExcelUtils.readExcelToEntity(TestCfgExcel.class, in, file.getName());
            for (TestCfgExcel testCfgExcel : list) {
                // NOOP
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
