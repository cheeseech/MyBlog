package cn.xmh.web.blogserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Xmh
 * @date 2020/9/6 14:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleUser implements Serializable {

    private Long id;
    private Long roleId;
    private Long userId;
}
