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
public class FTPUploadMngThread implements Runnable {
    String localFileNames[];
    String remoteFileNames[];
    int maxThreadNum = 2;
    int tasksNum = 0;
    int threadNum = 0;
    String username;
    String password;
    int port;
    String host;

    FTPUploadMngThread(String localFileNames[], String remoteFileNames[], String username, String password, String host, int port) {
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
            FTPUploadThread ftpuploadthreads[] = new FTPUploadThread[tasksNum];

            for (int i = 0; i < tasksNum; i++) {

                ftpuploadthreads[i] = new FTPUploadThread();
               


                String localfilename[] = new String[1];
                localfilename[0] = localFileNames[i];

                String remotefilename[] = new String[1];
                remotefilename[0] = remoteFileNames[i];
                ftpuploadthreads[i].setLocalFileName(localfilename);
                ftpuploadthreads[i].setRemoteFileName(remotefilename);
                ftpuploadthreads[i].ftpProtocol.connect(username, port, username, password);
                ts[i] = new Thread(ftpuploadthreads[i]);

            }
            for (int i = 0; i < tasksNum; i++) {

                pool.execute(ts[i]);

            }


            pool.shutdown();

        } else {

            int perThreadDealTasksNum = tasksNum / maxThreadNum;//姣忎釜绾跨▼闇�鎵ц鐨勪换鍔℃暟閲忥紙鏈�悗涓�釜绾跨▼鐨勬暟閲忓彲鑳戒笉鏄痯erThreadDealTasksNum锛�
            Thread ts[] = new Thread[maxThreadNum];//澹版槑maxThreadNum涓嚎绋�
            ExecutorService pool = Executors.newFixedThreadPool(maxThreadNum);//鍒涘缓绾跨▼姹�
            FTPUploadThread ftpuploadthreads[] = new FTPUploadThread[maxThreadNum];//澹版槑maxThreadNum涓狥TPDownloadThread 鐒跺悗灏唗asksNum骞冲潎鍒嗛厤缁檓axThreadNum涓猣tpdownloadthread
            for (int i = 0; i < maxThreadNum; i++) {

                ftpuploadthreads[i] = new FTPUploadThread();
               


                String localfilename[] = new String[perThreadDealTasksNum];//澹版槑perThreadDealTasksNum闀垮害鐨凷tring 鏁扮粍鐢ㄦ潵淇濆瓨瑕佷紶鍏ョ粰褰撳墠绾跨▼瑕佸鐞嗙殑鏂囦欢淇℃伅
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

                ftpuploadthreads[i].setLocalFileName(localfilename);
                ftpuploadthreads[i].setRemoteFileName(remotefilename);
                ftpuploadthreads[i].setArgs(username, password, host, port);
                ts[i] = new Thread(ftpuploadthreads[i]);

            }
            for (int i = 0; i < maxThreadNum; i++) {

                pool.execute(ts[i]);

            }

            pool.shutdown();

        }

    }
}
