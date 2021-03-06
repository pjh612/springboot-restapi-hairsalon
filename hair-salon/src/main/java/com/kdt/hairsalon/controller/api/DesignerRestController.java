package com.kdt.hairsalon.controller.api;

import com.kdt.hairsalon.controller.api.request.CreateDesignerRequest;
import com.kdt.hairsalon.controller.api.request.UpdateDesignerRequest;
import com.kdt.hairsalon.service.designer.DesignerDto;
import com.kdt.hairsalon.service.designer.DesignerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/designers")
public class DesignerRestController {

    private final DesignerService designerService;

    @PostMapping
    public ResponseEntity<DesignerDto> create(@RequestBody @Valid CreateDesignerRequest request) {
        return ResponseEntity.ok(designerService.create(request.getName(), request.getPosition(), request.getSpecialty()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DesignerDto> getById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(designerService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<DesignerDto>> getAll() {
        return ResponseEntity.ok(designerService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteById(@PathVariable("id") UUID id) {
        designerService.deleteById(id);

        return ResponseEntity.ok(id);
    }

    @PatchMapping
    public ResponseEntity<UUID> updateById(@RequestBody @Valid UpdateDesignerRequest request) {
        return ResponseEntity.ok(designerService.update(request.getId(), request.getName(), request.getPosition(), request.getSpecialty()));
    }
}
