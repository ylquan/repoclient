/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repoclient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author quanyuelong
 */
public class FtpConfigInfo {
    /**FTP服务器地址或名称*/
    private String ftpHostName;
    /**FTP服务器ftp服务端口*/
    private int port;
    /**FTP服务器登陆用户名*/
    private String username;
    /**FTP服务器登陆密码*/
    private String password;
    /**FTP下载到本地路径*/
    private String ftpDownLoadDir;
    /**FTPserver操作系统*/
    private String system;
    /**FTP语言*/
    private String serverlanguagecode;
    /**FTP多线程下载线程数量*/
    private int threadNUM;
    
    private final String _URL = "/config.properties";
    FtpConfigInfo(String ftpHostName,int port,String username,String password,String ftpDownLoadDir){
    this.ftpHostName = ftpHostName;
    this.port = port;
    this.username = username;
    this.password = password;
    this.ftpDownLoadDir = ftpDownLoadDir;
    }
    
    //日志
    Logger logger = Logger.getLogger(FtpConfigInfo.class);
    
    private static FtpConfigInfo config = new FtpConfigInfo();
    public static String getFtpHostName() {
        return config.ftpHostName;
    }
    public static int getPort() {
        return config.port;
    }
    
    public static String getUsername(){
        return config.username;
    }
    
    public static String getPassword(){
        return config.password;
    }
    
    public static String getFtpDownLoadDir(){
        return config.ftpDownLoadDir;
    }
    
    public static String getSystem(){
        return config.system;
    }
    
    public static String getServerLanguageCode(){
        return config.serverlanguagecode;
    }
    
    public static int getThreadNUM(){
        return config.threadNUM;
    }
    
    private FtpConfigInfo() {
        loadConfig();
    }
    
    private void loadConfig() {
        InputStream is = this.getClass().getResourceAsStream(_URL);
        Properties pro = new Properties();
        try {
            pro.load(is);
        } catch (IOException e) {
            logger.error("config.properties配置文件加载错误", e);
        }
        ftpHostName = pro.getProperty("ftphostname");
        port = Integer.valueOf(pro.getProperty("port"));
        username=pro.getProperty("username");
        password=pro.getProperty("password");
        ftpDownLoadDir=pro.getProperty("ftpdownloaddir");
        system = pro.getProperty("system");
        serverlanguagecode = pro.getProperty("serverlanguagecode");
        threadNUM = Integer.valueOf(pro.getProperty("threadNUM"));
        
        logger.info("配置文件信息.....");
        logger.info("ftpHostName:"+ftpHostName);
        logger.info("port:"+port);
        logger.info("username:"+username);
        logger.info("password:"+password);
        logger.info("ftpDownLoadDir:"+ftpDownLoadDir);
        logger.info("system:"+system);
        logger.info("serverlanguagecode:"+serverlanguagecode);
        logger.info("threadNUM:"+threadNUM);
    }
}
