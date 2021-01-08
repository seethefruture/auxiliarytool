package com.cxxy.edu.CunstomConfig;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//             判断，传入过来是String，并且要映射到Date属性时才会调用该方法
public class CustomDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        // 在这里面将String转换date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date date = simpleDateFormat.parse(source);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
