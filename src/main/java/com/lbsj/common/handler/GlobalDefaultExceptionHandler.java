package com.lbsj.common.handler;


import com.lbsj.common.model.RequestResult;
import com.lbsj.common.model.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author itranlin
 */
@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {


    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseBody
    public RequestResult<Void> requestExceptionHandler(DataIntegrityViolationException e) {
        log.error("数据操作格式异常", e);
        return RequestResult.e(StatusEnum.FAIL, null, "数据操作格式异常");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RequestResult<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        log.error("参数验证失败");
        StringBuilder s = new StringBuilder("参数验证失败");
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            errors.forEach(error -> {
                s.append("</n>");
                s.append(error.getDefaultMessage());
            });
        }
        return RequestResult.e(StatusEnum.FAIL, null, s.toString());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RequestResult<Void> requestExceptionHandler(Exception e) {
        log.error("服务器飘了～～管理员拿刀去修了 (ノへ￣、)", e);
        return RequestResult.e(StatusEnum.FAIL, null, "系统发生错误");
    }

}
