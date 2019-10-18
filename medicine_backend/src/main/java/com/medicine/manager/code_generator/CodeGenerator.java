package com.medicine.manager.code_generator;

import com.baomidou.mybatisplus.annotation.DbType;

/**
 * @author lenvaco
 * @date 2019/9/22 22:22
 */
public class CodeGenerator {
    public static void main(String[] args) {
        DbType dbType = DbType.MYSQL;
        String dbUrl = "jdbc:mysql://47.98.137.65:3306/medicine_system?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String password = "password";
        String driver = "com.mysql.cj.jdbc.Driver";
        // 表前缀，生成的实体类，不含前缀
        String [] tablePrefixs = {""};
        // 表名，为空，生成所有的表
        String [] tableNames = {};
        // 基础包名
        String packageName = "com.medicine.manager";
        CommonUtils.execute(dbType, dbUrl, username, password, driver, tablePrefixs, tableNames, packageName);
    }
}
