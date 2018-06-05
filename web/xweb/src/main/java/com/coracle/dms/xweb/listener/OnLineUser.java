package com.coracle.dms.xweb.listener;/**//*
* onLineUser类实现HttpSessionBindingListener接口
* onLineUser类将具有HttpSessionBindingListener接口的特有属性
* 那么HttpSessionBindingListener接口的特有属性是什么呢？
* HttpSessionBindingListener接口具有的两个空函数
* public void valueBound(HttpSessionBindingEvent e)
* public void valueUnBound(HttpSessionBindingEvent e)
*
* onLineUser实现一些方法：获取在线用户数
*
*/

import javax.servlet.http.*;
import java.util.*;
// @WebListener
public class OnLineUser implements HttpSessionBindingListener{

    public OnLineUser(){
    }

    private Vector users=new Vector();
    public int getCount(){
        users.trimToSize();//调整Vector users的容量为Vector users的大小
        return users.capacity();//返回users的容量
    }
    public boolean existUser(String userName){
        users.trimToSize();
        boolean existUser=false;
        for (int i=0;i<users.capacity();i++ )
        {
            if (userName.equals((String)users.get(i)))
            {
                existUser=true;
                break;
            }
        }
        return existUser;
    }

    public boolean deleteUser(String userName){
        users.trimToSize();
        if(existUser(userName)){
            int currUserIndex=-1;
            for(int i=0;i<users.capacity();i++){
                if(userName.equals((String)users.get(i))){
                    currUserIndex=i;
                    break;
                }
            }
            if (currUserIndex!=-1){
                users.remove(currUserIndex);
                users.trimToSize();
                return true;
            }
        }
        return false;
    }

    public Vector getOnLineUser()
    {
        return users;
    }

    public void valueBound(HttpSessionBindingEvent e){
        users.trimToSize();
        System.out.println("请求：：：：：：：：：：："+e.getName());
        if(!existUser(e.getName())){
            users.add(e.getName());
            System.out.print(e.getName()+"    登入到系统 "+(new Date()));
            System.out.println("      在线用户数为："+getCount());
        }else{
            System.out.println(e.getName()+"已经存在");
        }
    }

    public void valueUnbound(HttpSessionBindingEvent e){
        users.trimToSize();
        String userName=e.getName();
        deleteUser(userName);
        System.out.print(userName+"    退出系统 "+(new Date()));
        System.out.println("      在线用户数为："+getCount());
    }


}


