package com.kdt.hairsalon.service.menu;

import com.kdt.hairsalon.model.Menu;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@EqualsAndHashCode
@Getter
public class MenuDto {
    private final UUID id;
    private final String name;
    private final int price;

    public MenuDto(UUID id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static MenuDto of(Menu menu) {
        return new MenuDto(menu.getId(), menu.getName(), menu.getPrice());
    }
}
