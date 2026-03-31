package com.interview.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.interview.system.domain.BJianli;
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
import com.interview.system.domain.vo.BJianliVo;
import com.interview.system.domain.bo.BJianliBo;
import com.interview.system.service.IBJianliService;
import com.interview.common.core.page.TableDataInfo;

/**
 * 简历
 *
 * @author interview
 * @date 2024-12-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/jianli")
public class BJianliController extends BaseController {

    private final IBJianliService iBJianliService;

    /**
     * 查询简历列表
     */
    @SaCheckPermission("system:jianli:list")
    @GetMapping("/list")
    public TableDataInfo<BJianliVo> list(BJianliBo bo, PageQuery pageQuery) {
        return iBJianliService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出简历列表
     */
    @SaCheckPermission("system:jianli:export")
    @Log(title = "简历", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BJianliBo bo, HttpServletResponse response) {
        List<BJianliVo> list = iBJianliService.queryList(bo);
        ExcelUtil.exportExcel(list, "简历", BJianliVo.class, response);
    }

    /**
     * 获取简历详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:jianli:query")
    @GetMapping("/{id}")
    public R<BJianliVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iBJianliService.queryById(id));
    }

    /**
     * 新增简历
     */
//    @SaCheckPermission("system:jianli:add")
    @Log(title = "简历", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<BJianli> add(@Validated(AddGroup.class) @RequestBody BJianliBo bo) {
        return R.ok(iBJianliService.insertByBo(bo));
    }

    /**
     * 修改简历
     */
    @SaCheckPermission("system:jianli:edit")
    @Log(title = "简历", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BJianliBo bo) {
        return toAjax(iBJianliService.updateByBo(bo));
    }

    /**
     * 删除简历
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:jianli:remove")
    @Log(title = "简历", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBJianliService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
