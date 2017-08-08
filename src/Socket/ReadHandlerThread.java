package Socket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

class ReadHandlerThread implements Runnable{
    private Socket client;

    public ReadHandlerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            while(true){
                //读取服务器端数据
                dis = new DataInputStream(client.getInputStream());
                String receive = dis.readUTF();
                System.out.println("服务器端返回过来的是: " + receive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            try {
                if(dis != null){
                    dis.close();
                }
                if(client != null){
                    client = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}