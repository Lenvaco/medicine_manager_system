package com.medicine.manager;

import cn.hutool.core.util.IdUtil;
import com.mchange.lang.LongUtils;
import com.medicine.manager.common.utils.JwtTokenUtil;
import com.medicine.manager.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicineBackendApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    public void contextLoads() {

        System.out.println(new BCryptPasswordEncoder().encode("abc123456"));
        System.out.println(new BCryptPasswordEncoder().encode("abc123456"));
    }

}
