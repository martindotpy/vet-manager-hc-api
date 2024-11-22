package com.vet.hc.api.bill.core.application.usecase;

import java.io.OutputStream;
import java.util.List;

import com.vet.hc.api.bill.core.adapter.out.mapper.BillMapper;
import com.vet.hc.api.bill.core.application.port.in.GenerateBillExcelPort;
import com.vet.hc.api.bill.core.domain.dto.BillDto;
import com.vet.hc.api.bill.core.domain.repository.BillRepository;
import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to generate an Excel file with the bills.
 */
@NoArgsConstructor
public class GenerateBillExcelUseCase implements GenerateBillExcelPort {
    private BillRepository billRepository;
    private GenerateExcelFromTablePort<BillDto> generateExcelFromTablePort;

    private BillMapper billMapper = BillMapper.INSTANCE;

    @Inject
    public GenerateBillExcelUseCase(BillRepository billRepository,
            GenerateExcelFromTablePort<BillDto> generateExcelFromTablePort) {
        this.billRepository = billRepository;
        this.generateExcelFromTablePort = generateExcelFromTablePort;
    }

    @Override
    public void generateExcel(OutputStream outputStream) {
        List<BillDto> bills = billRepository.findAll().stream()
                .map(billMapper::toDto)
                .toList();

        generateExcelFromTablePort.generateExcel(outputStream, "Bills", bills, BillDto.class);
    }
}
