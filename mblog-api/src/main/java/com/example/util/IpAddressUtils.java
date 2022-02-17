package com.example.util;

import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author giaming
 * @date 2022/2/12 - 21:44
 * @detail
 */
@Slf4j
@Component
public class IpAddressUtils {
    private static DbSearcher searcher;
    private static Method method;

    /**
     * 在服务启动时加载 ip2region.db内存中
     * 解决打包jar后找不到ip2region.db的问题
     */
    @PostConstruct
    private void initIp2regionResource() {
        try{
            InputStream inputStream = new ClassPathResource("/ipdb/ip2region.db").getInputStream();
            // 将ip2region.db转为ByteArray
            byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
            DbConfig dbConfig = new DbConfig();
            searcher = new DbSearcher(dbConfig, dbBinStr);
            // 二进制方式初始化DBSearcher，需要使用基于内存的查找算法 memorySearch
            method = searcher.getClass().getMethod("memorySearch", long.class);
        }catch (Exception e){
//            e.printStackTrace();
            log.error("initIp2regionResource exception:", e);
        }
    }

    /**
     * 根据ip从ip2region.db中获取地理位置
     *
     * @param ip
     * @return
     */
    public static String getCityInfo(String ip){
       if (ip == null || !Util.isIpAddress(ip)){
           log.error("Error: Invalid ip address");
           return null;
       }

        try {
            DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
            String ipInfo = dataBlock.getRegion();
            if(!StringUtils.isEmpty(ipInfo)){
                ipInfo = ipInfo.replace("|0", "");
                ipInfo = ipInfo.replace("0|", "");
            }
            return ipInfo;
        } catch (Exception e) {
            log.error("getCityInfo exception : ", e);
        }
        return null;
    }

}
