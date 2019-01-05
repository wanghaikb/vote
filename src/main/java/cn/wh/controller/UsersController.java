package cn.wh.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import cn.wh.pojo.Item;
import cn.wh.pojo.Options;
import cn.wh.pojo.Subject;
import cn.wh.pojo.Users;
import cn.wh.service.SubjectService;
import cn.wh.service.UsersService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@SessionAttributes(value ={"user","subject"} )
public class UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/regist")
    public String index(Users user, Model model) {
        usersService.regist(user);
        return "login";
    }

    @RequestMapping(value = "/isExist", method = RequestMethod.GET)
    @ResponseBody
    public String isExist(String name) {
        System.out.println(name);
        if (usersService.isexist(name) > 0) {
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/login")
    public String login(Users user, Model model, int pagenum) {
        Users uu = usersService.login(user);
        if (uu != null) {
            model.addAttribute("error", "");
            model.addAttribute(uu.getUsername());
            model.addAttribute("user", uu);
            PageHelper.startPage(pagenum, 2);
            List<Subject> list = subjectService.getAllSub(null);
            PageInfo<Subject> sub = new PageInfo<>(list);
            model.addAttribute("sub", sub.getList());
            model.addAttribute("pageinfo", sub);
            Subject subject = new Subject();
            model.addAttribute("subject", subject);
            return "votelist";
        } else {
            model.addAttribute("user", new Users());
            model.addAttribute("error", "用户或密码输入有误！");
        }
        return "login";
    }

    @RequestMapping(value = "/maintain")
    public String maintain(Model model,@ModelAttribute("user") Users user,int pagenum){
        if(user.getIsAdmin()==0){
            return "admin";
        }
        PageHelper.startPage(pagenum, 2);
        List<Subject> list = subjectService.getAllSub(null);
        PageInfo<Subject> sub = new PageInfo<>(list);
        model.addAttribute("sub", sub.getList());
        model.addAttribute("pageinfo", sub);
        Subject subject = new Subject();
        model.addAttribute("subject", subject);
        return "maintain";
    }

    @RequestMapping(value = "/title")
    public String title(Subject subject, Model model, int pagenum) {
        PageHelper.startPage(pagenum, 2);
        List<Subject> list = subjectService.getAllSub(subject);
        PageInfo<Subject> sub = new PageInfo<>(list);
        model.addAttribute("sub", sub.getList());
        model.addAttribute("pageinfo", sub);
        model.addAttribute("subject", subject);
        return "votelist";
    }

    @RequestMapping(value = "/view")
    public String view(int id, String title, int votes, int options, Model model) {
        List<Options> ops = subjectService.getContent(id);
        model.addAttribute("ops", ops);
        model.addAttribute("title", title);
        model.addAttribute("votes", votes);
        model.addAttribute("options", options);
        return "voteview";
    }

    @RequestMapping(value = "/vote")
    public String vote(Subject subject, Model model) {
        List<Options> ops = subjectService.getContent(subject.getSid());
        model.addAttribute("ops", ops);
        model.addAttribute("sbj", subject);
        return "vote";
    }

    @RequestMapping(value = "/voting")
    public String voteing(@RequestParam("options") int[] options, @ModelAttribute("user") Users user, Item item, Model model) {
        int ss = subjectService.isVote(item);
        System.out.println(ss);
        if (ss > 0) {
            model.addAttribute("user", user);
            return "error";
        }
        System.out.println(item.getIid() + " " + item.getUid() + " " + item.getOid() + " " + item.getSid());
        for (int i : options) {
            item.setOid(i);
            int shu = subjectService.voteing(item);
            System.out.println(shu);
        }
        PageHelper.startPage(1, 2);
        List<Subject> list = subjectService.getAllSub(null);
        PageInfo<Subject> sub = new PageInfo<>(list);
        model.addAttribute("sub", sub.getList());
        model.addAttribute("pageinfo", sub);
        Subject subject = new Subject();
        model.addAttribute("subject", subject);
        return "success";
    }

    @RequestMapping(value = "/addsub")
    public String addsub(@ModelAttribute("user") Users user) {
        if(user.getIsAdmin()==0){
            return "admin";
        }
        return "add";
    }

    @RequestMapping(value = "/addingsub")
    public String addingsub(Subject subject, @RequestParam("content") String[] options, Model model) {
        subjectService.addsub(subject);
        int id=subject.getSid();
        for (String i : options) {
            Options oo = new Options();
            oo.setContent(i);
            oo.setSid(id);
            subjectService.addopp(oo);
        }
        return "addsuccess";
    }

    @RequestMapping(value = "/motifying")
    public String motifying(Subject subject,int sid, @RequestParam("content") String[] ops,@RequestParam("oids") int[] oids,Model model){
        subjectService.updateType(subject);
        for (int i=0;i<ops.length;i++) {
            if (i < oids.length) {
                Options oo = new Options();
                oo.setOid(oids[i]);
                oo.setContent(ops[i]);
                subjectService.updateContent(oo);
            } else {
                Options oo = new Options();
                oo.setContent(ops[i]);
                oo.setSid(sid);
                subjectService.addopp(oo);
            }
        }
        return "mosuccess";
    }

    @RequestMapping(value = "/motify")
    public String motify(int id,Model model,@ModelAttribute("user")Users user){
        Subject subject=subjectService.getBySid(id);
        model.addAttribute("subject", subject);
        List<Options> ops=subjectService.getcontbysid(id);
        model.addAttribute("ops",ops);
        return "motify";
    }

    @RequestMapping("/outLogin")
    public String tologout(HttpSession session, SessionStatus sessionStatus) {
        session.removeAttribute("user");
        session.invalidate();
        sessionStatus.setComplete();
        return "redirect:/login.html";
    }

    @RequestMapping(value = "delete")
    public String delete(int sid,Model model,@ModelAttribute("user")Users user){
        int shu=subjectService.deletesbj(sid);
        if (shu==1){
            subjectService.deleteops(sid);
            subjectService.deleteitem(sid);
        }
        model.addAttribute("user", user);
        PageHelper.startPage(1, 2);
        List<Subject> list = subjectService.getAllSub(null);
        PageInfo<Subject> sub = new PageInfo<>(list);
        model.addAttribute("sub", sub.getList());
        model.addAttribute("pageinfo", sub);
        Subject subject = new Subject();
        model.addAttribute("subject", subject);
        return "maintain";
    }

}

