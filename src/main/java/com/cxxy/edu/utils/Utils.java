package com.cxxy.edu.utils;

import com.cxxy.edu.entity.Class;
import com.cxxy.edu.entity.Student;
import com.cxxy.edu.entity.TestQuestion;
import com.cxxy.edu.service.ClassService;
import com.cxxy.edu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Administrator
 * @title: Utils
 * @projectName auxiliarytool
 * @description: TODO
 * @date 2019/5/99:56
 */
public class Utils {
    @Autowired
    private static StudentService studentService;
    @Autowired
    private static ClassService classService;

    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else return String.format("%d B", size);
    }

    public static boolean judgeFileName(String filePath, String fileName) {
        String fileName1 = filePath.substring(filePath.lastIndexOf("/") + 1);
        String fileName2 = filePath.substring(filePath.lastIndexOf("\\") + 1);
        if (fileName1.equals(fileName) && new File(filePath).exists()) {
            return true;
        } else if (fileName2.equals(fileName) && new File(filePath).exists()) {
            return true;
        }
        return false;
    }

    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void timeFormat(List<Map<String, Object>> listMap) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map<String, Object> map : listMap) {
            for (Map.Entry<String, Object> map1 : map.entrySet()) {
                if (map1.getValue().getClass() == Timestamp.class) {
                    map1.setValue(format.format(map1.getValue()));

                }
            }
        }
    }

    public static Map<String, List<TestQuestion>> handleQuestionType(List<TestQuestion> questions, int num) {
        if (num != 0 && num <= questions.size()) {
            //从questions里面取num个出来
            List<TestQuestion> tempQuestions = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                Random random = new Random();
                int n = random.nextInt(questions.size());
                TestQuestion question = questions.get(n);
                tempQuestions.add(question);
                questions.remove(question);
            }
            questions = tempQuestions;
        }

        Map<String, List<TestQuestion>> map = new HashMap<>();
        List<TestQuestion> singleQuestions = new ArrayList<>();
        List<TestQuestion> multipleQuestions = new ArrayList<>();
        for (TestQuestion question : questions) {
            if (question.getTestQuestionType().toString().equals("0")) {//单选
                singleQuestions.add(question);

            } else {//多选
                multipleQuestions.add(question);

            }
        }
        map.put("single", singleQuestions);
        map.put("multiple", multipleQuestions);
        return map;
    }

    public static List<String> mySplit(String str) {//[{"id":"1","false":"B"},{"id":"6","false":"B"},{"id":"3"},{"id":"5","false":"C,D"}]
        str = str.substring(1, str.length() - 1);//去除中括号{"id":"1","false":"B"},{"id":"6","false":"B"},{"id":"3"},{"id":"5","false":"C,D"}
        List<String> tempArray = new ArrayList<>();
        int beginIndex, endIndex;
        beginIndex = 0;
        while (str.contains("},")) {
            endIndex = str.indexOf("},") + 1;
            String a = str.substring(beginIndex, endIndex);
            tempArray.add(a);
            str = str.substring(endIndex + 1, str.length());
        }
        tempArray.add(str);
        return tempArray;

    }

}
