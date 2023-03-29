package cn.iosd.demo.dict.vo;

import cn.iosd.starter.dict.annotation.DictEntity;
import cn.iosd.starter.dict.annotation.DictField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ok1996
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuperPersonVo {

    @DictEntity
    private PersonVo personVo;

    @Schema(description = "是否是领导")
    @DictField(path = "E:\\..\\src\\main\\resources\\isALeader.json", source = "localDictServiceImpl", relatedField = "isALeaderText")
    private Integer isALeader;

    private String isALeaderText;
}