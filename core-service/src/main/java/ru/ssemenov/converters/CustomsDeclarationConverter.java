package ru.ssemenov.converters;

import org.springframework.stereotype.Component;
import ru.ssemenov.dtos.CustomsDeclarationDto;
import ru.ssemenov.entities.CustomsDeclaration;

/**
 * A converter to receive {@link ru.ssemenov.dtos.CustomsDeclarationDto} from {@link ru.ssemenov.entities.CustomsDeclaration} entity
 */
@Component
public class CustomsDeclarationConverter {
    public CustomsDeclarationDto entityToDto(CustomsDeclaration c) {
        return CustomsDeclarationDto.builder()
                .id(c.getId())
                .number(c.getNumber())
                .status(c.getStatus())
                .consignor(c.getConsignor())
                .vatCode(c.getVatCode())
                .invoiceData(c.getInvoiceData())
                .goodsValue(c.getGoodsValue())
                .dateOfSubmission(c.getDateOfSubmission())
                .dateOfRelease(c.getDateOfRelease())
                .build();
    }
}
