package com.interview.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

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
import com.interview.system.domain.vo.BMianshiDetailVo;
import com.interview.system.domain.bo.BMianshiDetailBo;
import com.interview.system.service.IBMianshiDetailService;
import com.interview.common.core.page.TableDataInfo;

/**
 * 面试详情
 *
 * @author interview
 * @date 2024-12-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/mianshiDetail")
public class BMianshiDetailController extends BaseController {

    private final IBMianshiDetailService iBMianshiDetailService;

    /**
     * 查询面试详情列表
     */
    @SaCheckPermission("system:mianshiDetail:list")
    @GetMapping("/list")
    public TableDataInfo<BMianshiDetailVo> list(BMianshiDetailBo bo, PageQuery pageQuery) {
        return iBMianshiDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出面试详情列表
     */
    @SaCheckPermission("system:mianshiDetail:export")
    @Log(title = "面试详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BMianshiDetailBo bo, HttpServletResponse response) {
        List<BMianshiDetailVo> list = iBMianshiDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "面试详情", BMianshiDetailVo.class, response);
    }

    /**
     * 获取面试详情详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:mianshiDetail:query")
    @GetMapping("/{id}")
    public R<BMianshiDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iBMianshiDetailService.queryById(id));
    }

    /**
     * 新增面试详情
     */
    @SaCheckPermission("system:mianshiDetail:add")
    @Log(title = "面试详情", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BMianshiDetailBo bo) {
        return toAjax(iBMianshiDetailService.insertByBo(bo));
    }

    /**
     * 修改面试详情
     */
    @SaCheckPermission("system:mianshiDetail:edit")
    @Log(title = "面试详情", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BMianshiDetailBo bo) {
        return toAjax(iBMianshiDetailService.updateByBo(bo));
    }

    /**
     * 删除面试详情
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:mianshiDetail:remove")
    @Log(title = "面试详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBMianshiDetailService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
