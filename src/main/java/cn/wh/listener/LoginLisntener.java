package cn.wh.listener;

import cn.wh.pojo.Users;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class LoginLisntener implements HttpSessionAttributeListener {

    private Map<String, HttpSession> map=new HashMap<String, HttpSession>();


    public void attributeAdded(HttpSessionBindingEvent event) {
        String name = event.getName();
        if (name.equals("user")) {
            Users user = (Users) event.getValue();
            if (map.get(user.getUsername()) != null) {
                HttpSession session = map.get(user.getUsername());
                session.removeAttribute(user.getUsername());
                session.invalidate();
            }
            map.put(user.getUsername(), event.getSession());
        }
    }

    public void attributeRemoved(HttpSessionBindingEvent event) {
        String name = event.getName();
        if (name.equals("user")) {
            Users user = (Users) event.getValue();
            map.remove(user.getUsername());
        }
    }

    public void attributeReplaced(HttpSessionBindingEvent event) {
    }

    public Map<String, HttpSession> getMap() {
        return map;
    }

    public void setMap(Map<String, HttpSession> map) {
        this.map = map;
    }

}