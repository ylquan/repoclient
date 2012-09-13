/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repoclient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author quanyuelong
 */
public class FTPDownLoadMngThread implements Runnable {

    String localFileNames[];
    String remoteFileNames[];
    int maxThreadNum = 2;
    int tasksNum = 0;
    int threadNum = 0;
    String username;
    String password;
    int port;
    String host;

    FTPDownLoadMngThread(String localFileNames[], String remoteFileNames[], String username, String password, String host, int port) {
        this.localFileNames = localFileNames;
        this.remoteFileNames = remoteFileNames;
        this.tasksNum = localFileNames.length;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    
    public void run() {

        if (tasksNum < maxThreadNum) {
            Thread ts[] = new Thread[tasksNum];
            ExecutorService pool = Executors.newFixedThreadPool(maxThreadNum);
            FTPDownLoadThread ftpdownloadthreads[] = new FTPDownLoadThread[tasksNum];

            for (int i = 0; i < tasksNum; i++) {

                ftpdownloadthreads[i] = new FTPDownLoadThread();
                


                String localfilename[] = new String[1];
                localfilename[0] = localFileNames[i];

                String remotefilename[] = new String[1];
                remotefilename[0] = remoteFileNames[i];
                ftpdownloadthreads[i].setLocalFileName(localfilename);
                ftpdownloadthreads[i].setRemoteFileName(remotefilename);
                ftpdownloadthreads[i].ftpProtocol.connect(username, port, username, password);
                ts[i] = new Thread(ftpdownloadthreads[i]);

            }
            for (int i = 0; i < tasksNum; i++) {

                pool.execute(ts[i]);

            }


            pool.shutdown();

        } else {

            int perThreadDealTasksNum = tasksNum / maxThreadNum;//ÿ���߳���Ҫִ�е��������������һ���̵߳��������ܲ���perThreadDealTasksNum��
            Thread ts[] = new Thread[maxThreadNum];//����maxThreadNum���߳�
            ExecutorService pool = Executors.newFixedThreadPool(maxThreadNum);//�����̳߳�
            FTPDownLoadThread ftpdownloadthreads[] = new FTPDownLoadThread[maxThreadNum];//����maxThreadNum��FTPDownloadThread Ȼ��tasksNumƽ�������maxThreadNum��ftpdownloadthread
            for (int i = 0; i < maxThreadNum; i++) {

                ftpdownloadthreads[i] = new FTPDownLoadThread();
                FtpProtocol ftpprotocol = new FtpProtocol(host, port, username, password);


                String localfilename[] = new String[perThreadDealTasksNum];//����perThreadDealTasksNum���ȵ�String ������������Ҫ�������ǰ�߳�Ҫ������ļ���Ϣ
                for (int j = 0; j < perThreadDealTasksNum; j++) {
                    if (i * perThreadDealTasksNum + j < tasksNum) {
                        localfilename[j] = localFileNames[i + j];
                    } else {
                        break;
                    }
                }

                String remotefilename[] = new String[perThreadDealTasksNum];
                for (int j = 0; j < perThreadDealTasksNum; j++) {
                    if (i * perThreadDealTasksNum + j < tasksNum) {
                        remotefilename[j] = remoteFileNames[i + j];
                    } else {
                        break;
                    }
                }

                ftpdownloadthreads[i].setLocalFileName(localfilename);
                ftpdownloadthreads[i].setRemoteFileName(remotefilename);
                ftpdownloadthreads[i].setArgs(username, password, host, port);
                ts[i] = new Thread(ftpdownloadthreads[i]);

            }
            for (int i = 0; i < maxThreadNum; i++) {

                pool.execute(ts[i]);

            }

            pool.shutdown();

        }

    }
}
