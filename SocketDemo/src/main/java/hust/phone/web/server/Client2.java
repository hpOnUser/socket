package hust.phone.web.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


//模拟无人机，一直发数据和接收数据
public class Client2 {
	 InputStreamReader input = null;
	 Socket socket = null;
	 public void socketStart() {
		 try {
			  socket = new Socket("127.0.0.1", 8888);
			  new Thread() {
	                public void run() {
	                    try {
	                        while (true) {
	                        	BufferedReader bReader = new BufferedReader(
	                					new InputStreamReader(socket.getInputStream(), "UTF-8"));
	                           
	                            System.out.println("返回数据：" + bReader.readLine());
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }.start();
	            System.out.println("客户端发送消息");
	            Scanner scanner = new Scanner(System.in);
	            //发送消息
	            PrintWriter ipw = new PrintWriter(
    					new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);
	            
	        	while(true) {
    				String msg=scanner.nextLine();
    				ipw.println(msg);
    				System.out.println("客户端输出"+msg);
    				System.out.println("已发送");
    			}
            
			 
		 }catch (Exception e) {
			// TODO: handle exception
		}
	 }
	 
	   public static void main(String[] args) {
	        new Client2().socketStart();
	    }
}
