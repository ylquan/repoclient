/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repoclient;

import java.io.File;

/**
 *
 * @author quanyuelong
 */
public interface FileOperation {
    //创建文件
    public boolean createFile(String fileName,String content);
    
    //创建文件夹
    public boolean createFolder(File folderName);
    
    //删除文件
    public void deleteFile(String fileName);
    
    //删除文件夹
    public void deleteFolder(String folderName);
    
    //重命名文件
    public boolean renameFile(String oldFileName,String newFileName);
    
    //重命名文件夹
    public boolean renameFolder(String oldFolderName,String newFolderName);
    
    //复制文件到其他文件夹
    public void copyFileToOtherFolder(String fileName,String folderName);
    
    //复制文件夹到其他文件夹
    public void copyFolderToOtherFolder(String folderName,String destFolderName);
    
    //移动文件到其他文件夹
    public void removeFileToOtherFolder(String fileName,String folderName);
    
    //移动文件夹到其他文件夹
    public void removeFolderToOtherFolder(String folderName,String otherFolderName);
    
    //获取文件大小   
    public  long getFileSize(String distFilePath);
    
    //获取当前文件夹的文件列表
    public String[] getFileList(String folder, String suffix);
    //获取当前文件夹下的文件夹列表
    public String[] getFolderList(String folder) ;
    
    //判断文件是否存在
    public  boolean isExist(String filePath);
}
