package com.k2data.unittest.example.unittestsample.service;

import com.k2data.unittest.example.unittestsample.base.exceptions.InvalidException;
import com.k2data.unittest.example.unittestsample.base.MessageEnum;
import com.k2data.unittest.example.unittestsample.base.exceptions.ServiceException;
import com.k2data.unittest.example.unittestsample.base.dao.User;
import com.k2data.unittest.example.unittestsample.base.dao.UserMapper;
import com.k2data.unittest.example.unittestsample.domain.MyResponseBody;
import com.k2data.unittest.example.unittestsample.domain.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * Created by cx on 2018-10-08.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value("${fileUpload.img.path}")
    private String IMAGE_FILE_PATH;
    @Autowired
    private UserMapper userDao;

    /**
     * 查询 user 列表
     * @return
     */
    public MyResponseBody<List<User>> listUsers() {
        List<User> users = userDao.selectAllUsers();
        return new MyResponseBody(MessageEnum.OK, users);
    }

    /**
     * 添加 user
     * @param user
     * @return
     */
    public MyResponseBody<User> addUser(User user) {
        user.setUpdatetime(new Date());
        Integer count = userDao.insertSelective(user);
        checkSuccess(count);

        return new MyResponseBody(MessageEnum.OK, user);
    }

    /**
     * 上传文件
     * @param file
     * @param target
     * @throws InvalidException
     * @throws IOException
     */
    private void saveFile(MultipartFile file, String target) throws InvalidException, IOException {
        if (file == null || file.isEmpty() || file.getSize() == 0) {
            throw new InvalidException(MessageEnum.FILE_ERROR);
        }
        if (file.getSize() > 1000000) {
            throw new InvalidException(MessageEnum.FILE_SIZE_ERROR);
        }

        File dest = new File(target);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();// if not, create parent directories recursively
        }
        //save
        file.transferTo(dest);
    }

    /**
     * 上传文件，并关联到 user
     * @param id
     * @param file
     * @return
     * @throws InvalidException
     * @throws IOException
     */
    public MyResponseBody uploadImage(Integer id, MultipartFile file) throws InvalidException, IOException {
        //检查id
        checkExist(id);
        // 获取名称
        String filename = file.getOriginalFilename();
        String target = IMAGE_FILE_PATH + File.separator + new Date().getTime() + "_" + filename;
        logger.debug(target);
        //上传
        saveFile(file, target);
        //更新数据库
        User user = new User().setId(id).setImg(target);
        user.setUpdatetime(new Date());
        int count = userDao.updateByPrimaryKeySelective(user);
        checkSuccess(count);

        return new MyResponseBody(MessageEnum.OK);
    }

    /**
     * 获取指定 id 的 user
     * @param id
     * @return
     */
    public MyResponseBody<User> getOneById(Integer id) {
        checkExist(id);
        User user =  userDao.selectByPrimaryKey(id);
        UserResponse userResponse = new UserResponse(user);
        return new MyResponseBody(MessageEnum.OK, userResponse);
    }

    /**
     * 删除指定 id 的 user
     * @param id
     * @return
     */
    public MyResponseBody deleteOneById(Integer id) {
        checkExist(id);
        int count = userDao.deleteByPrimaryKey(id);
        checkSuccess(count);
        return new MyResponseBody(MessageEnum.OK);
    }

    /**
     * 下载
     * @param id
     * @return
     * @throws FileNotFoundException
     */
    public String download(Integer id) throws FileNotFoundException {
        checkExist(id);
        User user = userDao.selectByPrimaryKey(id);
        String path = user.getImg();
        if (path == null) {
            throw new InvalidException(MessageEnum.NO_DATA_ERROR);
        }
        return path;
    }

    /**
     * 查看某数据是否存在
     * @param id
     * @return
     */
    private int checkExist(Integer id) {
        int count = userDao.chectExist(id);
        if (count == 0) {
            throw new InvalidException(MessageEnum.NO_DATA_ERROR);
        }
        return count;
    }

    /**
     * 查看数据库操作是否成功
     * @param count
     */
    private void checkSuccess(int count) {
        if (count == 0) {
            throw new ServiceException(MessageEnum.DATASOURCE_ERROR);
        }
    }

}
