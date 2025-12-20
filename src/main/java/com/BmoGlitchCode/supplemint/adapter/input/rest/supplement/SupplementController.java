package com.BmoGlitchCode.supplemint.adapter.input.rest.supplement;

import com.BmoGlitchCode.supplemint.application.dto.request.supplement.CreateSupplementRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.supplement.UpdateSupplementRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.supplement.SupplementResponse;
import com.BmoGlitchCode.supplemint.application.mapper.supplement.SupplementDtoMapper;
import com.BmoGlitchCode.supplemint.domain.model.supplement.Supplement;
import com.BmoGlitchCode.supplemint.domain.model.supplement.SupplementId;
import com.BmoGlitchCode.supplemint.domain.model.user.UserId;
import com.BmoGlitchCode.supplemint.domain.port.input.supplement.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/supplements")
@RequiredArgsConstructor
public class SupplementController {

    private final CreateSupplementUseCase createSupplementUseCase;
    private final UpdateSupplementUseCase updateSupplementUseCase;
    private final GetSupplementUseCase getSupplementUseCase;
    private final ListUserSupplementsUseCase listUserSupplementsUseCase;
    private final DeleteSupplementUseCase deleteSupplementUseCase;
    private final SupplementDtoMapper mapper;

    @PostMapping
    public ResponseEntity<SupplementResponse> create(@RequestBody @Valid CreateSupplementRequest request) {
        CreateSupplementUseCase.CreateSupplementCommand command = mapper.toCreateCommand(request);
        Supplement supplement = createSupplementUseCase.create(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(supplement));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplementResponse> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateSupplementRequest request) {

        UpdateSupplementUseCase.UpdateSupplementCommand command = mapper.toUpdateCommand(id, request);
        Supplement supplement = updateSupplementUseCase.update(command);
        return ResponseEntity.ok(mapper.toResponse(supplement));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplementResponse> get(
            @PathVariable UUID id,
            @RequestParam UUID userId) {
        UserId uid = UserId.of(userId);
        SupplementId supplementId = SupplementId.of(id);

        GetSupplementUseCase.GetSupplementQuery query = new GetSupplementUseCase.GetSupplementQuery(
                uid,
                supplementId);

        Supplement supplement = getSupplementUseCase.get(query);
        return ResponseEntity.ok(mapper.toResponse(supplement));
    }

    @GetMapping
    public ResponseEntity<List<SupplementResponse>> list(
            @RequestParam UUID userId,
            @RequestParam(defaultValue = "true") boolean activeOnly) {
        UserId uid = UserId.of(userId);

        ListUserSupplementsUseCase.ListUserSupplementsQuery query = new ListUserSupplementsUseCase.ListUserSupplementsQuery(
                uid,
                activeOnly);

        List<Supplement> supplements = listUserSupplementsUseCase.list(query);
        List<SupplementResponse> responseList = supplements.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestParam UUID userId) {
        UserId uid = UserId.of(userId);
        SupplementId supplementId = SupplementId.of(id);

        DeleteSupplementUseCase.DeleteSupplementCommand command = new DeleteSupplementUseCase.DeleteSupplementCommand(
                uid,
                supplementId);

        deleteSupplementUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }
}
