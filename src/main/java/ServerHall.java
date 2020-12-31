import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerHall extends Thread {

    InputStream in;
    int outInteger = -1;
    // 构造方法
    public ServerHall(Socket so, int i) {
        try {
            in=so.getInputStream();//获取socket的输入流

            //获取OutputStream[]
            outInteger = i ;
            Server.outputStreams[outInteger] = so.getOutputStream();//获取socket的输出流
            // 注册到outStreamList
           Server.outStreamList.add(outInteger);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            byte[] by =new byte[1024];
            int len = 0;
            while(true) {
                len=in.read(by); // 线程挂起
                System.out.println("java服务器收到消息 长度+" + len);
                // len = -1; 表示客户端断开
                if (len >= 0) {
                    if (len > 1024) return;
                    String s = new String(by, 0, len);
                    System.out.println(outInteger + ":" + new String(by,0,len) + "\n");
                    OutputStream out;
                    //给所有用户发送
                    for (Integer integer : Server.outStreamList) {
                        out = Server.outputStreams[integer];
                        out.write(s.getBytes());
                        out.flush();
                    }
                } else {
                    System.out.println(len + ":客户端结束？");
                    System.out.println(Server.outStreamList.toString());
                    //取消注册
                    Server.outStreamList.remove(outInteger);
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
