package com.medicine.manager.code_generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lenvaco
 * @date 2019/9/22 22:24
 */
public class CommonUtils {

    /**
     * 数据连接信息
     * @param dbType 数据库类型
     * @param dbUrl 连接地址
     * @param username 用户名
     * @param password 密码
     * @param driver 驱动
     * @return DataSourceConfig
     */
    private static DataSourceConfig dataSourceConfig(DbType dbType, String dbUrl, String username, String password, String driver) {
        return new DataSourceConfig()
                .setDbType(dbType)
                .setUrl(dbUrl)
                .setUsername(username)
                .setPassword(password)
                .setDriverName(driver)
                ;
    }

    // 配置
    private static GlobalConfig globalConfig() {
        return new GlobalConfig()
                .setActiveRecord(false)
                .setAuthor(Config.AUTHOR)
                .setOutputDir(Config.outputDir)
                .setFileOverride(true)
                .setActiveRecord(true)// 不需要ActiveRecord特性的请改为false
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(false)// XML columList
                .setKotlin(false) //是否生成 kotlin 代码
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setEntityName(Config.FILE_NAME_MODEL)
                .setMapperName(Config.FILE_NAME_DAO)
                .setXmlName(Config.FILE_NAME_XML)
                .setServiceName(Config.FILE_NAME_SERVICE)
                .setServiceImplName(Config.FILE_NAME_SERVICE_IMPL)
                .setControllerName(Config.FILE_NAME_CONTROLLER)
                .setDateType(DateType.ONLY_DATE) //只使用 java.util.date 代替
                .setIdType(IdType.ID_WORKER)
                .setSwagger2(true) // model swagger2
                //.setOpen(true) // 是否打开输出目录
                ;
//                if (!serviceNameStartWithI)
//                    config.setServiceName("%sService");
    }


    private static StrategyConfig strategyConfig(String [] tablePrefixs, String [] tableNames) {
        return new StrategyConfig()
                .setCapitalMode(true) // 全局大写命名 ORACLE 注意
                //.setDbColumnUnderline(true)
                .setTablePrefix(tablePrefixs)// 此处可以修改为您的表前缀(数组)
                .setNaming(NamingStrategy.underline_to_camel) // 表名生成策略
                .setInclude(tableNames)//修改替换成你需要的表名，多个表名传数组
                //.setExclude(new String[]{"test"}) // 排除生成的表
                .setEntityLombokModel(true) // lombok实体
                .setEntityBuilderModel(false) // 【实体】是否为构建者模型（默认 false）
                .setEntityColumnConstant(false) // 【实体】是否生成字段常量（默认 false）// 可通过常量名获取数据库字段名 // 3.x支持lambda表达式
                //.setLogicDeleteFieldName("is_delete") // 逻辑删除属性名称
                //.setEntityTableFieldAnnotationEnable
                //.entityTableFieldAnnotationEnable(true)
                ;
    }

    // 包信息配置
    private static PackageConfig packageConfig(String packageName) {
        return new PackageConfig()
                .setParent(packageName)
                .setController(Config.PACKAGE_NAME_CONTROLLER)
                .setEntity(Config.PACKAGE_NAME_MODEL)
                .setMapper(Config.PACKAGE_NAME_DAO)
                .setXml(Config.PACKAGE_NAME_XML)
                .setService(Config.PACKAGE_NAME_SERVICE)
                .setServiceImpl(Config.PACKAGE_NAME_SERVICE_IMPL)
                ;
    }

    /**
     *
     * @param packageConfig
     * @return
     */
    private static InjectionConfig injectionConfig(final PackageConfig packageConfig) {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        List<FileOutConfig> fileOutConfigList = new ArrayList<FileOutConfig>();
        fileOutConfigList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输入文件名称
                if (StringUtils.isEmpty(packageConfig.getModuleName())) {
                    return Config.projectPath + "/src/main/resources/mapper/" + tableInfo.getXmlName() + StringPool.DOT_XML;
                }else {
                    return Config.projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName() + "/" + tableInfo.getXmlName() + StringPool.DOT_XML;
                }
            }
        });
        injectionConfig.setFileOutConfigList(fileOutConfigList);
        return injectionConfig;
    }

    /**
     * 执行器
     * @param dbType
     * @param dbUrl
     * @param username
     * @param password
     * @param driver
     * @param tablePrefixs
     * @param tableNames
     * @param packageName
     */
    public static void execute(DbType dbType, String dbUrl, String username, String password, String driver,
                               String [] tablePrefixs, String [] tableNames, String packageName) {
        GlobalConfig globalConfig = globalConfig();
        DataSourceConfig dataSourceConfig = dataSourceConfig(dbType, dbUrl, username, password, driver);
        StrategyConfig strategyConfig = strategyConfig(tablePrefixs, tableNames);
        PackageConfig packageConfig = packageConfig(packageName);
        InjectionConfig injectionConfig = injectionConfig(packageConfig);
        new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setTemplateEngine(new FreemarkerTemplateEngine())
                //.setCfg(injectionConfig)
                .execute();
    }

}