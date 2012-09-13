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

//���Կ��Ǽ�����Щ    
//     //ö����UploadStatus����
//
// public enum UploadStatus {
// Create_Directory_Fail,   //Զ�̷�������ӦĿ¼����ʧ��
// Create_Directory_Success, //Զ�̷���������Ŀ¼�ɹ�
// Upload_New_File_Success, //�ϴ����ļ��ɹ�
// Upload_New_File_Failed,   //�ϴ����ļ�ʧ��
// File_Exits,      //�ļ��Ѿ�����
// Remote_Bigger_Local,   //Զ���ļ����ڱ����ļ�
// Upload_From_Break_Success, //�ϵ������ɹ�
// Upload_From_Break_Failed, //�ϵ�����ʧ��
// Delete_Remote_Faild;   //ɾ��Զ���ļ�ʧ��
// }
//
// //ö����DownloadStatus����
// public enum DownloadStatus {
// Remote_File_Noexist, //Զ���ļ�������
// Local_Bigger_Remote, //�����ļ�����Զ���ļ�
// Download_From_Break_Success, //�ϵ������ļ��ɹ�
// Download_From_Break_Failed,   //�ϵ������ļ�ʧ��
// Download_New_Success,    //ȫ�������ļ��ɹ�
// Download_New_Failed;    //ȫ�������ļ�ʧ��
// }
// 
    FtpProtocol(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;

        ftpClient = new FTPClient();
        //���ý�������ʹ�õ����������������̨   
        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }

    FtpProtocol(String host, String username, String password) {
        this.host = host;
        this.port = 21;
        this.username = username;
        this.password = password;

        ftpClient = new FTPClient();
        //���ý�������ʹ�õ����������������̨   
        this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
    }

    //��ͨ������
    public boolean connect(String hostname, int port, String user, String password) {

        logger.debug("FTP request connect to " + host + ":" + port);
        logger.info("����FTP����......");
	logger.info("hostname:" + hostname + "  port:" + port + " user" + user + " password:" + password);
        
        
        try {
            ftpClient.connect(host, port);
            ftpClient.setControlEncoding("GBK");//����֧�����ĸ�ʽ
           
//            // ���ÿͻ��˲���ϵͳ���ͣ�Ϊwindows ��ʵ����"WINDOWS" ��Ȼû�õ�
//            FTPClientConfig conf = new FTPClientConfig(FtpConfigInfo.getSystem());
//            // ���÷����������� ���� "zh"
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
        logger.info("����FTP���ӹر����ӷ���...");
        try {
            logger.info("�ر�ftp����......");
            ftpClient.disconnect();
        } catch (IOException ex) {
            logger.error("�ر�ftp���ӳ����쳣......", ex);
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
            //����PassiveMode����  
            ftpClient.enterLocalPassiveMode();
            //�����Զ��������ķ�ʽ����  
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            //��Զ��Ŀ¼�Ĵ���  
            String remoteFileName = remote;
            if (remote.contains("/")) {
                remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
                String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
                if (!directory.equalsIgnoreCase("/") && !ftpClient.changeWorkingDirectory(directory)) {
                    //���Զ��Ŀ¼�����ڣ���ݹ鴴��Զ�̷�����Ŀ¼  
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
                                System.out.println("����Ŀ¼ʧ��");
                                return "UploadStatus.Create_Directory_Fail";
                            }
                        }

                        start = end + 1;
                        end = directory.indexOf("/", start);

                        //�������Ŀ¼�Ƿ񴴽����  
                        if (end <= start) {
                            break;
                        }
                    }
                }
            }

            //���Զ���Ƿ�����ļ�  
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

                //�����ƶ��ļ��ڶ�ȡָ��,ʵ�ֶϵ�����  
                InputStream is = new FileInputStream(f);
                if (is.skip(remoteSize) == remoteSize) {
                    ftpClient.setRestartOffset(remoteSize);
                    if (ftpClient.storeFile(remote, is)) {
                        return "UploadStatus.Upload_From_Break_Success";
                    }
                }

                //����ϵ�����û�гɹ�����ɾ�����������ļ��������ϴ�  
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
        //--------------����Ĵ����ǲ��Թ���û������------------
               
        for(int i=0;i<localfiles.length;i++){ 
             this.download(localfiles[i], remotepaths[i]);
        }
        return "SUCCESS";
    }

    
    public String download(String localfile, String remotepath) {
        //���ñ���ģʽ   
        ftpClient.enterLocalPassiveMode();
        try {
            //�����Զ����Ʒ�ʽ����   
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        String result;

        //���Զ���ļ��Ƿ����   
        FTPFile[] files = null;
        try {
            files = ftpClient.listFiles(new String(remotepath.getBytes("GBK"), "iso-8859-1"));
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (files.length != 1) {
            System.out.println("Զ���ļ�������");
            return "DownloadStatus.Remote_File_Noexist";
        }

        long lRemoteSize = files[0].getSize();
        File f = new File(localfile);
        //���ش����ļ������жϵ�����   
        if (f.exists()) {
            long localSize = f.length();
            //�жϱ����ļ���С�Ƿ����Զ���ļ���С   
            if (localSize >= lRemoteSize) {
                System.out.println("�����ļ�����Զ���ļ���������ֹ");
                return " DownloadStatus.Local_Bigger_Remote";
            }

            //���жϵ�����������¼״̬   
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
                            System.out.println("���ؽ��ȣ�" + process+"%");
                        }
                        //TODO �����ļ����ؽ���,ֵ�����process������   
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
                            System.out.println("���ؽ��ȣ�" + process+"%");
                        }
                        //TODO �����ļ����ؽ���,ֵ�����process������   
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

    //��ͨ������
    public boolean fileExists(String nameOfFile) {//������ͨ��
        //���Զ���Ƿ�����ļ�   
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

    //��ͨ������
    public boolean folderExists(String nameOfFolder) {//������ͨ��
        //���Զ���Ƿ�����ļ�   
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

    //��ͨ������
    public boolean createFolder(String nameOfFolderToCreate) {//[��ͨ��]
        try {
            return ftpClient.makeDirectory(nameOfFolderToCreate);
        } catch (IOException e) {
        }
        return false;
    }

    //��ͨ������
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
            System.out.println("������" + (tlength - 2));
            for (int i = 2; i < tlength; i++) {

                if (files[i].getType() == 0) {
                    System.out.println(files[i].getName());
                    this.deleteFile(files[i].getName());
                    System.out.println("�ļ����鳤��:" + (files.length - 2));

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

    //��ͨ������
    public boolean renameFolder(String oldName, String newName) {
        try {
            return ftpClient.rename(oldName, newName);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //��ͨ������

    public boolean renameFile(String oldName, String newName) {
        try {
            return ftpClient.rename(oldName, newName);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //��ͨ������
    public boolean changeDir(String remoteDir) {
        try {
            logger.info("�������Ŀ¼Ϊ��" + remoteDir);
            
            return ftpClient.changeWorkingDirectory(remoteDir);

        } catch (IOException ex) {
            logger.error("�������Ŀ¼Ϊ" + remoteDir + "ʧ��", ex);
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
    }
    
    
    public boolean changeToParentDir() {
        try {
            logger.info("�������Ŀ¼����Ŀ¼");
            return ftpClient.changeToParentDirectory();
        } catch (IOException e) {
            logger.error("�������Ŀ¼����Ŀ¼����", e);
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
        System.out.println("�ļ�������" + names.length);
    }

    //��ͨ������
    public FTPFile[] getFilesList(String pathName) {//��ͨ������
        logger.info("�����ѯftp�����ļ�����.....");
        try {
            ftpClient.changeWorkingDirectory(pathName);
            return ftpClient.listFiles();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    //��ͨ������
    public boolean deleteFile(String nameOfFileToDelete) {//��ͨ������
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

    //��ͨ������
    public boolean moveFileToOtherFolder(String fileName, String destFolderName) {
        try {
            return ftpClient.rename(fileName, destFolderName + "/" + fileName);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //��ͨ������
    public boolean moveFolderToOtherFolder(String sourceFolderName, String destFolderName) {
        try {
            return ftpClient.rename(sourceFolderName, destFolderName + "/" + sourceFolderName);

        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FtpProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
