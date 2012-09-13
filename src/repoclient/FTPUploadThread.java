/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repoclient;

/**
 *
 * @author quanyuelong
 */
public class FTPUploadThread implements Runnable {
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
      ftpProtocol.upload(localFileNames, remoteFileNames);
      
    }
}
