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
import com.interview.system.domain.vo.BLabelVo;
import com.interview.system.domain.bo.BLabelBo;
import com.interview.system.service.IBLabelService;
import com.interview.common.core.page.TableDataInfo;

/**
 * 岗位标签
 *
 * @author interview
 * @date 2024-12-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/label")
public class BLabelController extends BaseController {

    private final IBLabelService iBLabelService;

    /**
     * 查询岗位标签列表
     */
    @SaCheckPermission("system:label:list")
    @GetMapping("/list")
    public TableDataInfo<BLabelVo> list(BLabelBo bo, PageQuery pageQuery) {
        return iBLabelService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出岗位标签列表
     */
    @SaCheckPermission("system:label:export")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BLabelBo bo, HttpServletResponse response) {
        List<BLabelVo> list = iBLabelService.queryList(bo);
        ExcelUtil.exportExcel(list, "【请填写功能名称】", BLabelVo.class, response);
    }

    /**
     * 获取岗位标签详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:label:query")
    @GetMapping("/{id}")
    public R<BLabelVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iBLabelService.queryById(id));
    }

    /**
     * 新增岗位标签
     */
    @SaCheckPermission("system:label:add")
    @Log(title = "岗位标签", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BLabelBo bo) {
        return toAjax(iBLabelService.insertByBo(bo));
    }

    /**
     * 修改岗位标签
     */
    @SaCheckPermission("system:label:edit")
    @Log(title = "岗位标签", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BLabelBo bo) {
        return toAjax(iBLabelService.updateByBo(bo));
    }

    /**
     * 删除岗位标签
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:label:remove")
    @Log(title = "岗位标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBLabelService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
