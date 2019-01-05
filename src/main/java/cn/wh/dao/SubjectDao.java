package cn.wh.dao;

import cn.wh.pojo.Item;
import cn.wh.pojo.Options;
import cn.wh.pojo.Subject;

import java.util.List;

public interface SubjectDao {
    public List<Subject> getAllSub(Subject subject);
    public List<Options> getContent(int id);
    public int isVote(Item item);
    public int voteing(Item item);
    public int addsub(Subject subject);
    public int addopp(Options options);
    public Subject getBySid(int sid);
    public List<Options> getcontbysid(int sid);
    public int updateType(Subject subject);
    public int updateContent(Options options);
    public int deletesbj(int sid);
    public int deleteops(int sid);
    public int deleteitem(int sid);
}
