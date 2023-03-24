package cn.iosd.starter.datasource.base;

import cn.iosd.starter.datasource.domain.PageRequest;
import cn.iosd.starter.datasource.utils.DsConvertUtil;
import cn.iosd.starter.web.domain.Response;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ok1996
 */
@Component
public class BaseController<T> {

    @Autowired
    private IService<T> service;

    @Operation(summary = "Api-新增")
    @PostMapping("/api")
    public Response<Boolean> apiSave(@RequestBody T entity) {
        setValue(entity, "setCreateTime", LocalDateTime.now(), LocalDateTime.class);
        return Response.ok(service.save(entity));
    }

    @Operation(summary = "Api-更新-Id")
    @PutMapping("/api/{id}")
    public Response<Boolean> apiUpdateById(@PathVariable Long id, @RequestBody T entity) {
        setValue(entity, "setId", id, Long.class);
        setValue(entity, "setModifyTime", LocalDateTime.now(), LocalDateTime.class);
        return Response.ok(service.updateById(entity));
    }

    @Operation(summary = "Api-删除")
    @DeleteMapping("/api/{id}")
    public Response<Boolean> apiRemoveById(@PathVariable Long id) {
        return Response.ok(service.removeById(id));
    }

    @Operation(summary = "Api-查询-单个")
    @GetMapping("/api/{id}")
    public Response<T> apiGetById(@PathVariable Long id) {
        return Response.ok(service.getById(id));
    }

    @Operation(summary = "Api-查询-列表")
    @GetMapping("/api/list")
    public Response<List<T>> apiList(@ParameterObject T req) {
        return Response.ok(service.list(Wrappers.lambdaQuery(req)));
    }

    @Operation(summary = "Api-查询-分页")
    @PostMapping("/api/page")
    public Response<IPage<T>> apiPage(@RequestBody PageRequest<T> req) {
        return Response.ok(service.page(DsConvertUtil.page(req), Wrappers.lambdaQuery(req.getData())));
    }

    /**
     * 通过反射调用实体类对象中的指定方法，设置指定的值。
     *
     * @param <T>        方法的参数类型
     * @param entity     需要设置值的实体类对象
     * @param methodName 需要调用的方法名
     * @param value      需要设置的值
     * @param valueType  value参数的类型
     */
    public <T> void setValue(T entity, String methodName, Object value, Class<?> valueType) {
        try {
            Method method = entity.getClass().getMethod(methodName, valueType);
            method.invoke(entity, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            // T实体不存在方法，不做处理
        }
    }

}