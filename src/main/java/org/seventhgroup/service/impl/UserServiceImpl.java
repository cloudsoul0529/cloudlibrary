package org.seventhgroup.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.seventhgroup.dao.BookMapper;
import org.seventhgroup.dao.UserMapper;
import org.seventhgroup.dto.PasswordResult;
import org.seventhgroup.dto.PageResult;
import org.seventhgroup.dto.Result;
import org.seventhgroup.pojo.Book;
import org.seventhgroup.pojo.User;
import org.seventhgroup.service.UserService;
import org.seventhgroup.util.SHA256WithSaltUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author cloudsoul
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BookMapper bookMapper;
    /**
     * 用户登录
     */
    @Override
    public User login(User user) {
        // 获取盐和哈希值
        PasswordResult passwordResult = userMapper.getPasswordResult(user);
        if (passwordResult == null) {
            return null;
        }
        String salt = passwordResult.getPasswordSalt();
        String hash = passwordResult.getPasswordHash();
        // 验证成功则登录
        if (salt != null && hash != null && SHA256WithSaltUtil.verify(user.getPassword(), salt, hash)) {
            return userMapper.login(user);
        }
        return null;
    }

    /**
     * 新增用户
     */
    @Override
    public void addUser(User user) {
        //设置用户状态与注册日期
        user.setStatus(User.ACTIVE);
        if (user.getCreatedate() == null || user.getCreatedate().isEmpty()) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            user.setCreatedate(dateFormat.format(new Date()));
        }
        // 设置盐，计算哈希值
        try {
            byte[] salt = SHA256WithSaltUtil.generate16ByteSalt();
            String hash = SHA256WithSaltUtil.encryptWith16ByteSalt(user.getPassword(), salt);
            String storedSalt = SHA256WithSaltUtil.bytesToBase64(salt);
            user.setPasswordSalt(storedSalt);
            user.setPasswordHash(hash);
            userMapper.addUser(user);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 编辑用户
     */
    @Override
    public void editUser(User user) {
        try {
            //输入值不为空再修改
            if (user.getName() != null && !"".equals(user.getName().trim())) {
                user.setName(user.getName());
            }
            if (user.getEmail() != null && !"".equals(user.getEmail().trim())) {
                user.setEmail(user.getEmail());
            }
            if (user.getPassword() != null && !"".equals(user.getPassword().trim())) {
                byte[] salt = SHA256WithSaltUtil.generate16ByteSalt();
                String hash = SHA256WithSaltUtil.encryptWith16ByteSalt(user.getPassword(),salt);
                String storedSalt = SHA256WithSaltUtil.bytesToBase64(salt);
                user.setPasswordSalt(storedSalt);
                user.setPasswordHash(hash);
            }
            userMapper.editUser(user);
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户注销
     */
    @Override
    public Result delUser(Integer id) {
        User user = this.findById(id);
        Book book = new Book();
        book.setBorrower(user.getName());
        List<Book> borrowedBook = bookMapper.selectMyBorrowed(book);
        //若该用户无借阅未还或未确认的书，执行注销
        if (borrowedBook.isEmpty()) {
            //添加注销时间及状态改为已注销，用户数据仍保存在数据库中
            user.setStatus(User.DELETED);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            user.setDeletedate(dateFormat.format(new Date()));
            userMapper.editUser(user);
            return new Result(true, "注销成功!");
        }
        else{
            return new Result(false, "用户有图书未归还或确认！");
        }
    }

    /**
     * 恢复注销
     */
    @Override
    public void recoverUser(Integer id) {
        //删除注销时间及状态改为已注册
        User user = this.findById(id);
        user.setStatus(User.ACTIVE);
        user.setDeletedate("cancel");
        userMapper.editUser(user);
    }

    /**
     * 搜索用户
     */
    @Override
    public PageResult searchUsers(User user, Integer pageNum, Integer pageSize) {
        //使用分页插件
        PageHelper.startPage(pageNum, pageSize);
        //SQL语句被插件改写，返回pageSize条数据，Page包装
        Page<User> page = userMapper.searchUsers(user);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 根据id查询用户
     */
    @Override
    public User findById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     * 新增、编辑用户时检查已注册的邮箱是否存在
     */
    @Override
    public boolean checkEmail(String email) {
        if (userMapper.checkEmail(email) != null) {
            return true;
        }
        return false;
    }
}
