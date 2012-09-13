/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
// */
package repoclient;



/**
 *
 * @author fafa
 */
public class FTPDownLoadThread  implements Runnable {
    String localFileNames[];
    String remoteFileNames[];
    TransProtocol ftpProtocol = null;
    String username;
    String password;
    String host;
    int port ;

    public void setLocalFileName(String localfilenames[]) {
        this.localFileNames = localfilenames;
    }

    public void setRemoteFileName(String remotefilenames[]) {
        this.remoteFileNames = remotefilenames;
    }
    public void setArgs(String username,String password,String host,int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        ftpProtocol  = new FtpProtocol(host,port,username,password);
               
    }
    

   
    public void run() {
      ftpProtocol.connect(username, port, username, password);
      ftpProtocol.download(localFileNames, remoteFileNames);
      
    }
}
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
// 
//import org.apache.log4j.Logger;
// 
//
//
//import repoclient.FtpConfigInfo;
//import repoclient.FtpProtocol;
//import repoclient.TransProtocol;
//
//
//public class FTPDownLoadThread extends Thread {
//	    private static Logger logger = Logger.getLogger(FTPDownLoadThread.class);
//	     
//    private List<FtpFile> list;
//	    private String local_downLoad_dir;
//	     
//	     
//	     
//	    public FTPDownLoadThread(List<FtpFile> list, String localDownLoadDir,String threadName) {
//	        super();
//	        this.list = list;
//	        local_downLoad_dir = localDownLoadDir;
//	        super.setName(threadName);
//	    }
//	     
//	    @Override
//	    public void run() {
//	        String name = Thread.currentThread().getName();
//	        if(list==null ||local_downLoad_dir==null){
//	            logger.error("绾跨▼"+name+"鍙傛暟閿欒");
//	            return ;
//	        }
//	        int num = list.size();
//	        int count = 0;
//	        int flag = 0 ;
//	        for (FtpFile file : list) {
//	            count++;
//	            logger.info("绾跨▼"+name+"寮�涓嬭浇"+num+"涓枃浠朵腑鐨勭"+count+"涓枃浠�);
//	            //FTP杩炴帴
//	            TransProtocol ftpHelper = new FtpProtocol(FtpConfigInfo.getFtpHostName(),FtpConfigInfo.getPort(), FtpConfigInfo.getUsername(), FtpConfigInfo.getPassword());
//	            ftpHelper.connect(FtpConfigInfo.getFtpHostName(),
//	                    FtpConfigInfo.getPort(), FtpConfigInfo.getUsername(), FtpConfigInfo.getPassword());
//	             
//	            //璇ユ枃浠跺伐浣滅┖闂撮泦鍚�//	            List<String> filepath = file.getList();
//	            //鏂囦欢涓嬭浇鍒版湰鍦扮殑璺緞
//	            String local_path = local_downLoad_dir;
//	             
//	            // 鍙樻洿宸ヤ綔鐩綍
//	            // 缁勫悎涓嬭浇璺緞
//	            for (int i = 0; i < filepath.size(); i++) {
//	                //濡傛灉鏄┖闂撮粯璁ょ殑寮�宸ヤ綔绌洪棿
//	                if ("/".equals(filepath.get(i))) {
//	                    local_path += filepath.get(i);
//	                } else {
//	                    //鍏朵粬鐨勫伐浣滅┖闂�//	                     
//	                    //鍙樻洿宸ヤ綔绌洪棿
//	                    ftpHelper.changeDir(filepath.get(i));
//	                     
//	                    //缁勫悎鏈湴璺緞
//	                    local_path += filepath.get(i) + "/";
//	                }
//	            }
//	 
//	            logger.info("缁勫悎涔嬪悗涓嬭浇鐩綍涓猴細" + local_path);
//	             
//	            //濡傛灉鏈湴宸ヤ綔璺緞涓嶅瓨鍦紝寤虹珛鐩綍
//	            File local_file = new File(local_path);
//	//          synchronized (local_file) {
//	                if (!local_file.exists()) {
//	                    local_file.mkdirs();
//	                }
//	//          }
//	 
//	             
//	            //杩涜涓嬭浇骞惰繑鍥炰笅杞界粨鏋�//	            String status = ftpHelper.download(file.getFileName(), local_path + file.getFileName());
//	 
////	            if (!(status))
////	                flag++;
////	 
//	            //鏂紑FTP杩炴帴
//	            ftpHelper.disconnect();
//	 
//	        }
//	         
//	        if (flag != 0) {
//	            logger.error("绾跨▼"+name+"涓嬭浇澶辫触");
//	        }
//	        logger.info("绾跨▼"+name+"涓嬭浇鎴愬姛.......");
//	         
//	 
//	    }
//	 
//	    public List<FtpFile> getList() {
//	        return list;
//	    }
//	    public void setList(List<FtpFile> list) {
//	        this.list = list;
//	    }
//	    public String getLocal_downLoad_dir() {
//	        return local_downLoad_dir;
//	    }
//	    public void setLocal_downLoad_dir(String localDownLoadDir) {
//	        local_downLoad_dir = localDownLoadDir;
//	    }
//	     
//	     
//	}