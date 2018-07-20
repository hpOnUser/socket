package hust.phone.web.server;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.task1.msg_task1_types;


//模拟无人机，一直发数据
public class PlaneClient {
	 InputStreamReader input = null;
	 Socket socket = null;
	 public void socketStart() {
		 try {
			  socket = new Socket("120.79.182.46", 8888);
			     //发送消息
	            //采用打包的方式
	            OutputStream out=socket.getOutputStream();
		        //准备数据，先模拟只发5次
	            msg_task1_types msg= new msg_task1_types();
	  			msg.longitude=225454464;
	  			msg.latitude = 333345544;
	  			msg.altitude= 3334445;
	  			msg.number =18;
	  			//设置为无人机端
	  			msg.flag=1;
	  			//发送数据包
	  			MAVLinkPacket pack = msg.pack();
	  			byte[] encodePacket = pack.encodePacket();
	  			int i=5;
	  			while(i>0)
	  			{
		  			out.write(encodePacket);
		  			System.out.println("打包后: "+Arrays.toString(encodePacket));
		  			i--;
		  			Thread.sleep(2000);
	  			}
			 
		 }catch (Exception e) {
			// TODO: handle exception
		}
	 }
	 
	   public static void main(String[] args) {
	        new PlaneClient().socketStart();
	    }
}
