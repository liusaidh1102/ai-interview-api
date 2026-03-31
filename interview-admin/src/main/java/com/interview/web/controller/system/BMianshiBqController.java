package com.interview.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.interview.common.annotation.RepeatSubmit;
import com.interview.common.annotation.Log;
import com.interview.common.core.controller.BaseController;
import com.interview.common.core.domain.PageQuery;
import com.interview.common.core.domain.R;
import com.interview.common.core.validate.AddGroup;
import com.interview.common.core.validate.EditGroup;
import com.interview.common.core.validate.QueryGroup;
import com.interview.common.enums.BusinessType;
import com.interview.common.utils.poi.ExcelUtil;
import com.interview.system.domain.vo.BMianshiBqVo;
import com.interview.system.domain.bo.BMianshiBqBo;
import com.interview.system.service.IBMianshiBqService;
import com.interview.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】
 *
 * @author interview
 * @date 2025-03-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/mianshiBq")
public class BMianshiBqController extends BaseController {

    private final IBMianshiBqService iBMianshiBqService;

    /**
     * 查询【请填写功能名称】列表
     */
//    @SaCheckPermission("system:mianshiBq:list")
    @SaIgnore
    @GetMapping("/getlist")
    public R<List<BMianshiBqVo>> list(BMianshiBqBo bo) {
        if(ObjectUtil.isEmpty(bo.getMsId())) {
            return R.fail("面试id不可为空");
        }
        return R.ok(iBMianshiBqService.queryList(bo));
    }

    @GetMapping("/list")
    public TableDataInfo<BMianshiBqVo> list(BMianshiBqBo bo, PageQuery pageQuery) {
        return iBMianshiBqService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @SaCheckPermission("system:mianshiBq:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BMianshiBqBo bo, HttpServletResponse response) {
        List<BMianshiBqVo> list = iBMianshiBqService.queryList(bo);
        ExcelUtil.exportExcel(list, "【请填写功能名称】", BMianshiBqVo.class, response);
    }

    /**
     * 获取【请填写功能名称】详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:mianshiBq:query")
    @GetMapping("/{id}")
    public R<BMianshiBqVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iBMianshiBqService.queryById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @SaCheckPermission("system:mianshiBq:add")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BMianshiBqBo bo) {
        return toAjax(iBMianshiBqService.insertByBo(bo));
    }

    /**
     * 修改【请填写功能名称】
     */
    @SaCheckPermission("system:mianshiBq:edit")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BMianshiBqBo bo) {
        return toAjax(iBMianshiBqService.updateByBo(bo));
    }

    /**
     * 删除【请填写功能名称】
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:mianshiBq:remove")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBMianshiBqService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
