package cn.xmh.web.blogserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Xmh
 * @date 2020/7/29 10:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleTags implements Serializable {

    private Long atId;
    private Long articleId;
    private Long tagId;
}
