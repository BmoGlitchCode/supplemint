package com.BmoGlitchCode.supplemint.adapter.input.rest.supplementlog;

import com.BmoGlitchCode.supplemint.application.dto.request.supplementlog.LogSupplementRequest;
import com.BmoGlitchCode.supplemint.application.dto.request.supplementlog.UpdateSupplementLogRequest;
import com.BmoGlitchCode.supplemint.application.dto.response.supplementlog.SupplementLogResponse;
import com.BmoGlitchCode.supplemint.application.mapper.supplementlog.SupplementLogDtoMapper;
import com.BmoGlitchCode.supplemint.domain.model.supplementlog.SupplementLog;
import com.BmoGlitchCode.supplemint.domain.port.input.supplementlog.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/supplement-logs")
@RequiredArgsConstructor
public class SupplementLogController {

    private final LogSupplementUseCase logSupplementUseCase;
    private final GetSupplementLogUseCase getSupplementLogUseCase;
    private final ListSupplementLogsUseCase listSupplementLogsUseCase;
    private final UpdateSupplementLogUseCase updateSupplementLogUseCase;
    private final DeleteSupplementLogUseCase deleteSupplementLogUseCase;
    private final SupplementLogDtoMapper mapper;

    @PostMapping
    public ResponseEntity<SupplementLogResponse> create(
            @RequestParam UUID userId,
            @RequestBody @Valid LogSupplementRequest request) {

        LogSupplementUseCase.LogSupplementCommand command = mapper.toLogCommand(userId, request);
        SupplementLog log = logSupplementUseCase.log(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(log));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplementLogResponse> get(
            @PathVariable UUID id,
            @RequestParam UUID userId) {

        GetSupplementLogUseCase.GetSupplementLogQuery query = mapper.toGetQuery(userId, id);
        SupplementLog log = getSupplementLogUseCase.get(query);
        return ResponseEntity.ok(mapper.toResponse(log));
    }

    @GetMapping
    public ResponseEntity<List<SupplementLogResponse>> list(
            @RequestParam UUID userId,
            @RequestParam(required = false) UUID supplementId,
            @RequestParam(required = false) UUID stackId,
            @RequestParam(required = false) Instant startDate,
            @RequestParam(required = false) Instant endDate,
            @RequestParam(required = false) Boolean skippedOnly) {

        ListSupplementLogsUseCase.ListSupplementLogsQuery query = mapper.toListQuery(
                userId, supplementId, stackId, startDate, endDate, skippedOnly);

        List<SupplementLog> logs = listSupplementLogsUseCase.list(query);
        List<SupplementLogResponse> responseList = logs.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplementLogResponse> update(
            @PathVariable UUID id,
            @RequestParam UUID userId,
            @RequestBody @Valid UpdateSupplementLogRequest request) {

        UpdateSupplementLogUseCase.UpdateSupplementLogCommand command = mapper.toUpdateCommand(userId, id, request);
        SupplementLog log = updateSupplementLogUseCase.update(command);
        return ResponseEntity.ok(mapper.toResponse(log));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id,
            @RequestParam UUID userId) {

        DeleteSupplementLogUseCase.DeleteSupplementLogCommand command = mapper.toDeleteCommand(userId, id);
        deleteSupplementLogUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }
}
