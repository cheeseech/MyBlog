package cn.xmh.web.blogserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther Xmh
 * @date 2020/7/31 8:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultJson {
    private String status;
    private String msg;
    private Object data;
}
