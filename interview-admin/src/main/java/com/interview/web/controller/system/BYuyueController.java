package com.interview.web.controller.system;

import java.time.Month;
import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.interview.system.domain.BYuyue;
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
import com.interview.system.domain.vo.BYuyueVo;
import com.interview.system.domain.bo.BYuyueBo;
import com.interview.system.service.IBYuyueService;
import com.interview.common.core.page.TableDataInfo;

/**
 * 预约
 *
 * @author interview
 * @date 2024-12-18
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/yuyue")
public class BYuyueController extends BaseController {

    private final IBYuyueService iBYuyueService;

    /**
     * 查询预约列表
     */
//    @SaCheckPermission("system:yuyue:list")
    @GetMapping("/myList")
    public R<List<BYuyueVo>> list(BYuyueBo bo) {
        return R.ok(iBYuyueService.queryPageList(bo));
    }

    @GetMapping("/listByMonth")
    public R<List<BYuyue>> listByMonth(String date) {

        return R.ok(iBYuyueService.queryPageAllList(date));
    }

//    /**
//     * 导出预约列表
//     */
//    @SaCheckPermission("system:yuyue:export")
//    @Log(title = "预约", businessType = BusinessType.EXPORT)
//    @PostMapping("/export")
//    public void export(BYuyueBo bo, HttpServletResponse response) {
//        List<BYuyueVo> list = iBYuyueService.queryList(bo);
//        ExcelUtil.exportExcel(list, "预约", BYuyueVo.class, response);
//    }

    /**
     * 获取预约详细信息
     *
     * @param id 主键
     */
//    @SaCheckPermission("system:yuyue:query")
    @GetMapping("/{id}")
    public R<BYuyueVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iBYuyueService.queryById(id));
    }

    /**
     * 新增预约
     */
//    @SaCheckPermission("system:yuyue:add")
    @Log(title = "预约", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody BYuyueBo bo) {
        return toAjax(iBYuyueService.insertByBo(bo));
    }

//    /**
//     * 修改预约
//     */
////    @SaCheckPermission("system:yuyue:edit")
//    @Log(title = "预约", businessType = BusinessType.UPDATE)
//    @RepeatSubmit()
//    @PutMapping()
//    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BYuyueBo bo) {
//        return toAjax(iBYuyueService.updateByBo(bo));
//    }

    /**
     * 删除预约
     *
     * @param ids 主键串
     */
//    @SaCheckPermission("system:yuyue:remove")
    @Log(title = "预约", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBYuyueService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
