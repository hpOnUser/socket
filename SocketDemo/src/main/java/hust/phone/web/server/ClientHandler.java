package hust.phone.web.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientHandler extends Thread{
	 Socket socket = null;
//	 Map<String, Socket> sessionMap = null;//手机
//	 public ClientHandler(Socket socket, Map<String, Socket> sessionMap)
//	 {
//		 this.socket=socket;
//		 this.sessionMap=sessionMap;
//	 }
//	 //将客户端的通信存入到集合中
//	 private void putIn(String key,Socket socket)
//	 {
//		 synchronized (this) {
//			 sessionMap.put(key, socket);
//			
//		}
//	 }
//	 //将给定的从共享集合中删除
//	 private synchronized void remove(String key)
//	 {
//		 sessionMap.remove(key);
//	 }
//	 //解析信息中的信息，得到id
//	 private String getClientId()
//	 {
//		 try {
//			 //读取客户端发来的消息
//			BufferedReader bReader = new BufferedReader(
//					new InputStreamReader(socket.getInputStream(), "UTF-8"));
//			//给客户端反馈消息
//			PrintWriter ipw = new PrintWriter(
//					new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);
//			//客户端发来的消息
//			String clientId = bReader.readLine();
//			return clientId;
//			
//			 
//		 }catch (Exception e) {
//			// TODO: handle exception
//		}
//		 return null;
//	 }
//	 
//	 public void run()
//	 {
//		 try {
//			 
//			 //先将客户端的信息保存到sessionMap 中
//			 String id=getClientId();
//			 putIn(id, socket);
//			 System.out.println("将客户端"+id+"放入到了map中");
//			 Thread.sleep(100);
//			 //读取客户端消息
//			BufferedReader bReader = new BufferedReader(
//					new InputStreamReader(socket.getInputStream(), "UTF-8"));
//			System.out.println("服务器接收客户端发的消息");
//			String message =null;
//			while((message=bReader.readLine())!=null)
//			{
//				
//				int index =message.indexOf(":");
//				String toclientid = message.substring(1,index);
//				System.out.println(message);
//				String info=message.substring(index+1,message.length());
//				  // 发送数据
//                try {
//                    Socket targetSocket = sessionMap.get(toclientid);
//                    if(targetSocket!=null)
//                    {
//                    	//特定用户的输出流，将数据转发
//                    	PrintWriter ipw = new PrintWriter(
//            					new OutputStreamWriter(targetSocket.getOutputStream(), "UTF-8"),true);
//                       ipw.println(info);
//                        System.out.println("服务端已转发");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//				
//			} 
//		 }catch (Exception e) {
//			// TODO: handle exception
//		}
//	 }
//	 
	 Map<String, Socket> phonesessionMap = null;//手机
	 Map<String, Socket> planesessionMap = null;//无人机
	 public ClientHandler(Socket socket, Map<String, Socket> phonesessionMap,Map<String, Socket> planesessionMap)
	 {
		 this.socket=socket;
		 this.phonesessionMap=phonesessionMap;
		 this.planesessionMap=planesessionMap;
	 }
	 //解析信息中的信息，得到id
	 private String getClientId()
	 {
		 try {
			 //读取客户端发来的消息
			BufferedReader bReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), "UTF-8"));
			//客户端发来的消息
			String clientId = bReader.readLine();
			return clientId;
			
			 
		 }catch (Exception e) {
			// TODO: handle exception
		}
		 return null;
	 }
	 public void run()
	 {
		 try {
			 
			 //先将客户端的信息保存到sessionMap 中
			 String id=getClientId();
			 if(id.startsWith("phone"))
			 {
				 
				 //来自手机端的信息放入到phonesessionMap中：phone无人机编号:-->判断之前有没有socket ,有的话结束掉后在放入当前的socket
				 int index =id.indexOf(":");
				 String toclientid = id.substring(5,index);
//				 if(phonesessionMap.containsKey(toclientid))
//				 {
//					 phonesessionMap.get(toclientid).close();
//				 }
				 phonesessionMap.put(toclientid, socket);
				 System.out.println("将手机客户端"+toclientid+"放入到了phonesessionMap中");
				 
			 }else
			 {
				 //来自无人机端的信息放入到planesessionMap中:plane无人机编号：
				 int index =id.indexOf(":");
				 String toclientid = id.substring(5,index);
//				 if(planesessionMap.containsKey(toclientid))
//				 {
//					 planesessionMap.get(toclientid).close();
//				 }
				 planesessionMap.put(toclientid, socket);
				 System.out.println("将无人机客户端"+toclientid+"放入到了planesessionMap中");
			 }
			 Thread.sleep(100);
			 //读取客户端消息
			BufferedReader bReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), "UTF-8"));
			System.out.println("服务器接收客户端发的消息");
			String message =null;
			while((message=bReader.readLine())!=null)
			{
				//接收来自客户端的消息
				int index =message.indexOf(":");
				String toclientid = message.substring(5,index);//获得编号
				String info=message.substring(index+1,message.length());
				  // 发送数据
                try {
                	Socket targetSocket =null;
                	if(message.startsWith("phone"))
                	{
                		//来自手机端的信息要把信息发给无人机，查询planeSessionmap中的key=无人机编号
                		 targetSocket = planesessionMap.get(toclientid);
                		
                	}
                	else {
                		//来自无人机的信息，要把信息发给手机，查询phoneSessionMap 中和key=无人机的编号
                		targetSocket = phonesessionMap.get(toclientid);
                	}
                    if(targetSocket!=null)
                    {
                    	//特定用户的输出流，将数据转发
                    	PrintWriter ipw = new PrintWriter(
            					new OutputStreamWriter(targetSocket.getOutputStream(), "UTF-8"),true);
                       ipw.println(info);
                        System.out.println("服务端已转发");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
				
			} 
		 }catch (Exception e) {
			// TODO: handle exception
		}
	 }
}
