package com.interview.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.util.ObjectUtil;
import com.interview.system.domain.BMianshi;
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
import com.interview.system.domain.vo.BMianshiVo;
import com.interview.system.domain.bo.BMianshiBo;
import com.interview.system.service.IBMianshiService;
import com.interview.common.core.page.TableDataInfo;

/**
 * 面试
 *
 * @author interview
 * @date 2024-12-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/mianshi")
public class BMianshiController extends BaseController {

    private final IBMianshiService iBMianshiService;

    /**
     * 查询面试列表
     */
    @SaCheckPermission("system:mianshi:list")
    @GetMapping("/list")
    public TableDataInfo<BMianshiVo> list(BMianshiBo bo, PageQuery pageQuery) {
        return iBMianshiService.queryPageList(bo, pageQuery);
    }
    @GetMapping("/myList")
    public TableDataInfo<BMianshiVo> myList(BMianshiBo bo, PageQuery pageQuery) {
        return iBMianshiService.queryMyPageList(bo, pageQuery);
    }


    /**
     * 导出面试列表
     */
    @SaCheckPermission("system:mianshi:export")
    @Log(title = "面试", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(BMianshiBo bo, HttpServletResponse response) {
        List<BMianshiVo> list = iBMianshiService.queryList(bo);
        ExcelUtil.exportExcel(list, "面试", BMianshiVo.class, response);
    }

    /**
     * 获取面试详细信息
     *
     * @param id 主键
     */
//    @SaCheckPermission("system:mianshi:query")
    @GetMapping("/{id}")
    @SaIgnore
    public R<BMianshiVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(iBMianshiService.queryById(id));
    }

    /**
     * 新增面试
     */
//    @SaCheckPermission("system:mianshi:add")
    @Log(title = "面试", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<BMianshi> add(@Validated(AddGroup.class) @RequestBody BMianshiBo bo) {
        if(ObjectUtil.isEmpty(bo.getJianliId()) && ObjectUtil.isEmpty(bo.getJobsId())) {
            return R.fail("请选择岗位或者上传简历");
        }
        return R.ok(iBMianshiService.insertByBo(bo));
    }

    /**
     * 修改面试
     */
//    @SaCheckPermission("system:mianshi:edit")
    @Log(title = "面试", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody BMianshiBo bo) {
        if(ObjectUtil.isEmpty(bo.getMsContent())){
            return R.fail("面试内容不能为空");
        }
        return toAjax(iBMianshiService.updateByBo(bo));
    }


    @GetMapping("/bgsc/{id}")
    @SaIgnore
    public R<Void> bgsc(@NotNull(message = "主键不能为空")
                            @PathVariable Long id) {
        return toAjax(iBMianshiService.bgsc(id));
    }


    /**
     * 删除面试
     *
     * @param ids 主键串
     */
//    @SaCheckPermission("system:mianshi:remove")
    @Log(title = "面试", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(iBMianshiService.deleteWithValidByIds(Arrays.asList(ids), true));
    }
}
