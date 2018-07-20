package hust.phone.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.phone.mapper.pojo.Messages;
import hust.phone.service.MessageService;
import hust.phone.service.TestService;
import hust.phone.utils.pojo.Message;


@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	private MessageService messageServiceimpl;
//	
//	Socket socket = null;
//    InputStreamReader input = null;
//    InputStream in = null;
//    OutputStream out = null;
//	
//	@Override
//	public String recevie() 
//	{
//		try {
//            socket = new Socket("127.0.0.1", 8888);
//            System.out.println("客户端1启动.......");
//            while (true) {
//                in = socket.getInputStream();
//                ObjectInputStream ois = new ObjectInputStream(in);
//                Message msg = (Message) ois.readObject();
//                System.out.println("返回数据：" + msg.getMsg());
//                
//                //把数据更新到数据库中,数据库发生更新，然后通知给前台发出更新。
//                Messages message =new Messages();
//                message.setContent(msg.getMsg());
//                message.setMessageid("1");
//                messageServiceimpl.updateMessage(message);
//	            }
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	        } 
//        finally {
//            // 关闭流和连接
//            try {
//                in.close();
//                out.close();
//                socket.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//		return null;
//	}
	
	 InputStreamReader input = null;
	 Socket socket = null;
	 //一直接收来自无人机端的数据
	@Override
	public String recevie() {
		 try {
			  socket = new Socket("127.0.0.1", 8888);
			  new Thread() {
	                public void run() {
	                    try {
	                        while (true) {
	                        	BufferedReader bReader = new BufferedReader(
	                					new InputStreamReader(socket.getInputStream(), "UTF-8"));
	                        	String s=bReader.readLine();
	                            System.out.println("返回数据：" + s);
	                          //把数据更新到数据库中,数据库发生更新，然后通知给前台发出更新。
	                          Messages message =new Messages();
	                          message.setContent(s);
	                          message.setMessageid("1");
	                          messageServiceimpl.updateMessage(message);
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }.start();
	            System.out.println("客户端发送消息");
	            //发送消息
	            Scanner scanner = new Scanner(System.in);
	            PrintWriter ipw = new PrintWriter(
   					new OutputStreamWriter(socket.getOutputStream(), "UTF-8"),true);
	            
//           	input = new InputStreamReader(System.in);
//   			String msg = new BufferedReader(input).readLine();
//   			System.out.println("客户端输出"+msg);
//   			
//              ipw.println(msg);
   			
//   				String msg="phone1:";
//   				ipw.println(msg);
//   				while(true)
//   				{
//   					
//   				}
	            while(true) {
    				String msg=scanner.nextLine();
    				ipw.println(msg);
    				System.out.println("客户端输出"+msg);
    				System.out.println("已发送");
    			}
		 }catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	 

}
