package com.interview.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import cn.dev33.satoken.annotation.SaIgnore;
import com.interview.system.domain.BLabel;
import com.interview.system.domain.vo.BLabelVo;
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
import com.interview.system.domain.vo.BJobsVo;
import com.interview.system.domain.bo.BJobsBo;
import com.interview.system.service.IBJobsService;
import com.interview.common.core.page.TableDataInfo;

/**
 * 岗位
 *
 * @author interview
 * @date 2024-12-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/jobs")
public class BJobsController extends BaseController {

    private final IBJobsService iBJobsService;

    @GetMapping("/all")
    @SaIgnore
    public List<BLabelVo> all(BJobsBo bo) {
        return iBJobsService.queryAllList(bo);
    }

    /**
     * 查询岗位列表
     */
    @SaCheckPermission("system:jobs:list")
    @GetMapping("/list")
    public TableDataInfo<BJobsVo> list(BJobsBo bo, PageQuery pageQuery) {
        return iBJobsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出岗位列表
     */
    @SaCheckPermission("system:jobs:export")
    @Log(title = "岗位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BJobsBo bo, HttpServletResponse response) {
        List<BJobsVo> list = iBJobsService.queryList(bo);
        ExcelUtil.exportExcel(list, "岗位", BJobsVo.class, response);
    }

    /**
     * 获取岗位详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:jobs:query")
    @GetMapping("/{id}")
    public R<BJobsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iBJobsService.queryById(id));
    }

    /**
     * 新增岗位
     */
    @SaCheckPermission("system:jobs:add")
    @Log(title = "岗位", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BJobsBo bo) {
        return toAjax(iBJobsService.insertByBo(bo));
    }

    /**
     * 修改岗位
     */
    @SaCheckPermission("system:jobs:edit")
    @Log(title = "岗位", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BJobsBo bo) {
        return toAjax(iBJobsService.updateByBo(bo));
    }

    /**
     * 删除岗位
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:jobs:remove")
    @Log(title = "岗位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBJobsService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
