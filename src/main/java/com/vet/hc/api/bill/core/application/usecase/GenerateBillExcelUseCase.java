package com.vet.hc.api.bill.core.application.usecase;

import java.io.OutputStream;
import java.util.List;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.bill.core.application.mapper.BillMapper;
import com.vet.hc.api.bill.core.application.port.in.GenerateBillExcelPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;

import lombok.RequiredArgsConstructor;

/**
 * Service to generate an Excel file with the bills.
 */
@UseCase
@RequiredArgsConstructor
public final class GenerateBillExcelUseCase implements GenerateBillExcelPort {
    private final BillRepository billRepository;
    private final GenerateExcelFromTablePort<BillDto> generateExcelFromTablePort;
    private final BillMapper billMapper;

    @Override
    public void generateExcel(OutputStream outputStream) {
        List<BillDto> bills = billRepository.findAll().stream()
                .map(billMapper::toDto)
                .toList();

        generateExcelFromTablePort.generateExcel(outputStream, "Facturas", bills, BillDto.class);
    }
}
