package kr.code.main.user.controller;

import kr.code.main.common.department.domain.DepartmentVO;
import kr.code.main.common.department.service.DepartmentService;
import kr.code.main.common.position.domain.PositionVO;
import kr.code.main.common.position.service.PositionService;
import kr.code.main.user.domain.entity.UserEntity;
import kr.code.main.user.dto.UserDto;
import kr.code.main.user.dto.UserLoginDTO;
import kr.code.main.user.dto.UserVO;
import kr.code.main.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PositionService positionService;
    private final DepartmentService departmentService;

    //유저(직원)가입
    @GetMapping("/join")
    public ModelAndView dispJoin() {
        return new ModelAndView("/views/user/join");
    }

    //유저가입 처리
    @PostMapping("/join")
    @ResponseBody
    public String execJoin(UserDto userDto) {
        userService.joinUser(userDto);
        return "redirect:/users/join_result";
    }

    @PostMapping("/checkId")
    @ResponseBody
    public String checkUserId(@RequestParam(value="userId") String userId) {
        return userService.findUser(userId) == null ? "success" : "failed";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public ModelAndView dispLogin() {
        return new ModelAndView("/views/user/loginForm");
    }

    // 로그인 처리
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<String> doLogin(@RequestBody UserLoginDTO dto,
                                          HttpServletRequest req) {
        String token = userService.doLogin(dto.getUserId(), dto.getUserPasswd());

        UserVO user = userService.findUser(dto.getUserId());
        req.getSession().setAttribute("user", user);

        return ResponseEntity.ok().body(token);
    }

    // 로그인 결과
    @GetMapping("/login/result")
    public ModelAndView dispLoginResult() {
        return new ModelAndView("/index");
    }

    // 로그아웃 결과
//    @GetMapping("/user/login/result")
//    public ModelAndView dispLogout() {
//        return new ModelAndView("/index");
//    }

    // 내 정보 페이지
    @GetMapping("/info")
    public ModelAndView dispMyinfo() {
        return new ModelAndView("/views/user/myinfo");
    }

    // 권한 페이지
    @GetMapping("/admin")
    public ModelAndView dispAdmin() {
        ModelAndView mav = new ModelAndView("/views/user/admin");

        List<UserEntity> users = userService.findAllUser();
        if (users.size() > 0) {

            List<UserDto> savedUsers = users.stream().map( user -> {
                return UserDto.builder()
                        .userUid(user.getUserUid())
                        .userId(user.getUserId())
                        .userBirth(user.getUserBirth())
                        .userPhone(user.getUserPhone())
                        .userEmail(user.getUserEmail())
                        .userPosition(user.getUserPosition())
                        .userTele(user.getUserTele())
                        .userName(user.getUserName())
                        .userDepart(user.getUserDepart())
                        .userGender(user.getUserGender())
                        .userAuth(user.getUserAuth())
                        .userAddrs(user.getUserAddrs())
                        .build();
            }).toList();

            mav.addObject("userList", savedUsers);

            List<PositionVO> positions = positionService.GetAllPosition();
            mav.addObject("positions", positions);

            List<DepartmentVO> departments = departmentService.GetAllDepartment();
            mav.addObject("departments", departments);
        }

        return mav;
    }

    @GetMapping("/authModify")
    @ResponseBody
    public ResponseEntity<String> canModifiedAttribute(@RequestParam(name="loginUser") String loginUser,
                                                        @RequestParam(name="targetUser") String targetUser,
                                                        @RequestParam(name="wantAuth") int wantAuth) {
        boolean modified = false;
        if (userService.CanUserModifiedAuth(loginUser)) {
            modified = userService.updateUserAuth(targetUser, wantAuth);
        }

        return modified ? ResponseEntity.ok("success") :
                ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

}
