package com.gr.imp;


import com.gr.in.BaseCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: wwis-kunming
 * @description:
 * @author: Shizh
 * @create: 2018-07-23 16:37
 **/
@Component
public class MainRunner implements ApplicationRunner {

    private List<BaseCreate> creators;

    @Autowired(required = false)
    public void setHandlers(List<BaseCreate> creators) {
        if (creators == null || creators.isEmpty())
            creators = new ArrayList<>();
        this.creators = creators;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (BaseCreate creator : creators) {
            creator.create();
        }
    }
}
