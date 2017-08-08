package Socket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class WriteHandlerThread implements Runnable{
    private Socket client;

    public WriteHandlerThread(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        BufferedReader br = null;
        try {
            while(true){
                //取得输出流
                dos = new DataOutputStream(client.getOutputStream());
                System.out.print("请输入: \t");
                //键盘录入
                br = new BufferedReader(new InputStreamReader(System.in));
                String send = br.readLine();
                //发送数据
                dos.writeUTF(send);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }  finally{
            try{
                if(dos != null){
                    dos.close();
                }
                if(br != null){
                    br.close();
                }
                if(client != null){
                    client = null;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}