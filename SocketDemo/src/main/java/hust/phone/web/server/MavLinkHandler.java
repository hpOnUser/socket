package hust.phone.web.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Parser;
import com.MAVLink.task1.msg_task1_types;

import hust.phone.mapper.pojo.Plane;;

public class MavLinkHandler extends Thread{
	 Socket socket = null;
	 Map<Integer, Socket> phonesessionMap = null;//手机
	 Map<Integer, Socket> planesessionMap = null;//无人机
	 public MavLinkHandler(Socket socket, Map<Integer, Socket> phonesessionMap,Map<Integer, Socket> planesessionMap)
	 {
		 this.socket=socket;
		 this.phonesessionMap=phonesessionMap;
		 this.planesessionMap=planesessionMap;
	 }
	 public void run()
	 {
		 try {
			 InputStream in =socket.getInputStream(); 
			 while(true)
			 {
				 //读取消息
				 byte[]lenbuf = new byte[30];
				 in.read(lenbuf);
				 //解析数据
				 Parser parser = new Parser();
				//先读出包的长度
				int plen = lenbuf[1] & 0x00FF;
				int len = plen +2 +6;
				 for(int i=0;i<len-1;i++)
				 {
					 int code = lenbuf[i] & 0x00FF;
					 parser.mavlink_parse_char(code);
				 }
				 MAVLinkPacket m = parser.mavlink_parse_char(lenbuf[len-1]  & 0x00FF);
				 msg_task1_types message = (msg_task1_types) m.unpack();
				 //得到判别的标识，flag中 m代表手机，p代表无人机
				 int flag=message.flag;
				 int toclientid = message.number;
				 if(flag==0)
				 {
					 //来自手机端的信息放入到phonesessionMap中：phone无人机编号:-->判断之前有没有socket ,有的话结束掉后在放入当前的socket
					 //去无人机编号
					 
					 phonesessionMap.put(toclientid, socket);
					 System.out.println("将手机客户端"+toclientid+"放入到了phonesessionMap中");
					 
				 }else if(flag == 1)
				 {
					 //来自无人机端的信息放入到planesessionMap中:plane无人机编号：
					 planesessionMap.put(toclientid, socket);
					 System.out.println("将无人机客户端"+toclientid+"放入到了planesessionMap中");
				 }
				 //发送数据
				 try {
					 Socket targetSocket =null;
                	if(flag==0)
                	{
                		//来自手机端的信息要把信息发给无人机，查询planeSessionmap中的key=无人机编号
                		 targetSocket = planesessionMap.get(toclientid);
                		 if(targetSocket!=null)
                		 {
                			 //将数据包发送
                			 OutputStream out = targetSocket.getOutputStream();
                			 out.write(lenbuf);
                			 System.out.println("服务端已转发"); 
                		 }
                	}
                	else if(flag ==1){
                		//来自无人机的信息，要把信息发给手机，查询phoneSessionMap 中和key=无人机的编号
                		targetSocket = phonesessionMap.get(toclientid);
                		 if(targetSocket!=null)
                		 {
                			 //将解析的数据发送给手机
                			 //得到经度，维度，高度，无人机编号
                			 Plane p = new Plane();
                			 p.setPlaneid(message.number);
                			 p.setLongitude(message.longitude/(10000000.0));
                			 p.setLatitude(message.latitude/(10000000.0));
                			 p.setAltitude(message.altitude/1000.0);
                			 //将对象发送给手机
                			 OutputStream out = targetSocket.getOutputStream();
                			 ObjectOutputStream oos = new ObjectOutputStream(out);
                			 oos.writeObject(p);
                			 System.out.println("服务端已转发"); 
                		 }
                	}
                
	                
				 }catch(Exception e)
				 {
					 e.printStackTrace();
				 }
				 if (socket.isClosed()) {
                     break;
                 }
				 
			 }
			
		 }catch (Exception e) {
			  e.printStackTrace();
              try {
                  socket.close();
              } catch (IOException e1) {
                  e1.printStackTrace();
              }
		}
	 }
}
