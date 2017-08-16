package Connection;

import java.net.*;

import Controller.*;
import Entity.*;
import Util.Commons;
import Util.JSONObject;

public class Server {

    public static void main() {

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
            //CALENDAR
            case Commons.REQ_SEARCH_CALENDAR_BY_ID:
                try {
                    Calendar cal = CalendarFactory.searchCalendar(
                            Integer.valueOf(obj.getField(Commons.CALENDAR_ID))
                    );
                    if (cal != null) {
                        respobj = cal.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_CALENDAR));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.NOT_FOUND));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_SEARCH_CALENDAR_BY_GROUP_YEAR_MONTH:
                try {
                    Calendar cal = CalendarFactory.searchCalendar(
                            Integer.valueOf(obj.getField(Commons.GROUP_ID)),
                            Integer.valueOf(obj.getField(Commons.YEAR)),
                            Integer.valueOf(obj.getField(Commons.MONTH))
                    );
                    if (cal != null) {
                        respobj = cal.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_CALENDAR));
                    }  else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.NOT_FOUND));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_INSERT_CALENDAR:
                try {
                    int cid = CalendarFactory.insertCal(
                            Commons.convertStringToList(obj.getField(Commons.CALENDAR_EVENT_IDS)),
                            Integer.valueOf(obj.getField(Commons.YEAR)),
                            Integer.valueOf(obj.getField(Commons.MONTH))
                    );
                    if (cid != -1) {
                        respobj.putField(Commons.CALENDAR_ID, String.valueOf(cid));
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_CALENDAR_ID));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_UPDATE_CALENDAR_EVENT:
                try {
                    CalendarFactory.updateCalEvent(
                            Integer.valueOf(obj.getField(Commons.CALENDAR_ID)),
                            Commons.convertStringToList(obj.getField(Commons.CALENDAR_EVENT_IDS))
                    );
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.SUCCESS));
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            //EVENT
            case Commons.REQ_SEARCH_EVENT_BY_ID:
                try {
                    Event evt = EventFactory.searchEventByEID(
                            Integer.valueOf(obj.getField(Commons.EVENT_ID))
                    );
                    if (evt != null) {
                        respobj = evt.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_EVENT));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.NOT_FOUND));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_SEARCH_EVENT_BY_DATE:
                try {
                    Event evt = EventFactory.searchEventByDate(
                            Integer.valueOf(obj.getField(Commons.YEAR)),
                            Integer.valueOf(obj.getField(Commons.MONTH)),
                            Integer.valueOf(obj.getField(Commons.DAY))
                    );
                    if (evt != null) {
                        respobj = evt.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_EVENT));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.NOT_FOUND));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();;
                }
                break;

            case Commons.REQ_INSERT_EVENT:
                try {
                    int eid = EventFactory.insertEvent(
                            Integer.valueOf(obj.getField(Commons.YEAR)),
                            Integer.valueOf(obj.getField(Commons.MONTH)),
                            Integer.valueOf(obj.getField(Commons.DAY)),
                            obj.getField(Commons.DESCRIPTION),
                            Integer.valueOf(obj.getField(Commons.CALENDAR_ID))
                    );
                    if (eid != -1) {
                        respobj.putField(Commons.EVENT_ID, String.valueOf(eid));
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_EVENT_ID));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
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
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_DELETE_EVENT:
                try {
                    EventFactory.deleteEventWithId(
                            Integer.valueOf(obj.getField(Commons.EVENT_ID))
                    );
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.SUCCESS));
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            //GROUP
            case Commons.REQ_SEARCH_GROUP_BY_ID:
                try {
                    Group gp = GroupFactory.searchGroup(
                            Integer.valueOf(obj.getField(Commons.GROUP_ID))
                    );
                    if (gp != null) {
                        respobj = gp.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_GROUP));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.NOT_FOUND));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_SEARCH_GROUP_BY_NAME:
                try {
                    Group gp = GroupFactory.searchGroup(
                            obj.getField(Commons.GROUPNAME)
                    );
                    if (gp != null) {
                        respobj = gp.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_GROUP));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.NOT_FOUND));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_UPDATE_GROUP_MEMBER:
                try {
                    GroupFactory.updateGpMember(
                            Integer.valueOf(obj.getField(Commons.GROUP_ID)),
                            Commons.convertStringToList(obj.getField(Commons.MEMBERS_ID))
                    );
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.SUCCESS));
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_INSERT_GROUP:
                try {
                    int gid = GroupFactory.insertGp(
                            obj.getField(Commons.GROUPNAME),
                            Commons.convertStringToList(obj.getField(Commons.MEMBERS_ID)),
                            Integer.valueOf(obj.getField(Commons.OWNERS_ID))
                    );
                    if (gid != -1) {
                        respobj.putField(Commons.GROUP_ID, String.valueOf(gid));
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_GROUP_ID));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;
            //USER
            case Commons.REQ_SEARCH_USER_BY_ID:
                try {
                    User ur = UserFactory.getUserById(
                            Integer.valueOf(obj.getField(Commons.USER_ID))
                    );
                    if (ur != null) {
                        respobj = ur.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_USER));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.NOT_FOUND));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_SEARCH_USER_BY_NAME:
                try {
                    User ur = UserFactory.getUserByName(
                            obj.getField(Commons.USERNAME)
                    );
                    if (ur != null) {
                        respobj = ur.toJSON();
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_USER));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.NOT_FOUND));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_INSERT_USER:
                try {
                    int uid = UserFactory.insertUser(
                            obj.getField(Commons.USERNAME),
                            obj.getField(Commons.PASSWORD)
                    );
                    if (uid != -1) {
                        respobj.putField(Commons.USER_ID, String.valueOf(uid));
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_USER_ID));
                    } else {
                        respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    }
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_INSERTG2C:
                try {
                    GroupToCalendarDB.insertG2C(
                            Integer.valueOf(obj.getField(Commons.GROUP_ID)),
                            Integer.valueOf(obj.getField(Commons.CALENDAR_ID))
                    );
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.SUCCESS));
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_INSERTG2U:
                try {
                    GroupToUserDB.insertG2U(
                            Integer.valueOf(obj.getField(Commons.GROUP_ID)),
                            Integer.valueOf(obj.getField(Commons.USER_ID))
                    );
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.SUCCESS));
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;

            case Commons.REQ_GET_GROUPS_BY_USER_ID:
                try {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.RESPOND_GROUP_IDS));
                    respobj.putField(Commons.GROUP_IDS, Commons.convertListToString(
                            GroupToUserDB.getGroupsByUserId(Integer.valueOf(obj.getField(Commons.USER_ID)))
                    ));
                } catch (Exception e) {
                    respobj.putField(Commons.TYPE, String.valueOf(Commons.FAIL));
                    e.printStackTrace();
                }
                break;
            default:
                respobj.putField(Commons.TYPE, String.valueOf(Commons.REQ_NOT_FOUND));
        }
        return respobj.toString();
    }
}