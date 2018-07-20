package hust.phone.web.server;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.task1.msg_task1_types;

import hust.phone.mapper.pojo.Plane;


//模拟手机接收数据
public class MobileClient {
	 InputStream input = null;
	 Socket socket = null;
	 public void socketStart() {
		 try {
			  socket = new Socket("120.79.182.46", 8888);
			  new Thread() {
	                public void run() {
	                    try {
	                        while (true) {
	                        	input = socket.getInputStream();
	                             ObjectInputStream ois = new ObjectInputStream(input);
	                             Plane p = (Plane) ois.readObject();
	                             System.out.println("客户端收到消息"+p.toString());
	                            
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }.start();
	            System.out.println("客户端发送消息");
	            //发送消息
	            //采用打包的方式
	            OutputStream out=socket.getOutputStream();
	            //准备数据，先模拟只发一次
    			msg_task1_types msg= new msg_task1_types();
    			msg.number =18;
    			//设置为手机端
    			msg.flag=0;
    			//发送数据包
    			MAVLinkPacket pack = msg.pack();
    			byte[] encodePacket = pack.encodePacket();
    			out.write(encodePacket);
		 }catch (Exception e) {
			// TODO: handle exception
		}
	 }
	 
	   public static void main(String[] args) {
	        new MobileClient().socketStart();
	    }
}
