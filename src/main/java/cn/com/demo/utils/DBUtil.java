/*
 * Copyright (c) 2018 Cloud-Star, Inc. All Rights Reserved..
 */

package cn.com.demo.utils;


import com.alibaba.druid.pool.DruidDataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 简易DB工具类
 *
 * @author Xiaolu.Liu
 */
public class DBUtil {
    /**
     * 数据库查询类
     */
    private static QueryRunner runner;

    static {
        Properties p = new Properties();

        /* 数据库用户名 */
        String user = "root";
        /* 数据库密码 */
        String password = "123456";
        /* 数据库连接 */
        String url = "jdbc:mysql://47.93.25.215:3306/demo?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false";

        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(password);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setValidationQuery("select 'x'");
        druidDataSource.setMaxActive(20);
        druidDataSource.setMinIdle(1);
        druidDataSource.setPoolPreparedStatements(false);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60 * 1000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setRemoveAbandoned(true);
        druidDataSource.setRemoveAbandonedTimeout(3600);

        runner = new QueryRunner(druidDataSource);
    }

    /**
     * 查询表.
     *
     * @param <T>       the type parameter
     * @param sql       查询SQL
     * @param beanClazz the bean clazz
     * @param param     SQL参数
     * @return 结果 list
     */
    public static <T> List<T> queryBeanList(String sql, Class<T> beanClazz, Object... param) {
        List<T> result = null;
        try {
            result = runner.query(sql, new BeanListHandler<>(beanClazz), param);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 插入数据.
     *
     * @param sql   SQL
     * @param param 参数
     * @return 影响条数 int
     */
    public static int insert(String sql, Object... param) {
        ResultSetHandler<String> resultHandler = new BeanHandler<>(String.class);
        String genKey = null;
        try {
            genKey = runner.insert(sql, resultHandler, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return genKey == null ? 0 : 1;
    }

    /**
     * 更新、删除DB数据.
     *
     * @param sql   SQL
     * @param param 参数
     * @return 影响条数 int
     */
    public static int update(String sql, Object... param) {
        int result = 0;
        try {
            result = runner.update(sql, param);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 查询表.
     *
     * @param sql   查询SQL
     * @param param SQL参数
     * @return 结果 map
     */
    public static Map queryMap(String sql, Object... param) {
        Map result = null;
        try {
            result = runner.query(sql, new MapHandler(), param);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 查询表.
     * 结果为出入列的List
     *
     * @param sql   查询SQL
     * @param col   查询列
     * @param param SQL参数
     * @return 结果 list
     */
    public static List<String> queryColumnList(String sql, String col, Object... param) {
        List<String> result = null;
        try {
            result = runner.query(sql, new ColumnListHandler<String>(col), param);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}
