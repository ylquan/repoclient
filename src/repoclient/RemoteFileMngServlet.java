/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repoclient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author quanyuelong
 */
public class RemoteFileMngServlet extends RepoServlet{
      String host;
      int port;
      String username;
      String password;
        
    TransProtocol tp = new FtpProtocol(host,port,username,password);
    
    /**
     *建立连接
     * 参数：主机名hostname,端口号port,用户名user,密码：password
     */
    public boolean connect(String hostname, int port, String user, String password){
        return tp.connect(hostname, port, user, password);
    }

    /**
     *关闭连接
     * 参数：
     */
    public void disconnect(){
        tp.disconnect();
    }
    
    /**
     *上传文件
     * 参数：request,response
     */
    public String upload(String request, String response){
    
        return tp.upload(request, response);
    }
    
    /**
     *下载文件
     * 参数：request,response
     */
    public String download(String localFileName, String remoteFileName){
        return tp.download(localFileName, remoteFileName);
    }
   
    /**
     *判断文件是否存在
     * 参数：文件名
     */
    public boolean fileExists(String nameOfFile){
        return tp.fileExists(nameOfFile);
    }
    
    /**
     * 判断文件夹是否存在
     * 参数文件夹名称
     */
    public boolean folderExists(String nameOfFolder){
        return tp.folderExists(nameOfFolder);
    }
    
    /**
     *创建一个文件夹
     * 参数：要创建的文件夹的名称
     */
    public boolean createFolder(String nameOfFolderToCreate){
        return tp.createFolder(nameOfFolderToCreate);
    }
    
    /**
     *删除一个文件夹
     * 参数：要删除的文件夹的名称
     */
    public boolean deleteFolder(String nameOfFolderToDelete){
        return tp.deleteFolder(nameOfFolderToDelete);
    }
    
     /**
     *修改文件夹名称
     * 参数：要修改的文件夹的新名称，旧名称
     */
    public boolean renameFolder(String oldName,String newName){
        return tp.renameFolder(oldName, newName);
    }
  
    /**
     *根据路径改变当前目录
     * 参数：路径名称 String remoteDir
     */
    public boolean changeDir(String remoteDir){
        return tp.changeDir(remoteDir);
    }
    
    /**
    * 获取文件列表
    * 参数：FTPFile[]
    */
    public void getFilesList(FTPFile[] ftpFile){
         tp.getFilesList(ftpFile);
    }
    
    /**
     *删除文件
     * 参数：文件名称
     */
    public boolean deleteFile(String nameOfFileToDelete){
        return tp.deleteFile(nameOfFileToDelete);
    }
       
    /**
     *复制文件到另外一个文件夹
     * 参数：文件名称，路径名称
     */
    public boolean copyFileToOtherFolder(String fileName,String destFolderName){
        return tp.copyFileToOtherFolder(fileName, destFolderName);
    }
    
    /**
     *复制一个文件夹到另外一个文件夹
     * 参数：源文件夹名称，目标文件夹名称
     */
    public boolean copyFolderToOtherFolder(String sourceFolderName,String destFolderName){
        return tp.copyFolderToOtherFolder(sourceFolderName, destFolderName);
    }
    
     /**
     *移动文件到另外一个文件夹
     * 参数：文件名称，路径名称
     */
    public boolean moveFileToOtherFolder(String fileName,String destFolderName){
        return tp.moveFileToOtherFolder(fileName, destFolderName);
    }
    
    /**
     *移动一个文件夹到另外一个文件夹
     * 参数：源文件夹名称，目标文件夹名称
     */
    public boolean moveFolderToOtherFolder(String sourceFolderName,String destFolderName){
        return tp.moveFolderToOtherFolder(sourceFolderName, destFolderName);
    }
          
   
    
}
