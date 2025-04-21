package com.techwiz5.techwiz5.dtos.menuadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItem {
    private String title;
    private String path;
    private String icon;
}