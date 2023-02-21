package com.mouse.domain.dto;

import com.mouse.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 星星
 * @create 2023-02-21 13:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MenuTreeDto {
//    children":[],
//    "id":"2023",
//    "label":"写博文",
//    "parentId":"0"

    private List<MenuTreeDto> children;
    private Long id;
    private String label;
    private Long parentId;

}
