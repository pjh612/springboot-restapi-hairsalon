package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.model.Position;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateDesignerRequest {
    private final String name;
    private final Position position;
}