package application;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TestKlient {

	Socket s;
	
	
	public TestKlient(String ip){
		try {
			s=new Socket(ip, 9090);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(String data){
		
		try {
			PrintWriter out = new PrintWriter(s.getOutputStream());
			out.println(data);
	        out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

	}
	
	public void close(){
		try {
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  
}