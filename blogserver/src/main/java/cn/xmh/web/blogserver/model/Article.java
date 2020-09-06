package cn.xmh.web.blogserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Xmh
 * @date 2020/7/28 20:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {

    private Long articleId;
    private String title;
    private String mdContent;
    private String htmlContent;
    private String summary;
    private Long typeId;
    private Long cateId;
    private Long userId;
    private Date publishTime;
    private Date updateTime;
    private Integer articleState;
    private Integer recommend;
    private Integer views;
    private Integer likes;
    private Integer comments;
    private String firstPicture;

    private List<Tags> tags;
    private String typeName;
    private String cateName;
}
