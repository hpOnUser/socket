package hust.phone.web.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;



//创建生产socket线程类
public class SocketThread extends Thread{
	  // socket服务
    ServerSocket serverSocket = null;
    // 用来存放已连接的客户端的socket会话
    static Map<Integer, Socket> sessionMap = new HashMap<Integer, Socket>();
    
    public SocketThread(ServerSocket serverSocke)
	{
		if(null==serverSocke)
		{
			try {
				//创建端口号
				this.serverSocket = new ServerSocket(8888);
				System.err.println("socket start");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("SocketThread 创建socket出错");
				e.printStackTrace();
			}
		}
	}
    //启动
    public void run()
    {
    	 try {
            
             System.out.println("服务器开启。。。");
             // 客户端编号
             int i = 1;
             // 实现多个客户端连接
             while (true) {
                 Socket socket = serverSocket.accept();
                 System.out.println("客户端" + i + "连接成功。。。");
                 if (socket != null) {
                     // 将socket放入map，key为客户端编号
                     sessionMap.put(i, socket);
                     // 开启线程处理本次会话
                     Thread thread = new Thread(new NotifyHandler(socket, sessionMap));
                     thread.setDaemon(true);
                     thread.start();
                     i++;
                 }
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

	//关闭
	public void closeSocketServer()
	{
		try {
			if(null !=serverSocket && !serverSocket.isClosed())
			{
				serverSocket.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
