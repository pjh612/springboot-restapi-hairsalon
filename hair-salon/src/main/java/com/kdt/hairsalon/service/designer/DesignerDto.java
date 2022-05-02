package com.kdt.hairsalon.service.designer;

import com.kdt.hairsalon.model.Designer;
import com.kdt.hairsalon.model.Position;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DesignerDto {
    private final UUID id;
    private final String name;
    private final Position position;
    private final LocalDateTime joinedAt;

    public static DesignerDto of(Designer designer) {
        return new DesignerDto(designer.getId(), designer.getName(), designer.getPosition(), designer.getJoinedAt());
    }
}
