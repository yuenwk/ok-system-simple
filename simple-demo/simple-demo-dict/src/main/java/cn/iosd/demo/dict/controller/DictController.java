package cn.iosd.demo.dict.controller;

import cn.iosd.demo.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import cn.iosd.starter.web.domain.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ok1996
 */
@Tag(name = "提供字典数据接口")
@RestController
@RequestMapping("dict")
public class DictController {
    @Autowired
    private DictService service;

    @Operation(summary = "字典翻译")
    @GetMapping("/{param}")
    public Response<List<DictItem>> remoteDict(@PathVariable String param) {
        return Response.ok(service.remoteDict(param));
    }

}
