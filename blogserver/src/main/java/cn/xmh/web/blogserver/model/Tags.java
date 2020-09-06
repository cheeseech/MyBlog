package cn.xmh.web.blogserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Xmh
 * @date 2020/7/28 20:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tags implements Serializable {

    private Long tagId;
    private String tagName;
    private String tagType;

    private List<Article> articles;
}
