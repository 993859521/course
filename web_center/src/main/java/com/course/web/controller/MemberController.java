package com.course.web.controller;

import com.alibaba.fastjson.JSON;
import com.course.service.domain.dto.*;
import org.springframework.data.redis.core.RedisTemplate;
import com.course.service.service.MemberService;
import com.course.service.util.UuidUtil;
import com.course.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;
@Slf4j
@RestController
@RequestMapping("/member")

public class MemberController {
    public static final String BUSINESS_NAME = "会员";
    @Resource
    private MemberService  memberService;
    @Resource
    private RedisTemplate redisTemplate;

    /***
    * 列表查询
    * @param pageDto
    * @return
    */
    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        return ResponseDto.builder().success(true).content( memberService.selectAll(pageDto)).build();
    }

    /**
    * 增加数据或者更新数据
    * @param  memberDto
    * @return
    */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody MemberDto  memberDto){
         ValidatorUtil.require(memberDto.getMobile(), "手机号");
         ValidatorUtil.length(memberDto.getMobile(), "手机号", 1, 11);
         ValidatorUtil.require(memberDto.getPassword(), "密码");
         ValidatorUtil.length(memberDto.getName(), "昵称", 1, 50);
         ValidatorUtil.length(memberDto.getPhoto(), "头像url", 1, 200);
         ResponseDto responseDto = new ResponseDto();
         memberService.save( memberDto);
         return ResponseDto.builder().success(true).content( memberDto).build();
    }

    /**
    * 删除数据
    * @param id
    * @return
    */
    @DeleteMapping("/delete/{id}")
    public ResponseDto del(@PathVariable String id){
         memberService.delete(id);
         return ResponseDto.builder().success(true).content(id).build();
    }
    /**
     * 登录
     */
    @PostMapping("/login")
    public ResponseDto login(@RequestBody MemberDto memberDto, HttpServletRequest request) {
        log.info("用户登录开始");
        memberDto.setPassword(DigestUtils.md5DigestAsHex(memberDto.getPassword().getBytes()));
        ResponseDto responseDto = new ResponseDto();

        // 根据验证码token去获取缓存中的验证码，和用户输入的验证码是否一致
        // String imageCode = (String) request.getSession().getAttribute(userDto.getImageCodeToken());
        String imageCode = (String) redisTemplate.opsForValue().get(memberDto.getImageCodeToken());
        log.info("从redis中获取到的验证码：{}", imageCode);
        if (StringUtils.isEmpty(imageCode)) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码已过期");
            log.info("用户登录失败，验证码已过期");
            return responseDto;
        }
        if (!imageCode.toLowerCase().equals(memberDto.getImageCode().toLowerCase())) {
            responseDto.setSuccess(false);
            responseDto.setMessage("验证码不对");
            log.info("用户登录失败，验证码不对");
            return responseDto;
        } else {
            // 验证通过后，移除验证码
//            request.getSession().removeAttribute(userDto.getImageCodeToken());
            redisTemplate.delete(memberDto.getImageCodeToken());
        }

        LoginUserDto loginUserDto = memberService.login(memberDto);
        String token = UuidUtil.getShortUuid();
        loginUserDto.setToken(token);
//        request.getSession().setAttribute(Constants.logIN_USER, loginUserDto);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginUserDto), 3600, TimeUnit.SECONDS);
        responseDto.setContent(loginUserDto);
        return responseDto;
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout/{token}")
    public ResponseDto logout(@PathVariable String token) {
        ResponseDto responseDto = new ResponseDto();
//        request.getSession().removeAttribute(Constants.logIN_USER);
        redisTemplate.delete(token);
        log.info("从redis中删除token:{}", token);
        return responseDto;
    }
}
