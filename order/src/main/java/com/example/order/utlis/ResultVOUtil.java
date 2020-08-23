package com.example.order.utlis;

import com.example.order.VO.ResultVO;

/**
 * 统一返回结果集工具
 */
public class ResultVOUtil {
    public  static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(data);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success( Integer code, String msg,Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(data);
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }
}
