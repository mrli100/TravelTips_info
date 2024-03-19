package net.maku.travel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.maku.framework.common.utils.PageResult;
import net.maku.framework.common.utils.Result;
import net.maku.framework.security.user.SecurityUser;
import net.maku.framework.security.user.UserDetail;
import net.maku.travel.query.TPlanTripMainQuery;
import net.maku.travel.service.TPlanTripMainService;
import net.maku.travel.vo.TPlanTripMainVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("travel")
@AllArgsConstructor
@Tag(name = "API接口集合")
public class ApiTravelController {

    private final TPlanTripMainService tPlanTripMainService;

    @GetMapping("/user/travelInfo")
    @Operation(summary = "查询个人旅游信息资料")
    public Result<Map> travelInfo() {
        UserDetail user = SecurityUser.getUser();
        Map map = new HashMap<>();

        //查詢草稿箱的内容
        TPlanTripMainQuery query = new TPlanTripMainQuery();
        query.setPage(1);
        query.setLimit(10);
        PageResult<TPlanTripMainVO> page = tPlanTripMainService.page(query);
        map.put("drafts", page.getTotal());
        return Result.ok(map);
    }
}
