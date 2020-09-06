package cn.xmh.web.blogserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xmh
 * @date 2020/7/28 20:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    private Long cateId;
    private String cateName;
    private String cateSummary;
    private Date createTime;
}
