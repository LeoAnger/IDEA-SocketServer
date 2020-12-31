import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    public static void main(String[] args) {
        try {
            //第一个参数的服务器的ip 第二个参数是端口号
            Socket so=new Socket("127.0.0.1",61666);
            System.out.println("java连接服务器成功...");
            OutputStream out=so.getOutputStream();//获取socket的输出流
            Scanner in=new Scanner(System.in);
            while(true) {
                String s=in.nextLine();
                out.write(s.getBytes());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
