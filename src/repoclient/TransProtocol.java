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
public interface TransProtocol {
    
    /**
     *建立连接
     * 参数：主机名hostname,端口号port,用户名user,密码：password
     */
    public boolean connect(String hostname, int port, String user, String password);//建立连接

    /**
     *关闭连接
     * 参数：
     */
    public void disconnect() ;//断开连接
    
    /**
     *上传文件
     * 参数：request,response
     */
    public String upload(String localfileName,String remotefolderName);//上传
    public String upload(String[] localfileNames,String[] remotefolderNames);//上传    
    /**
     *下载文件
     * 参数：request,response
     */
    public String download(String localfolderName, String remoteFilerName );//下载
    
    public String download(String[] localfileNames,String[] remotefolderNames);//下载
    /**
     *判断文件是否存在
     * 参数：文件名
     */
    public boolean fileExists(String nameOfFile);
    
    /**
     * 判断文件夹是否存在
     * 参数文件夹名称
     */
    public boolean folderExists(String nameOfFolder);
    
    /**
     *创建一个文件夹
     * 参数：要创建的文件夹的名称
     */
    public boolean createFolder(String nameOfFolderToCreate);//创建一个文件夹
    
    /**
     *删除一个文件夹
     * 参数：要删除的文件夹的名称
     */
    public boolean deleteFolder(String nameOfFolderToDelete);//删除一个文件夹
    
     /**
     *修改文件夹名称
     * 参数：要修改的文件夹的新名称，旧名称
     */
    public boolean renameFolder(String oldName,String newName);//修改文件夹名称
  
    /**
     *根据路径改变当前目录
     * 参数：路径名称 String remoteDir
     */
    public boolean changeDir(String remoteDir);//根据路径改变当前目录
    
    /**
     *到父目录
     * 参数：
     */
    public boolean changeToParentDir();//到父目录
    /**
    * 获取文件列表
    * 参数：FTPFile[]
    */
    public void getFilesList(FTPFile[] ftpFile);//获取文件列表
    
    /**
     *删除文件
     * 参数：文件名称
     */
    public boolean deleteFile(String nameOfFileToDelete);//删除文件
       
    /**
     *复制文件到另外一个文件夹
     * 参数：文件名称，路径名称
     */
    public boolean copyFileToOtherFolder(String fileName,String destFolderName);//复制文件到另外一个文件夹
    
    /**
     *复制一个文件夹到另外一个文件夹
     * 参数：源文件夹名称，目标文件夹名称
     */
    public boolean copyFolderToOtherFolder(String sourceFolderName,String destFolderName);//复制一个文件夹到另外一个文件夹
    
     /**
     *移动文件到另外一个文件夹
     * 参数：文件名称，路径名称
     */
    public boolean moveFileToOtherFolder(String fileName,String destFolderName);//移动文件到另外一个文件夹
    
    /**
     *移动一个文件夹到另外一个文件夹
     * 参数：源文件夹名称，目标文件夹名称
     */
    public boolean moveFolderToOtherFolder(String sourceFolderName,String destFolderName);//移动一个文件夹到另外一个文件夹
          
   
}
