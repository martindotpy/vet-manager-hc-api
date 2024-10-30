package com.vet.hc.api.client.application.service;

import java.io.OutputStream;
import java.util.List;

import com.vet.hc.api.client.adapter.out.mapper.ClientMapper;
import com.vet.hc.api.client.application.dto.ClientDto;
import com.vet.hc.api.client.application.port.in.GenerateClientExcelPort;
import com.vet.hc.api.client.domain.repository.ClientRepository;
import com.vet.hc.api.shared.application.port.out.GenerateExcelFromTablePort;

import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

/**
 * Service to generate an Excel file with the clients.
 */
@NoArgsConstructor
public class GenerateClientExcelService implements GenerateClientExcelPort {
    private ClientRepository clientRepository;
    private GenerateExcelFromTablePort<ClientDto> generateExcelFromTablePort;

    private ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Inject
    public GenerateClientExcelService(ClientRepository clientRepository,
            GenerateExcelFromTablePort<ClientDto> generateExcelFromTablePort) {
        this.clientRepository = clientRepository;
        this.generateExcelFromTablePort = generateExcelFromTablePort;
    }

    @Override
    public void generateExcel(OutputStream outputStream) {
        List<ClientDto> clients = clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .toList();

        generateExcelFromTablePort.generateExcel(outputStream, "Clientes", clients, ClientDto.class);
    }
}
