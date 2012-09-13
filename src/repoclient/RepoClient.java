/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repoclient;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPFile;




/**
 *
 * @author quanyuelong
 */
public class RepoClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //娴嬭瘯鏈湴鐨勬枃浠舵搷浣�
//        FileOperation localfileoperation = new LocalFileOperation();
 //-----------------------------涓嬮潰鏄湰鍦版枃浠舵搷浣滅殑娴嬭瘯-----------------------------          
////        //鍒涘缓鏂囦欢 [閫氳繃娴嬭瘯] 寰呰璁烘槸鍚﹀簲璇ュ姞鍙傛暟content
//        String fileName = "E:\\TestLocalFileOperation\\a.txt";
//        String content ="asdfasdfas";
//        localfileoperation.createFile(fileName,content);

        
// //        //鍒涘缓鏂囦欢澶�[閫氳繃娴嬭瘯]
//        File folderName =new File("E:\\TestLocalFileOperation\\testCreateFolder\\a");
//        System.out.println("鍒涘缓鏂囦欢澶�+localfileoperation.createFolder(folderName));       
        

// //        //鍒犻櫎鏂囦欢 [閫氳繃娴嬭瘯]
//        String fileName ="E:\\TestLocalFileOperation\\a.txt";
//        localfileoperation.deleteFile(fileName);              
//        

////        //鍒犻櫎鏂囦欢澶�[閫氳繃娴嬭瘯]
//        String fileName ="E:\\TestLocalFileOperation\\testCreateFolder";
//        localfileoperation.deleteFolder(fileName);              
        
        
        
// ////    閲嶅懡鍚嶆枃浠�[閫氳繃娴嬭瘯]
//        String oldName = "E:\\TestLocalFileOperation\\c.txt";
//        String newName ="E:\\TestLocalFileOperation\\cd.txt";
//        localfileoperation.renameFile(oldName, newName);
               
        
// ////    閲嶅懡鍚嶆枃浠跺す [閫氳繃娴嬭瘯]
//        String oldName = "E:\\TestLocalFileOperation\\asdf";
//        String newName ="E:\\TestLocalFileOperation\\tt";
//        localfileoperation.renameFolder(oldName, newName);       

//        // ////    鑾峰彇鏂囦欢澶у皬[閫氳繃娴嬭瘯]
//
//        String fileName = "E:\\TestLocalFileOperation";
//        System.out.println(fileName + " " + "size:" + " " + localfileoperation.getFileSize(fileName) + "B");  
      
//         鍒ゆ柇鏂囦欢鏄惁瀛樺湪[閫氳繃娴嬭瘯]
//
//     // String fileName = "E:\\TestLocalFileOperation\\cd.txt";娴嬭瘯鏂囦欢鏄惁瀛樺湪
//     String folderName = "E:\\TestLocalFileOperation\\a";//娴嬭瘯鏂囦欢鏄惁瀛樺湪
//     //System.out.println(fileName + " " + "is Exist?" + " " + localfileoperation.isExist(fileName));  
//     System.out.println(folderName + " " + "is Exist?" + " " + localfileoperation.isExist(folderName));          
 
        
//         澶嶅埗鏂囦欢鍒板叾浠栨枃浠跺す[閫氳繃娴嬭瘯]
//
//     String fileName = "E:\\TestLocalFileOperation\\cd.txt";//瑕佸鍒剁殑鏂囦欢
//     String folderName = "E:\\TestLocalFileOperation\\a";//澶嶅埗鍒扮殑鏂囦欢澶�              
//     localfileoperation.copyFileToOtherFolder(fileName, folderName);

        
//         澶嶅埗鏂囦欢澶瑰埌鍏朵粬鏂囦欢澶筟閫氳繃娴嬭瘯]
//     String sourceFolderName = "E:\\TestLocalFileOperation\\a";//瑕佸鍒剁殑鏂囦欢澶�
//     String destFolderName = "E:\\TestLocalFileOperation\\tt";//澶嶅埗鍒扮殑鏂囦欢澶�              
//     localfileoperation.copyFolderToOtherFolder(sourceFolderName, destFolderName);        
        
 //         澶嶅埗鏂囦欢鍒板叾浠栨枃浠跺す[閫氳繃娴嬭瘯]

//     String fileName = "E:\\TestLocalFileOperation\\cd.txt";//瑕佸鍒剁殑鏂囦欢
//     String folderName = "E:\\TestLocalFileOperation\\aaa";//澶嶅埗鍒扮殑鏂囦欢澶�              
//     localfileoperation.removeFileToOtherFolder(fileName, folderName);
     
            
//         绉诲姩鏂囦欢澶瑰埌鍏朵粬鏂囦欢澶筟閫氳繃娴嬭瘯]
//     String sourceFolderName = "E:\\TestLocalFileOperation\\a";//瑕佺Щ鍔ㄧ殑鏂囦欢澶�
//     String destFolderName = "E:\\TestLocalFileOperation\\r";//绉诲姩鐨勭洰鐨勬枃浠跺す               
//     localfileoperation.removeFolderToOtherFolder(sourceFolderName, destFolderName);   
     
 //       鑾峰彇褰撳墠鏂囦欢澶逛笅鐨勬枃浠跺垪琛╗閫氳繃娴嬭瘯]
//     String folder = "E:\\TestLocalFileOperation";//瑕佺Щ鍔ㄧ殑鏂囦欢澶�
//     String suffix = "";//绉诲姩鐨勭洰鐨勬枃浠跺す 
//     //String suffix = "";//绉诲姩鐨勭洰鐨勬枃浠跺す
//     String fileList[] = null;
//     fileList = localfileoperation.getFileList(folder, suffix) ;
//     for(int i=0;i<fileList.length;i++){
//         System.out.println(fileList[i]);
//     }
       
     //鑾峰彇褰撳墠鏂囦欢澶逛笅鐨勬枃浠跺す鍒楄〃[宸查�杩囨祴璇昡   
//     String folder = "E:\\TestLocalFileOperation";
//     String fileList[] = null;
//     fileList = localfileoperation.getFolderList(folder) ;
//     for(int i=0;i<fileList.length;i++){
//         System.out.println(fileList[i]);
//     }
        
        
        
        
        
        
        
        
//-----------------------------涓嬮潰鏄疐TPProtocol鐨勬祴璇�----------------------------        
        
// TODO code application logic here
//        娴嬭瘯FtpProtocol
        String host = "www.ey18.com";
	String username = "quanyuelong";
	String password = "quanyuelong";
        int port = 21;
        FtpProtocol ftpMng = new FtpProtocol(host,port,username,password);
       if( ftpMng.connect(username, port, username, password)){
        
        
           
           
        //娴嬭瘯鏂囦欢鏄惁瀛樺湪fileExists(String)[宸查�杩嘳
         /*String path = "a.txt";
         ftpMng.changeDir("/test/");
         boolean isExists = ftpMng.fileExists(path);     
         System.out.println(isExists);*/
       
      
         
//           
//         //娴嬭瘯鏂囦欢澶规槸鍚﹀瓨鍦╢olderExists(String)[宸查�杩嘳
//         /*String path = "a";
//         ftpMng.changeDir("/test/");
//         boolean isExists = ftpMng.folderExists(path);     
//         System.out.println(isExists);
//        */
//           
//           
//        //娴嬭瘯鍒涘缓鏂囦欢澶筩reateFolder(String)[宸查�杩嘳
//         /*String path = "testcreatfolder";
//         ftpMng.changeDir("/test/");
//         boolean isExists = ftpMng.createFolder(path);     
//         System.out.println(isExists);
//        */
//           
//         //娴嬭瘯鍒犻櫎鏂囦欢deleteFile(String)[宸查�杩嘳
//         /*String path = "a.txt";
//         ftpMng.changeDir("/test");
//         boolean isExists = ftpMng.deleteFile(path);     
//         System.out.println(isExists);
//          */
//         
//           //娴嬭瘯鍒犻櫎鏂囦欢澶筪eleteFile(String)[宸查�杩嘳
//       /*  String path = "c";
//         ftpMng.changeDir("/test");
//         boolean isExists = ftpMng.deleteFolder(path);     
//         System.out.println(isExists);
//        */
//       
//           
//           //ps:鏂囦欢鍜屾枃浠跺す鐨勯噸鍛藉悕鍩烘湰涓婃槸涓�牱鐨勶紝鍥犳鍙互鑰冭檻灏嗚繖涓や釜鍑芥暟鍚堜负涓�釜rename(from,to)
//           //娴嬭瘯閲嶅懡鍚峳enameFolder(String from,String to)[宸查�杩嘳
//        /* String from = "c";
//         String to = "d";
//         ftpMng.changeDir("/test");
//          ftpMng.renameFolder(from, to);     
//         */
//        
//         
//           //娴嬭瘯閲嶅懡鍚峳enameFile(String from,String to)[宸查�杩嘳
//        /* String from = "3.txt";
//         String to = "4.txt";
//         ftpMng.changeDir("/test");
//          ftpMng.renameFolder(from, to);     
//        */
//           
//        
//           
//           
//           //娴嬭瘯鏀瑰彉宸ヤ綔璺緞changeDir(String remotepath)[宸查�杩嘳
//         /*String remotepath = "b";
//         ftpMng.changeDir("/test");
//         ftpMng.changeDir(remotepath);     
//         */ 
//        
//           
//            //娴嬭瘯灏嗕竴涓枃浠剁Щ鍔ㄥ埌鍙﹀涓�釜鏂囦欢澶筸oveFileToOtherFolder(String fileName, String destFolderName)[宸查�杩嘳
//         /*String filename = "4.txt";
//         String destFolder = "c";
//         ftpMng.changeDir("/test");
//         ftpMng.moveFileToOtherFolder(filename, destFolder);     
//         */
//           
//           
//           //娴嬭瘯灏嗕竴涓枃浠跺す绉诲姩鍒板彟澶栦竴涓枃浠跺すmoveFolderToOtherFolder(String fileName, String destFolderName)[宸查�杩嘳
//         /*String filename = "c";
//         String destFolder = "d";
//         ftpMng.changeDir("/test");
//         ftpMng.moveFileToOtherFolder(filename, destFolder); 
//           */
//           
//           
//           //娴嬭瘯灏嗕竴涓枃浠跺鍒跺埌鍙﹀涓�釜鏂囦欢澶筸oveFileToOtherFolder(String fileName, String destFolderName)
//        /* String filename = "4.txt";
//         String destFolder = "d";
//         ftpMng.changeDir("/test");
//         ftpMng.copyFileToOtherFolder(filename, destFolder); 
//          */
//           
//           
//           
//    
//           
//         /*     File localfile =new File("d://testUpload//2.txt");
//              String destFolder = "2.txt";
//         ftpMng.changeDir("/test");
//           //娉ㄦ剰杩欓噷蹇呴』鏄竴涓枃浠跺悕(璇存槑鍦ㄤ笂浼犲嚱鏁颁腑搴旇鍏堝垽鏂綋鍓嶈矾寰勪笅鏄惁鏈夎鏂囦欢锛屽鏋滄湁鍒欑洿鎺ヤ笂浼狅紝鍚﹀垯涓嬪垱寤�
//          try {
//           String result =  ftpMng.uploadFile(destFolder, localfile,0);
//          System.out.println(result);
//           } catch (IOException ex) {
//              Logger.getLogger(RepoClient.class.getName()).log(Level.SEVERE, null, ex);
//          }
//          * 
//          */
//           
//           
//           //娴嬭瘯涓嬭浇[宸查�杩囨祴璇昡
//       String localpath = "E:\\testDownLoadThread\\a.txt";
//       String remotepath="test\\a\\a.txt";//娉ㄦ剰杩欓噷蹇呴』鏄竴涓枃浠跺悕(璇存槑鍦ㄤ笅杞藉嚱鏁颁腑搴旇鍏堝垽鏂綋鍓嶈矾寰勪笅鏄惁鏈夎鏂囦欢锛屽鏋滄湁鍒欑洿鎺ヤ笂浼狅紝鍚﹀垯涓嬪垱寤�
//       ftpMng.download(localpath, remotepath);
    
//           
//           //娴嬭瘯鏄剧ず鏂囦欢鍒楄〃[宸查�杩囨祴璇昡
//       String filePath="test//";
//       FTPFile[] files =  ftpMng.getFilesList(filePath);
//       for(int i=2;i<files.length;i++)
//       {
//           System.out.println(files[i].getName());
//       }
//         */
//         
           
//           娴嬭瘯澶氱嚎绋嬩笅杞藉姛鑳� //瀵逛簬澶у皬涓�鐨勬枃浠朵細鍑洪棶棰�
//       String localfolder1 = "E:\\testDownLoadThread";
//       String localfolder2 = "E:\\testDownLoadThread";
//       String remote1 = "test\\a";    
//       String remote2 = "test\\b";  
//       String localfiles[] = new String[2];
//       localfiles[0]=localfolder1;
//       localfiles[1]=localfolder2;
//       System.out.print(localfiles[0]);
//       String remotefiles[]=new String[2];
//      remotefiles[0] = remote1;    
//      remotefiles[1] = remote2; 
//   
//      
//     FTPDownLoadMngThread ftmngthread = new FTPDownLoadMngThread(localfiles,remotefiles,username,password,host,port);
//      Thread thread = new Thread(ftmngthread);
//      thread.start();
//      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
       /*String localfolder1 = "E://testDownLoadThread//a.txt";
       String localfolder2 = "E://testDownLoadThread//aa.txt";
       String remote1 = "test//b//a.txt";    
       String remote2 = "test//b//aa.txt";  
       String localfiles[] = new String[2];
       localfiles[0]=localfolder1;
       localfiles[1]=localfolder2;
       System.out.print(localfiles[0]);
       String remotefiles[]=new String[2];
      remotefiles[0] = remote1;    
      remotefiles[1] = remote2; 
   
      
      FTPUploadMngThread ftmngthread = new FTPUploadMngThread(localfiles,remotefiles,username,password,host,port);
      Thread thread = new Thread(ftmngthread);
      thread.start();*/
      
      
      
       }else{
      System.out.println("鎵撳紑杩炴帴澶辫触");
       }
       ftpMng.disconnect();


    
    }
  
}
 