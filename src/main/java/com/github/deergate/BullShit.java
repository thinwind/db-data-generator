package com.github.deergate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class BullShit {

    @Autowired
    ObjectMapper mapper;

    BullShitData data;

    String[] famous;

    String[] bosh;

    String[] after;

    String[] before;

    @PostConstruct
    public void init() throws IOException {
        InputStream inputStream=BullShit.class.getResourceAsStream("/bullshit/data.json");
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
        data = mapper.readValue(reader,BullShitData.class);
        inputStream.close();
        famous = data.famous;
        bosh = data.bosh;
        after = data.after;
        before = data.before;
    }

    /**
     * @param title  标题
     * @param length 正文长度
     * @return content 返回正文长度
     */
    public String generator(String title, int length) {
        StringBuilder builder=new StringBuilder();
        try {
            while (builder.length() < length) {
                //获取100以内的随机整数
                ThreadLocalRandom random = ThreadLocalRandom.current();
                int rand = random.nextInt(100);
                if (rand < 10) {
                    builder.append("\n");
                } else if (rand < 20) {
                    String content = famous[random.nextInt(famous.length)];
                    content = content.replace("a", before[random.nextInt(before.length)])
                            .replace("b", after[random.nextInt(after.length)]);
                    builder.append(content);
                } else {
                    String content = bosh[random.nextInt(bosh.length)];
                    content = content.replace("x", title);
                    builder.append(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
