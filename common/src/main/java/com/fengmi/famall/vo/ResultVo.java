package com.fengmi.famall.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ResultVo对象",description = "封装数据返回给前端的数据")
public class ResultVo {
    @ApiModelProperty(value = "响应状态码",dataType = "int")
//    响应给前端的状态码
    private int code;
//    响应给前端的提示信息
@ApiModelProperty(value = "响应提示信息")

private String msg;
//    响应给前端的数据
@ApiModelProperty(value = "响应前端数据")

private Object data;
}
