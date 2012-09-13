/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repoclient;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.*;
import org.apache.log4j.Logger;


public class FtpProtocol implements TransProtocol {

    private static final Logger logger = Logger.getLogger(FtpProtocol.class);
    public FTPClient ftpClient;
    public String host;
    public int port;
    public String username;
    public String password;

//可以考虑加上这些    
//     //枚举类UploadStatus代码
//
// public enum UploadStatus {
// Create_Directory_Fail,   //远程服务器相应目录创建失败
// Create_Directory_Success, //远程服务器闯将目录成功
// Upload_New_File_Success, //上传新文件成功
// Upload_New_File_Failed,   //上传新文件失败
// File_Exits,      //文件已经存在
// Remote_Bigger_Local,   //远程文件大于本地文件
// Upload_From_Break_Success, //断点续传成功
// Upload_From_Break_Failed, //断点续传失败
// Delete_Remote_Faild;   //删除远程文件失败
// }
//
// //枚举类DownloadStatus代码
// public enum DownloadStatus {
// Remote_File_Noexist, //远程文件不存在
// Local_Bigger_Remote, //本地文件大于远程文件
// Download_From_Break_Success, //断点下载文件成功
// Download_From_Break_Failed,   //断点下载文件失败
// Download_New_Success,    //全新下载文件成功
// Download_New_Failed;    //全新下载文件失败
// }
// 
    FtpProtocol(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

        ftpClient = new FTPClient();
        //设置将过程中使用到的命令输出到控制台   
        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }

    FtpProtocol(String host, String username, String password) {
        this.host = host;
        this.port = 21;
        this.username = username;
        this.password = password;

        ftpClient = new FTPClient();
        //设置将过程中使用到的命令输出到控制台   
        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }

    //已通过测试
    public boolean connect(String hostname, int port, String user, String password) {

        logger.debug("FTP request connect to " + host + ":" + port);
        logger.info("进行FTP连接......");
	logger.info("hostname:" + hostname + "  port:" + port + " user" + user + " password:" + password);
        
        
        try {
            ftpClient.connect(host, port);
            ftpClient.setControlEncoding("GBK");//设置支持中文格式
           
//            // 设置客户端操作系统类型，为windows 其实就是"WINDOWS" 虽然没用到
//            FTPClientConfig conf = new FTPClientConfig(FtpConfigInfo.getSystem());
//            // 设置服务器端语言 中文 "zh"
//            conf.setServerLanguageCode(FtpConfigInfo.getServerLanguageCode());
            
        } catch (SocketException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        int reply = ftpClient.getReplyCode();
        if (FTPReply.isPositiveCompletion(reply)) {
            logger.debug("FTP request login as " + username);
            try {
                if (ftpClient.login(username, password)) {
                    ftpClient.enterLocalPassiveMode();
                    return true;
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        disconnect();
        return false;
    }


    public void disconnect() {
        logger.info("进入FTP连接关闭连接方法...");
        try {
            logger.info("关闭ftp连接......");
            ftpClient.disconnect();
        } catch (IOException ex) {
            logger.error("关闭ftp连接出现异常......", ex);
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public String upload(String[] localfileNames, String[] remotefolderNames) {
         for(int i=0;i<localfileNames.length;i++){
             this.upload(localfileNames[i], remotefolderNames[i]);
        }
        return "SUCCESS";
    }
    

  
    public String upload(String local, String remote) {
        String result = "Upload Failed";
        try {
            //设置PassiveMode传输  
            ftpClient.enterLocalPassiveMode();
            //设置以二进制流的方式传输  
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            //对远程目录的处理  
            String remoteFileName = remote;
            if (remote.contains("/")) {
                remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
                String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
                if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(directory)) {
                    //如果远程目录不存在，则递归创建远程服务器目录  
                    int start = 0;
                    int end = 0;
                    if (directory.startsWith("/")) {
                        start = 1;
                    } else {
                        start = 0;
                    }
                    end = directory.indexOf("/", start);
                    while (true) {
                        String subDirectory = remote.substring(start, end);
                        if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                            if (ftpClient.makeDirectory(subDirectory)) {
                                ftpClient.changeWorkingDirectory(subDirectory);
                            } else {
                                System.out.println("创建目录失败");
                                return "UploadStatus.Create_Directory_Fail";
                            }
                        }

                        start = end + 1;
                        end = directory.indexOf("/", start);

                        //检查所有目录是否创建完毕  
                        if (end <= start) {
                            break;
                        }
                    }
                }
            }

            //检查远程是否存在文件  
            FTPFile[] files = null;
            try {
                files = ftpClient.listFiles(remoteFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (files.length == 1) {
                long remoteSize = files[0].getSize();
                File f = new File(local);
                long localSize = f.length();
                if (remoteSize == localSize) {
                    return "UploadStatus.File_Exits";
                } else if (remoteSize > localSize) {
                    return "UploadStatus.Remote_Bigger_Local";
                }

                //尝试移动文件内读取指针,实现断点续传  
                InputStream is = new FileInputStream(f);
                if (is.skip(remoteSize) == remoteSize) {
                    ftpClient.setRestartOffset(remoteSize);
                    if (ftpClient.storeFile(remote, is)) {
                        return "UploadStatus.Upload_From_Break_Success";
                    }
                }

                //如果断点续传没有成功，则删除服务器上文件，重新上传  
                if (!ftpClient.deleteFile(remoteFileName)) {
                    return "UploadStatus.Delete_Remote_Faild";
                }
                is = new FileInputStream(f);
                if (ftpClient.storeFile(remote, is)) {
                    result = "UploadStatus.Upload_New_File_Success";
                } else {
                    result = "UploadStatus.Upload_New_File_Failed";
                }
                is.close();
            } else {
                InputStream is = new FileInputStream(local);
                if (ftpClient.storeFile(remoteFileName, is)) {
                    result = "UploadStatus.Upload_New_File_Success";
                } else {
                    result = "UploadStatus.Upload_New_File_Failed";
                }
                is.close();
            }
            return result;
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    
    public String download(String[] localfiles, String[] remotepaths) {
//         Thread ts[]=new Thread[localfiles.length];
//        ExecutorService pool = Executors.newFixedThreadPool(localfiles.length);
//         FTPDownLoadThread tems[]=  new FTPDownLoadThread[localfiles.length];
//         for(int i=0;i<localfiles.length;i++){
//          tems[i] = new FTPDownLoadThread();
//          
//          tems[i].setFtpClient(this);
//          System.out.println(this.toString());
//            tems[i].setLocalFileName(localfiles[i]);
//           tems[i].setRemoteFileName(remotepaths[i]);
//          ts[i] = new Thread(tems[i]);
//         }
//         //for(int j=0;j<localfiles.length;j++){
//         pool.execute(ts[0]);
//         pool.execute(ts[1]);
//         //}
//        pool.shutdown();
//           return "SUCCESS";
        //--------------下面的代码是测试过的没有问题------------
               
        for(int i=0;i<localfiles.length;i++){ 
             this.download(localfiles[i], remotepaths[i]);
        }
        return "SUCCESS";
    }

    
    public String download(String localfile, String remotepath) {
        //设置被动模式   
        ftpClient.enterLocalPassiveMode();
        try {
            //设置以二进制方式传输   
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        String result;

        //检查远程文件是否存在   
        FTPFile[] files = null;
        try {
            files = ftpClient.listFiles(new String(remotepath.getBytes("GBK"), "iso-8859-1"));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (files.length != 1) {
            System.out.println("远程文件不存在");
            return "DownloadStatus.Remote_File_Noexist";
        }

        long lRemoteSize = files[0].getSize();
        File f = new File(localfile);
        //本地存在文件，进行断点下载   
        if (f.exists()) {
            long localSize = f.length();
            //判断本地文件大小是否大于远程文件大小   
            if (localSize >= lRemoteSize) {
                System.out.println("本地文件大于远程文件，下载中止");
                return " DownloadStatus.Local_Bigger_Remote";
            }

            //进行断点续传，并记录状态   
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(f, true);
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }
            ftpClient.setRestartOffset(localSize);
            InputStream in = null;
            try {
                in = ftpClient.retrieveFileStream(new String(remotepath.getBytes("GBK"), "iso-8859-1"));
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte[] bytes = new byte[1024];
            long step = lRemoteSize / 100;
            long process = localSize / step;
            int c;
            try {
                while ((c = in.read(bytes)) != -1) {
                    out.write(bytes, 0, c);
                    localSize += c;
                    long nowProcess = localSize / step;
                    if (nowProcess > process) {
                        process = nowProcess;
                        if (process % 10 == 0) {
                            System.out.println("下载进度：" + process+"%");
                        }
                        //TODO 更新文件下载进度,值存放在process变量中   
                    }
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }

            boolean isDo = false;
            try {
                isDo = ftpClient.completePendingCommand();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (isDo) {
                result = "DownloadStatus.Download_From_Break_Success";
            } else {
                result = "DownloadStatus.Download_From_Break_Failed";
            }
        } else {
            try {
                OutputStream out = null;
                try {
                    out = new FileOutputStream(f);
                } catch (FileNotFoundException ex) {
                    java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
                }
                InputStream in = null;
                try {
                    in = ftpClient.retrieveFileStream(new String(remotepath.getBytes("GBK"), "iso-8859-1"));
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
                }
                byte[] bytes = new byte[1024];
                long step = lRemoteSize / 100;
                long process = 0;
                long localSize = 0L;
                int c;
                while ((c = in.read(bytes)) != -1) {
                    out.write(bytes, 0, c);
                    localSize += c;
                    long nowProcess = localSize / step;
                    if (nowProcess > process) {
                        process = nowProcess;
                        if (process % 10 == 0) {
                            System.out.println("下载进度：" + process+"%");
                        }
                        //TODO 更新文件下载进度,值存放在process变量中   
                    }
                }
                in.close();
                out.close();
                boolean upNewStatus = ftpClient.completePendingCommand();
                if (upNewStatus) {
                    result = "DownloadStatus.Download_New_Success";
                } else {
                    result = "DownloadStatus.Download_New_Failed";
                }
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "SUCCESS";
    }

    //已通过测试
    public boolean fileExists(String nameOfFile) {//测试已通过
        //检查远程是否存在文件   
        FTPFile[] files = null;
        try {
            files = ftpClient.listFiles();

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
            if ((files[i].getName()).equals(nameOfFile) && files[i].getType() == 0) {

                return true;
            }
        }
        return false;
    }

    //已通过测试
    public boolean folderExists(String nameOfFolder) {//测试已通过
        //检查远程是否存在文件   
        FTPFile[] files = null;
        try {
            files = ftpClient.listFiles();

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
            if ((files[i].getName()).equals(nameOfFolder) && files[i].getType() == 1) {

                return true;
            }
        }
        return false;
    }

    //已通过测试
    public boolean createFolder(String nameOfFolderToCreate) {//[已通过]
        try {
            return ftpClient.makeDirectory(nameOfFolderToCreate);
        } catch (IOException e) {
        }
        return false;
    }

    //已通过测试
    public boolean deleteFolder(String nameOfFolderToDelete) {
        try {
            ftpClient.changeWorkingDirectory(nameOfFolderToDelete);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            FTPFile[] files = null;
            files = ftpClient.listFiles();
            int tlength = 0;
            tlength = files.length;
            System.out.println("数量：" + (tlength - 2));
            for (int i = 2; i < tlength; i++) {

                if (files[i].getType() == 0) {
                    System.out.println(files[i].getName());
                    this.deleteFile(files[i].getName());
                    System.out.println("文件数组长度:" + (files.length - 2));

                } else {
                    System.out.println(files[i].getName());
                    this.deleteFolder(files[i].getName());
                }


            }

            ftpClient.changeToParentDirectory();
            return ftpClient.removeDirectory(nameOfFolderToDelete);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    //已通过测试
    public boolean renameFolder(String oldName, String newName) {
        try {
            return ftpClient.rename(oldName, newName);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //已通过测试

    public boolean renameFile(String oldName, String newName) {
        try {
            return ftpClient.rename(oldName, newName);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //已通过测试
    public boolean changeDir(String remoteDir) {
        try {
            logger.info("变更工作目录为：" + remoteDir);
            
            return ftpClient.changeWorkingDirectory(remoteDir);

        } catch (IOException ex) {
            logger.error("变更工作目录为" + remoteDir + "失败", ex);
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    
    public boolean changeToParentDir() {
        try {
            logger.info("变更工作目录到父目录");
            return ftpClient.changeToParentDirectory();
        } catch (IOException e) {
            logger.error("变更工作目录到父目录出错", e);
            return false;
        }
    }
    
    
    
    public void getFilesList(FTPFile[] ftpFile) {
        String remoteFilePath = "ftp//";
        String names[] = null;
        try {

            ftpClient.changeWorkingDirectory(remoteFilePath);
            names = ftpClient.listNames();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("文件个数：" + names.length);
    }

    //已通过测试
    public FTPFile[] getFilesList(String pathName) {//已通过测试
        logger.info("进入查询ftp所有文件方法.....");
        try {
            ftpClient.changeWorkingDirectory(pathName);
            return ftpClient.listFiles();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    //已通过测试
    public boolean deleteFile(String nameOfFileToDelete) {//已通过测试
        try {
            return ftpClient.deleteFile(nameOfFileToDelete);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
    public boolean copyFileToOtherFolder(String fileName, String destFolderName) {
        //Tell server that the file will have JCL records
        try {
            ftpClient.site("RECFM=FB LRECL=140");
            String replyText = ftpClient.getReplyString();
            System.out.println(replyText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Submit the job from the text file.
        try {
            FileInputStream inputStream = new FileInputStream(fileName);
            ftpClient.storeFile(destFolderName + "/" + fileName, inputStream);
            String replyText = ftpClient.getReplyString();
            System.out.println(replyText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean copyFolderToOtherFolder(String sourceFolderName, String destFolderName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //已通过测试
    public boolean moveFileToOtherFolder(String fileName, String destFolderName) {
        try {
            return ftpClient.rename(fileName, destFolderName + "/" + fileName);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //已通过测试
    public boolean moveFolderToOtherFolder(String sourceFolderName, String destFolderName) {
        try {
            return ftpClient.rename(sourceFolderName, destFolderName + "/" + sourceFolderName);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
