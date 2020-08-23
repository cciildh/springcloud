package com.example.order.exception;

import com.example.order.VO.ResultVO;
import com.example.order.enums.ResultEnums;
import com.example.order.utlis.ResultVOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * GlobExceptionHandler全局异常处理
 */
@RestControllerAdvice // 实现全局异常处理
public class GlobExceptionHandler {
    //logback
    private static final Logger log = LoggerFactory.getLogger(GlobExceptionHandler.class);

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public Object runtimeException(RuntimeException e, HttpServletRequest req) {
        log.error("运行时异常:", e);

        return handlerException(e, req, ResultEnums.RUN_ERROR.getMessage(), ResultEnums.RUN_ERROR.getCode());
    }

    /**
     * 系统错误
     */
    @ExceptionHandler(Exception.class)
    public Object systemException(Exception e, HttpServletRequest req) {
        System.out.println("系统错误");
        log.error(e.getMessage(), e);
        return handlerException(e, req, ResultEnums.SYSTEM_ERROR.getMessage(), ResultEnums.SYSTEM_ERROR.getCode());
    }



    /**
     * 捕获方法参数校验异常
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResultVO constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        System.out.println("捕获方法参数校验异常");
        Set<ConstraintViolation<?>> message = e.getConstraintViolations();
        HashMap<String, Object> map = new HashMap<>();
        message.stream().forEach(msg -> {
            String path = msg.getPropertyPath().toString();
            String field = path.substring(path.indexOf(".") + 1);
            map.put(field, msg.getMessageTemplate());
        });
        return ResultVOUtil.success(ResultEnums.PARAM_ERROR.getCode(), ResultEnums.PARAM_ERROR.getMessage(), map);
    }

    /**
     * 捕获实体参数校验异常
     */
    @ExceptionHandler(value = BindException.class)
    public ResultVO validationExceptionHandler(BindException e) {
        log.error(e.getMessage(), e);
        System.out.println("捕获实体参数校验异常");
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        HashMap<String, Object> map = new HashMap<>();
        allErrors.stream().forEach(error -> {
            FieldError fieldError = (FieldError) error;
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResultVOUtil.success(ResultEnums.PARAM_ERROR.getCode(), ResultEnums.PARAM_ERROR.getMessage(), map);

    }

    private ResultVO handlerException(Exception e, HttpServletRequest req, String msg, int code) {

        // 使用HttpServletRequest中的header检测请求是否为ajax, 如果是ajax则返回json,
        // 如果为非ajax则返回view(即ModelAndView)
//        String contentTypeHeader = req.getHeader("Content-Type");
//        String acceptHeader = req.getHeader("Accept");
//        String xRequestedWith = req.getHeader("X-Requested-With");

        // if ((contentTypeHeader != null && contentTypeHeader.contains(" "))
        // || (acceptHeader != null && acceptHeader.contains("application/json"))
        // || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
        Map<String, String> map = new HashMap<>();
        map.put(msg, e.getMessage() == null ? e.toString() : e.getMessage());
        return ResultVOUtil.success(code, msg, map);

        // } else {
        // ModelAndView modelAndView = new ModelAndView();
        // modelAndView.addObject("msg",
        // e.getMessage()==null?e.toString():e.getMessage());
        // modelAndView.addObject("url", req.getRequestURL());
        // modelAndView.addObject("stackTrace", e.getStackTrace());
        // modelAndView.setViewName("error");
        // return modelAndView;
        // }
    }

}
