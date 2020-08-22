package com.example.utils;

import com.example.VO.ResultVO;

public class ResultVOUtil {
    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success(Object object, Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
