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

    //������[ͨ������]
    public boolean createFile(String fileName,String content) {
       
		
		OutputStream output = null;// ����ֽ���
		OutputStreamWriter outputWrite = null;// ����ַ���
		PrintWriter print = null;// ���������
		try {
			output = new FileOutputStream(fileName);
			outputWrite = new OutputStreamWriter(output);
			print = new PrintWriter(outputWrite);
			print.print(content);
			print.flush();// һ����Ҫ���Ǵ˾䣬���������п��ܲ��ܱ�д���ļ�
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
       
        return true;
    }

    //[ͨ������]
    public boolean createFolder(File folderName) {
        try {
            FileUtils.forceMkdir(folderName);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(LocalFileOperation.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    //ɾ���ļ� [ͨ������]
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

    //ɾ���ļ��� [ͨ������]
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

    //    �������ļ� [ͨ������]
    public boolean renameFile(String oldFileName, String newFileName) {
//       String newFilePath = StringToolkit.formatPath(StringToolkit.getParentPath(resFilePath) + "/" + newFileName);
        File resFile = new File(oldFileName);
        File newFile = new File(newFileName);
        return resFile.renameTo(newFile);
    }

    //    �������ļ��� [ͨ������]
    public boolean renameFolder(String oldFolderName, String newFolderName) {
        File resFile = new File(oldFolderName);
        File newFile = new File(newFolderName);
        return resFile.renameTo(newFile);
    }

    //    ��ȡ�ļ���С [ͨ������]
    public long getFileSize(String distFilePath) {
        File distFile = new File(distFilePath);
        if (distFile.isFile()) {
            return distFile.length();
        } else if (distFile.isDirectory()) {
            return FileUtils.sizeOfDirectory(distFile);
        }
        return -1L;
    }

    //�ж��ļ����ļ����Ƿ����[ͨ������]
    public boolean isExist(String filePath) {
        return new File(filePath).exists();
    }

    //�����ļ��������ļ���[ͨ������]
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

    //�����ļ��е������ļ���[ͨ������]
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

    //�ƶ��ļ��������ļ���[ͨ������]
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

    //�ƶ��ļ��е������ļ���[ͨ������]
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

    //��ȡ��ǰ�ļ����µ��ļ��б�[ͨ������]��suffixΪ�����ǽ������ļ��г�������ָ����������ֻ�и����͵��ļ�
    public String[] getFileList(String folder, String suffix) {
       System.out.println("-------------���Խ��-----------------");
        File dir = new File(folder);
        String[] files = dir.list(DirectoryFileFilter.INSTANCE);
        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i]);
        }
       System.out.println("-------------���-----------------");
       IOFileFilter fileFilter1 = new SuffixFileFilter(suffix);
       IOFileFilter fileFilter2 = new NotFileFilter(DirectoryFileFilter.INSTANCE);
       
        
       FilenameFilter filenameFilter = new AndFileFilter(fileFilter1, fileFilter2);
     
        return new File(folder).list(filenameFilter);
    }
    
      
    //��ȡ��ǰ�ļ����µ��ļ����б�[��ͨ������]  
    public String[] getFolderList(String folder) {
       
        File dir = new File(folder);
        String[] files = dir.list(DirectoryFileFilter.INSTANCE);
        return files;
    }
}
