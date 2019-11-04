package com.medicine.manager;

import cn.hutool.core.util.IdUtil;
import com.mchange.lang.LongUtils;
import com.medicine.manager.bean.DeptQuery;
import com.medicine.manager.bean.dto.DeptDTO;
import com.medicine.manager.common.utils.JwtTokenUtil;
import com.medicine.manager.model.Dept;
import com.medicine.manager.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicineBackendApplicationTests {

    @Autowired
    private UserService userService;
    @Test
    public void contextLoads() {
        List<DeptDTO> list = new ArrayList<>();
        Dept dept = new Dept();
        dept.setId(1L);
        dept.setName("121");dept.setParentId(0L); dept.setCreateTime(new Date());
        list.add(new DeptDTO(dept));
        dept.setId(2L);
        list.add(new DeptDTO(dept));
        for (DeptDTO deptDTO:
             list) {
            deptDTO.setChildren(new ArrayList<>());
            deptDTO.getChildren().add(new DeptDTO(dept));
        }
        list.forEach(deptDTO -> {
            System.err.println(list);
        });
        System.out.println(userService.findByUsername("1156434215"));
        System.out.println(new BCryptPasswordEncoder().encode("abc123456"));
    }

}
