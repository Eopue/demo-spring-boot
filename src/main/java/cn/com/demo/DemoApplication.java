package cn.com.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The type DemoApplication.
 * <p>
 *
 * @author Xiaolu.Liu
 * @date 2018/4/10
 */
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"cn.com.demo.dao"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
