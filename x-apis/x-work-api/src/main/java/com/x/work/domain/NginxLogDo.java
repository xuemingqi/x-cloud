package com.x.work.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : xuemingqi
 * @since : 2023/2/10 17:27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "NginxLogDto", description = "nginx日志参数")
public class NginxLogDo {

    @Schema(name = "ip", description = "ip")
    private String ip;

    @Schema(name = "time", description = "时间")
    private LocalDateTime time;

    @Schema(name = "city", description = "城市")
    private String city;

    @Schema(name = "addr", description = "通信地址")
    private String addr;
}
