package com.project.lms.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.lms.error.ErrorResponse;
import com.project.lms.error.NotValidExceptionResponse;
import com.project.lms.service.MemberSecurityService;
import com.project.lms.service.MemberService;
import com.project.lms.vo.LoginVO;
import com.project.lms.vo.MapVO;
import com.project.lms.vo.MemberLoginResponseVO;
import com.project.lms.vo.member.MemberJoinVO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(description = "회원 관련 API입니다.", name = "회원")
public class MemberController {
    private final MemberService mService;
    private final MemberSecurityService memberSecurityService;

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회성공", content = @Content(schema = @Schema(implementation = MapVO.class))),
        @ApiResponse(responseCode = "406", description = "회원가입 실패. response의 err메세지 확인", content = @Content(schema = @Schema(implementation = NotValidExceptionResponse.class))),
        @ApiResponse(responseCode = "400", description = "type에러(대소문자는 상관없습니다. 오타확인해주세요) or 과목번호 에러 or 반 번호 에러", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "424", description = "선생님 가입중일 시 과목번호 오류(일치하는 과목이 없습니다.), 직원 가입중일시 부서나 직급정보 누락(오타나도 에러납니다), 학생 가입중일시 반번호 누락", content = @Content(schema = @Schema(implementation = MapVO.class))) })
    @Operation(summary = "회원가입", description ="입력받은 타입 정보(student, teacher, employee, master)에 따라 회원가입이 진행됩니다.")
    @PutMapping("/join/{type}")
    public ResponseEntity<MapVO> joinMember(
            @Parameter(description ="가입할 계정 타입(student, teacher, employee, master). 대소문자 상관없이 일치하는 타입으로 가입됩니다.") @PathVariable String type, 
            @RequestBody @Valid MemberJoinVO data, 
            BindingResult bindingResult){

        return new ResponseEntity<>(mService.joinMember(data, type, bindingResult), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberLoginResponseVO> postMemberLogin(@RequestBody LoginVO login) {
        MemberLoginResponseVO response = memberSecurityService.securityLogin(login);
        return new ResponseEntity<>(response,response.getCod());
    }
}
