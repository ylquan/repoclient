/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repoclient;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LocalFileOperation implements FileOperation {

    //待讨论[通过测试]
    public boolean createFile(String fileName,String content) {
       
		
		OutputStream output = null;// 输出字节流
		OutputStreamWriter outputWrite = null;// 输出字符流
		PrintWriter print = null;// 输出缓冲区
		try {
			output = new FileOutputStream(fileName);
			outputWrite = new OutputStreamWriter(output);
			print = new PrintWriter(outputWrite);
			print.print(content);
			print.flush();// 一定不要忘记此句，否则数据有可能不能被写入文件
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
       
        return true;
    }

    //[通过测试]
    public boolean createFolder(File folderName) {
        try {
            FileUtils.forceMkdir(folderName);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //删除文件 [通过测试]
    public void deleteFile(String targetPath) {
        File targetFile = new File(targetPath);
        if (targetFile.isDirectory()) {
            try {
                FileUtils.deleteDirectory(targetFile);
            } catch (IOException ex) {
                Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (targetFile.isFile()) {
            targetFile.delete();
        }
    }

    //删除文件夹 [通过测试]
    public void deleteFolder(String targetPath) {

        File targetFile = new File(targetPath);
        if (targetFile.isDirectory()) {
            try {
                FileUtils.deleteDirectory(targetFile);
            } catch (IOException ex) {
                Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (targetFile.isFile()) {
            targetFile.delete();
        }
    }

    //    重命名文件 [通过测试]
    public boolean renameFile(String oldFileName, String newFileName) {
//       String newFilePath = StringToolkit.formatPath(StringToolkit.getParentPath(resFilePath) + "/" + newFileName);
        File resFile = new File(oldFileName);
        File newFile = new File(newFileName);
        return resFile.renameTo(newFile);
    }

    //    重命名文件夹 [通过测试]
    public boolean renameFolder(String oldFolderName, String newFolderName) {
        File resFile = new File(oldFolderName);
        File newFile = new File(newFolderName);
        return resFile.renameTo(newFile);
    }

    //    获取文件大小 [通过测试]
    public long getFileSize(String distFilePath) {
        File distFile = new File(distFilePath);
        if (distFile.isFile()) {
            return distFile.length();
        } else if (distFile.isDirectory()) {
            return FileUtils.sizeOfDirectory(distFile);
        }
        return -1L;
    }

    //判断文件或文件夹是否存在[通过测试]
    public boolean isExist(String filePath) {
        return new File(filePath).exists();
    }

    //复制文件到其他文件夹[通过测试]
    public void copyFileToOtherFolder(String resFilePath, String distFolder) {
        File resFile = new File(resFilePath);
        File distFile = new File(distFolder);
        if (resFile.isDirectory()) {
            try {
                FileUtils.copyFileToDirectory(resFile, distFile);
                

            } catch (IOException ex) {
                Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (resFile.isFile()) {
            try {
                FileUtils.copyFileToDirectory(resFile, distFile);
                FileUtils.copyFileToDirectory(resFile, distFile);
            } catch (IOException ex) {
                Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //复制文件夹到其他文件夹[通过测试]
    public void copyFolderToOtherFolder(String resFilePath, String distFolder) {
        File resFile = new File(resFilePath);
        File distFile = new File(distFolder);
        if (resFile.isDirectory()) {
            try {
                FileUtils.copyFileToDirectory(resFile, distFile);

            } catch (IOException ex) {
                Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (resFile.isFile()) {
            try {
                FileUtils.copyFileToDirectory(resFile, distFile);
            } catch (IOException ex) {
                Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //移动文件到其他文件夹[通过测试]
    public void removeFileToOtherFolder(String resFilePath, String distFolder) {
        File resFile = new File(resFilePath);
        File distFile = new File(distFolder);
        if (resFile.isDirectory()) {
            try {
                FileUtils.copyFileToDirectory(resFile, distFile);
                this.deleteFile(resFilePath);
            } catch (IOException ex) {
                Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (resFile.isFile()) {
            try {
                FileUtils.copyFileToDirectory(resFile, distFile);
                this.deleteFolder(resFilePath);
            } catch (IOException ex) {
                Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //移动文件夹到其他文件夹[通过测试]
    public void removeFolderToOtherFolder(String resFilePath, String distFolder) {
        File resFile = new File(resFilePath);
        File distFile = new File(distFolder);
        if (resFile.isDirectory()) {
            try {
                FileUtils.copyFileToDirectory(resFile, distFile);
                this.deleteFile(resFilePath);
            } catch (IOException ex) {
                Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (resFile.isFile()) {
            try {
                FileUtils.copyFileToDirectory(resFile, distFile);
                this.deleteFolder(resFilePath);
            } catch (IOException ex) {
                Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //获取当前文件夹下的文件列表[通过测试]若suffix为空则是将所有文件列出来，若指定了类型则只列该类型的文件
    public String[] getFileList(String folder, String suffix) {
       System.out.println("-------------测试结果-----------------");
        File dir = new File(folder);
        String[] files = dir.list(DirectoryFileFilter.INSTANCE);
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i]);
        }
       System.out.println("-------------结果-----------------");
       IOFileFilter fileFilter1 = new SuffixFileFilter(suffix);
       IOFileFilter fileFilter2 = new NotFileFilter(DirectoryFileFilter.INSTANCE);
       
        
       FilenameFilter filenameFilter = new AndFileFilter(fileFilter1, fileFilter2);
     
        return new File(folder).list(filenameFilter);
    }
    
      
    //获取当前文件夹下的文件夹列表[已通过测试]  
    public String[] getFolderList(String folder) {
       
        File dir = new File(folder);
        String[] files = dir.list(DirectoryFileFilter.INSTANCE);
        return files;
    }
}
