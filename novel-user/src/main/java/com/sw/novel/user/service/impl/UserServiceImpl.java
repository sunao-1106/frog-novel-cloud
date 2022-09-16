package com.sw.novel.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.common.exception.BizCodeEnum;
import com.sw.common.utils.AppException;
import com.sw.common.utils.JwtUtil;
import com.sw.common.utils.R;
import com.sw.novel.monitor.entity.LoginUser;
import com.sw.novel.user.dao.UserBookselfMapper;
import com.sw.novel.user.dao.UserMapper;
import com.sw.novel.user.dao.UserPayRecordMapper;
import com.sw.novel.user.entity.UserEntity;
import com.sw.novel.user.exception.AuthenticateException;
import com.sw.novel.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-09-06 16:09:43
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserPayRecordMapper userPayRecordMapper;

    @Autowired
    UserBookselfMapper userBookselfMapper;

    /**
     * 登录接口实现
     * @param user
     * @return R
     */
    @Override
    public String login(UserEntity user) {
        //用户名密码方式进行认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new AuthenticateException("用户名或密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUserDto().getId().toString();
        Object jwt = JwtUtil.createJWT(userId);
        //authenticate存入redis
        redisTemplate.opsForValue().set("login:"+userId, JSON.toJSON(loginUser));
        //把token响应给前端
        return jwt.toString();
    }

    //退出登录接口
    @Override
    public R outLogin() {
        //获取SecurityContextHolder中的认证信息，删除redis中对应的数据即可。
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        System.out.println("================================="+principal);
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //没有登录 默认数据为  anonymousUser  会出现String  == 》 LoginUser 异常
        //获取 用户id
        Long id = loginUser.getUserDto().getId();
        //删除 redis 中用mei户的信息
        redisTemplate.delete("login:"+id);
        return R.ok("退出成功");
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public R Register(UserEntity user) {
        //对密码经行加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                //再把密码后的密码放入User
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //保存成功
        if (save(user)) {
            return R.ok(BizCodeEnum.USER_REGISTER.getMessage());
        }else {
            throw new AppException(BizCodeEnum.USER_REGISTER_ERROR);
        }
    }

    /**
     * 用户信息
     * @return
     */
    @Override
    public R UserInfo() {
//        //获取当前登录用户的 id
//        //通过解析token 获取 用户的id
//        String userId = commUserMin.getUserId();
//        if (  userId!=null  ){
//            System.out.println(commUserMin.getUserId());
//            LambdaQueryWrapper<UserEntity> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.eq(UserEntity::getId,userId);
//            UserEntity user = userMapper.selectOne(queryWrapper);
//            //存放到返回vo中
//            UserVo userVo = new UserVo();
//            //昵称
//            userVo.setNickname(user.getNickname());
//            //头像
//            userVo.setAvatar(user.getAvatar());
//
//            UserPayRecordEntity userPayRecord = userPayRecordMapper.selectById(userId);
//            //会员时间大于0
//            if (userPayRecord.getPayAmount()>0){
//                 //是会员
//                 userVo.setVip(0);
//                 //VIP到期时间
//                Date date = new Date();
//                date.setTime(date.getTime()+userPayRecord.getPayAmount()*24*60*60*1000);
//                userVo.setPayAmount(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
//            }else{
//                //不是会员
//                userVo.setVip(1);
//                userVo.setPayAmount(null);
//            }
//            LambdaQueryWrapper<UserBookselfEntity> bookselfLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            bookselfLambdaQueryWrapper.eq(UserBookselfEntity::getUserId,userId);
//            System.out.println(userVo.toString());
//
//            HashMap<String, Object> hashMap = new HashMap<>();
//            hashMap.put("nickname",userVo.getNickname());
//            hashMap.put("avatar",userVo.getAvatar());
//            hashMap.put("payAmount",userVo.getPayAmount());
//            hashMap.put("vip",userVo.getVip());
//            return R.ok(hashMap);
//
//        }else {
//          throw  new AppException(500,"查询用户 token 异常");
//        }

return null;
    }

    /**
     * 用户小说的浏览记录
     * @return
     */
    @Override
    public R bookHistory() {
//        UserHistory history = new UserHistory();
//        //获取token中用户id
//        String userId = commUserMin.getUserId();
//        //获取user_bookSelf 中的 用户对应小说的 上次阅读章节
//        LambdaQueryWrapper<UserBookselfEntity> userBookselfWrapper = new LambdaQueryWrapper<>();
//        userBookselfWrapper.eq(UserBookselfEntity::getUserId,userId);
//        List<UserBookselfEntity> userBookselves = userBookselfMapper.selectList(userBookselfWrapper);
//        //通过user_bookSelf 的章节 id 来查询 对应小说（book_chapter）的章节名字
//        userBookselves.stream().forEach(userBookself -> {
//
//            //批量查询
//
//
//        });
//        //通过user_bookSelf 表的 book_id 查询book_info表中 信息
//
//        //通过 book——info 查询分类表的名字
//
//
        return null;
    }
}

