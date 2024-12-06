package com.vet.hc.api.client.core.application.usecase;

import java.io.OutputStream;
import java.util.List;

import com.vet.hc.api.auth.core.adapter.annotations.UseCase;
import com.vet.hc.api.client.core.application.mapper.ClientMapper;
import com.vet.hc.api.client.core.application.port.in.GenerateClientExcelPort;
import com.vet.hc.api.client.core.domain.dto.ClientDto;
import com.vet.hc.api.client.core.domain.repository.ClientRepository;
import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;

import lombok.RequiredArgsConstructor;

/**
 * Service to generate an Excel file with the clients.
 */
@UseCase
@RequiredArgsConstructor
public final class GenerateClientExcelUseCase implements GenerateClientExcelPort {
    private final ClientRepository clientRepository;
    private final GenerateExcelFromTablePort<ClientDto> generateExcelFromTablePort;
    private final ClientMapper clientMapper;

    @Override
    public void generateExcel(OutputStream outputStream) {
        List<ClientDto> clients = clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .toList();

        generateExcelFromTablePort.generateExcel(outputStream, "Clientes", clients, ClientDto.class);
    }
}
