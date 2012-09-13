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
public class LocalFileMngServlet {
    FileOperation localfileoperation = new LocalFileOperation();
    //创建文件
    public boolean createFile(String fileName,String content){
     return   localfileoperation.createFile(fileName,content);
    }
    
    //创建文件夹
    public boolean createFolder(File folderName){
    return localfileoperation.createFolder(folderName);
    }
    
    //删除文件
    public void  deleteFile(String fileName)    {
         localfileoperation.deleteFile(fileName);
    }
    //删除文件夹
    public void deleteFolder(String folderName){
         localfileoperation.deleteFolder(folderName);
    }
    
    //重命名文件
    public boolean renameFile(String oldFileName,String newFileName){
        return localfileoperation.renameFile(oldFileName, newFileName);
    }
    
    //重命名文件夹
    public boolean renameFolder(String oldFolderName,String newFolderName){
        return localfileoperation.renameFolder(oldFolderName, newFolderName);
    }
    
    //复制文件到其他文件夹
    public void copyFileToOtherFolder(String fileName,String folderName){
         localfileoperation.copyFileToOtherFolder(fileName, folderName);
    }
    
    //复制文件夹到其他文件夹
    public void copyFolderToOtherFolder(String folderName,String destFolderName){
         localfileoperation.copyFolderToOtherFolder(folderName, destFolderName);
    }
    
    //移动文件到其他文件夹
    public void removeFileToOtherFolder(String fileName,String folderName){
         localfileoperation.removeFileToOtherFolder(fileName, folderName);
    }
    
    //移动文件夹到其他文件夹
    public void removeFolderToOtherFolder(String folderName,String otherFolderName){
         localfileoperation.removeFolderToOtherFolder(folderName, otherFolderName);
    }
      
    //获取当前文件夹中的文件列表
    public String[] getFileList(String folder, String suffix){
        return localfileoperation.getFileList(folder,suffix);
    }
    
    //获取文件大小
     public  long getFileSize(String distFilePath){
         return localfileoperation.getFileSize(distFilePath);
     }
    
  
    //判断文件是否存在
    public  boolean isExist(String filePath){
        return localfileoperation.isExist(filePath);
    }
}
