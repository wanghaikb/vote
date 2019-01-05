package cn.wh.service;

import cn.wh.dao.SubjectDao;
import cn.wh.pojo.Item;
import cn.wh.pojo.Options;
import cn.wh.pojo.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService{

    @Autowired
    private SubjectDao subjectDao;

    @Override
    public List<Subject> getAllSub(Subject sub) {
        return subjectDao.getAllSub(sub);
    }

    @Override
    public List<Options> getContent(int id) {
        return subjectDao.getContent(id);
    }

    @Override
    public int isVote(Item item) {
        return subjectDao.isVote(item);
    }

    @Override
    public int voteing(Item item) {
        return subjectDao.voteing(item);
    }

    @Override
    public int addsub(Subject subject) {
        return subjectDao.addsub(subject);
    }

    @Override
    public int addopp(Options options) {
        return subjectDao.addopp(options);
    }

    @Override
    public Subject getBySid(int sid) {
        return subjectDao.getBySid(sid);
    }

    @Override
    public List<Options> getcontbysid(int sid) {
        return subjectDao.getcontbysid(sid);
    }

    @Override
    public int updateType(Subject subject) {
        return subjectDao.updateType(subject);
    }

    @Override
    public int updateContent(Options options) {
        return subjectDao.updateContent(options);
    }

    @Override
    public int deletesbj(int sid) {
        return subjectDao.deletesbj(sid);
    }

    @Override
    public int deleteops(int sid) {
        return subjectDao.deleteops(sid);
    }

    @Override
    public int deleteitem(int sid) {
        return subjectDao.deleteitem(sid);
    }
}
