import org.example.signlinkuagecapstone.dto.DashboardResponse;
import org.example.signlinkuagecapstone.entity.User;
import org.example.signlinkuagecapstone.service.DashboardService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;





@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<List<DashboardResponse>> getDashboard(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(
                dashboardService.getUserDashboard(user)
        );
    }
}

