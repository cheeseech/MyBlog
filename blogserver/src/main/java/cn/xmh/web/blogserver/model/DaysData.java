package cn.xmh.web.blogserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xmh
 * @date 2020/11/16 9:59
 * 用来存储每天的文章数据
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaysData implements Serializable {

    private int daysId;
    private Date days;
    private int views;
    private int likes;
    private int comments;
    private long articleId;
}
