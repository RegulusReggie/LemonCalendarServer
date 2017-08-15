package Connection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

import Controller.CalendarFactory;
import Controller.EventFactory;
import Controller.GroupFactory;
import Entity.*;
import Util.Commons;
import Util.JSONObject;

public class Server {

    public static void main(String Stmt) {

        DatagramSocket ds = null; //连接对象

        DatagramPacket sendDp; //发送数据包对象

        DatagramPacket receiveDp; //接收数据包对象

        final int PORT = 10010; //端口
        while (true) {
            try{

                //建立连接，监听端口

                ds = new DatagramSocket(PORT);

                System.out.println("服务器端已启动：");

                //初始化接收数据

                byte[] b = new byte[1024];

                receiveDp = new DatagramPacket(b,b.length);

                //接收

                ds.receive(receiveDp);

                //读取反馈内容，并输出

                InetAddress clientIP = receiveDp.getAddress();

                int clientPort = receiveDp.getPort();

                byte[] data = receiveDp.getData();

                int len = receiveDp.getLength();

                System.out.println("客户端IP：" + clientIP.getHostAddress());

                System.out.println("客户端端口：" + clientPort);

                System.out.println("客户端发送内容：" + new String(data,0,len));

                //发送反馈

                byte[] bData = handleRequest(new String(data, 0, len)).getBytes();

                sendDp = new DatagramPacket(bData,bData.length,clientIP,clientPort);

                //发送

                ds.send(sendDp);
            }catch(Exception e){

                e.printStackTrace();

            }finally{

                try{

                    //关闭连接

                    ds.close();

                }catch(Exception e){}
            }
        }
    }

    private static String handleRequest(String request) {
        JSONObject obj = Commons.parseJSONObjectFromString(request);
        JSONObject respobj = new JSONObject();
        switch(Integer.valueOf(obj.getField(Commons.TYPE))) {
            case Commons.REQ_SEARCH_CALENDAR_BY_ID:
                try {
                    Calendar cal = CalendarFactory.searchCalendar(Integer.valueOf(obj.getField(Commons.CALENDAR_ID)));
                    if (cal != null) {
                        respobj = cal.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_CALENDAR));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //EVENT
            case Commons.REQ_SEARCH_EVENT_BY_ID:
                try {
                    Event evt = EventFactory.searchEventByEID(Integer.valueOf(obj.getField(Commons.EVENT_ID)));
                    if (evt != null) {
                        respobj = evt.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_EVENT));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_SEARCH_EVENT_BY_DATE:
                try {
                    Event evt = EventFactory.searchEventByDate(Integer.valueOf(obj.getField(Commons.YEAR)), Integer.valueOf(obj.getField(Commons.MONTH)), Integer.valueOf(obj.getField(Commons.DAY)));
                    if (evt != null) {
                        respobj = evt.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_EVENT));
                    }
                } catch (Exception e) {
                    e.printStackTrace();;
                }
                break;

            case Commons.REQ_INSERT_EVENT:
                try {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_EVENT_ID));
                    respobj.putField(Commons.EVENT_ID, String.valueOf(EventFactory.insertEvent(
                                    Integer.valueOf(obj.getField(Commons.YEAR)),
                                    Integer.valueOf(obj.getField(Commons.MONTH)),
                                    Integer.valueOf(obj.getField(Commons.DAY)),
                                    obj.getField(Commons.DESCRIPTION),
                                    Integer.valueOf(obj.getField(Commons.CALENDAR_ID)))));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_UPDATE_EVENT:
                try {
                    EventFactory.updateEvent(
                            Integer.valueOf(obj.getField(Commons.EVENT_ID)),
                            Integer.valueOf(obj.getField(Commons.YEAR)),
                            Integer.valueOf(obj.getField(Commons.MONTH)),
                            Integer.valueOf(obj.getField(Commons.DAY)),
                            obj.getField(Commons.DESCRIPTION)
                    );
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.SUCCESS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_DELETE_EVENT:
                try {
                    EventFactory.deleteEventWithId(Integer.valueOf(obj.getField(Commons.EVENT_ID)));
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.SUCCESS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            //GROUP
            case Commons.REQ_SEARCH_GROUP_BY_ID:
                try {
                    Group gp = GroupFactory.searchGroup(Integer.valueOf(obj.getField(Commons.GROUP_ID)));
                    if (gp != null) {
                        respobj = gp.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_GROUP));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_SEARCH_GROUP_BY_NAME:
                try {
                    Group gp = GroupFactory.searchGroup(Commons.GROUPNAME);
                    if (gp != null) {
                        respobj = gp.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_GROUP));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            default:
                return "-1";
        }
        return respobj.toString();
    }
}