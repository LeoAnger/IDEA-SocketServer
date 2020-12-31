import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Server {
    static ServerSocket soo;
    // 线程池
    int threadMax = 0;  // 已使用的最大线程下标
    static Thread[] threadArr = new Thread[1000];  // 子线程管理
    public static int outMax = 0; // 已使用的最大out集合下标
    public static Set<Integer> outStreamList = new HashSet<Integer>();
    public static OutputStream[] outputStreams = new OutputStream[1000];    // 数据分发
    public static void main(String[] args) {
        try{
            //在本机创建一个服务器 端口号为61666
            soo=new ServerSocket(61666);
        } catch (IOException io) {
            System.out.println("异常信息：" + io.getMessage());
            if (io.getMessage().equals("Address already in use: JVM_Bind")) {
                System.out.println("返回，要求换一个端口port");
            }
        }
        initServer();
    }

    private static void initServer() {
        try {
            if (soo == null) return;
            System.out.println("java创建服务器成功...");
            Socket so=soo.accept();//等待连接 连接成功才能往下执行

            // 新的线程进来
            // 1.初始化并启动一个子线程
            initRoom(so);
            initServer();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("捕获到异常：" + e.getMessage() + ";" + e.getCause());
            if (e.getMessage().equals("Connection reset")) {
                System.out.println("Connection reset:" + "1.客户端关闭连接 2.服务器网络关闭");
                initServer();
            }
        }
    }

    private static void initRoom(Socket so) {
        Thread thread = new ServerRoom(so, getOneOutList());
        if (threadArr[outMax] == null){
            threadArr[outMax] = thread;
        }
        else {
            initRoom(so);
        }
        Server.outMax ++;
        thread.start();
        System.out.println("子线程" + (outMax - 1) + "已经start." );
    }

    public static int getOneOutList() {
        if (outMax >= 1000) {

        }
        return outMax;
    }
}
