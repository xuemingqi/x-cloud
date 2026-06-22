package com.x.framework.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：xuemingqi
 * @since ：created in 2023/1/10 16:04
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String userId;
    private String mobile;
}
